/*
 *  COP 3503 Spring 2022 Programming Assignment P5: Poly
 *  Copyright 2022 Jeanne Claire Moore
 */

import java.util.*;
import java.math.*;


public class poly {
    //  Create a global: recursions = 0
    public static int recursions = 0;
    //  iterate upwards every time either mult or longmult are called

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        String input1, input2;
        input1 = sc.nextLine();
        input2 = sc.nextLine();

        List<String> strs1 = Arrays.asList(input1.split(" "));
        List<String> strs2 = Arrays.asList(input2.split(" "));

        int k = (int) Math.pow(2, n);
        long[] vals1 = new long[k];
        long[] vals2 = new long[k];

        for (int i = 0; i < k; i++) {
            vals1[i] = Long.parseLong(strs1.get(i));
            vals2[i] = Long.parseLong(strs2.get(i));
        }

        //  Call mult method with two polynomials constructed from values
        polynomial res = poly.mult(new polynomial(vals1), new polynomial(vals2));

        //  Get output string builder and print it
        StringBuilder output = res.buildOutput();
        System.out.print(output);
    }

    static polynomial combinePolys(polynomial ac, polynomial md, polynomial bd) {
        int length = ac.getLength() * 2;
        int half = ac.getLength() / 2;
        long[] vals = new long[length];
        for (int i = 0; i < length; i++) {
            if (i < ac.getLength()) {
                vals[i] += ac.getCoeff(i);
            }
            if ((i < ac.getLength() + half) && (i > half)) {
                vals[i] += md.getCoeff(i-half);
            }
            if (i > ac.getLength()) {
                vals[i] += bd.getCoeff(i-ac.getLength());
            }
        }

        //  With this, vals is the full array of longs
        //  Use it to create a new polynomial result
        polynomial result = new polynomial(vals);
        return result;
    }

    //  add polys method
    static polynomial addPolys(polynomial a, polynomial b) throws Exception {
        //  takes two polynomials, must be of the same length
        if (a.getLength() != b.getLength()) {
            throw new Exception("Polynomials not equal size");
        }

        for(int i = 0; i < a.getLength(); i++) {
            a.addCoeff(i, b.getCoeff(i));
        }

        //  return the polynomial
        return a;
    }


    //  sub polys method
    static polynomial subPolys(polynomial c, polynomial a, polynomial b) throws Exception {
        //  takes two polynomials, must be of the same length
        if (a.getLength() != c.getLength() || a.getLength() != b.getLength()) {
            throw new Exception("Polynomials not equal size");
        }

        //  Move through polynomials and sub their values
        for (int i = 0; i < c.getLength(); i++) {
            c.subCoeff(i,a.getCoeff(i));
            c.subCoeff(i,b.getCoeff(i));
        }


        //  Return c as resulting polynomial
        return c;
    }


    //  multSlow method
    static polynomial multSlow(polynomial a, polynomial b) {
        //  Only run this for the base case to compare against
        //  move through each element of the first polynomial, multiply it to every other element of the other polynomial,
        //  and add them into the degree in which they fit aka 10^x


        int n = a.getLength() + b.getLength();

        long[] vals = new long[n];
        for (int i = 0; i < n; i++) {
            vals[i] = 0;
        }

        for (int i = 0; i < a.getLength(); i++) {
            for (int j = 0; j < b.getLength(); j++) {
                recursions++;
                vals[i+j+1] += a.getCoeff(i) * b.getCoeff(j);
            }
        }

        polynomial res = new polynomial(vals);
        return res;
    }

    //  mult method
    static polynomial mult(polynomial first, polynomial second) throws Exception {
        recursions++;
        if (first.getLength() < 32) {
            return multSlow(first, second);
        }
        recursions++;
        //  the two polys MUST BE SAME SIZE
        if (first.getLength() != second.getLength()) {
            throw new Exception("Polynomials not equal size");
        }

        //  Split the polynomials in half into polys a, b, c, d
        polynomial a, b, c, d;
        a = first.getLeft();
        b = first.getRight();
        c = second.getLeft();
        d = second.getRight();


        //  Declare product polys
        polynomial ac, bd, md;

        if ((a.getLength() == 2) && (!a.checkIsSplittable())) {
            //  find ac, bd, and ad + bc
            ac = new polynomial(longmult(a.getCoeff(1),c.getCoeff(1)));

            bd = new polynomial(longmult(b.getCoeff(1),d.getCoeff(1)));

            md = new polynomial(longmult(addPolys(a,b).getCoeff(1),addPolys(c,d).getCoeff(1)));


        } else {
            //  If the polys are still larger than one, recurse into mult again
            ac = mult(a,c);
            bd = mult(b,d);
            md = mult(addPolys(a,b),addPolys(c,d));
        }

        md = subPolys(md, ac, bd);


        //  Vals should be built properly
        polynomial result = combinePolys(ac,md,bd);

        return result;
    }

    //  HERE IS A METHOD I MADE FOR FUN WHERE WE LOOK AT INDIVIDUAL LONGS AND PUNCH OURSELVES FOR PUTTING WAY TOO
    //  MUCH WORK INTO THE MATH NO THIS WAS NOT REQUIRED AND I AM MAD. longmult does NOT WORK in most situations, like
    //  it almost works but only for certain cases
    //  longmult method
    static long longmult(long first, long second) {
        recursions++;
        if (first < 10 || second < 10) {
            return first * second;
        }


        String firstStr, secondStr;

        //  split two longs in half by digits into: a, b, c, d longs
        //  long ac, bd, md
        firstStr = Long.toString(first);
        secondStr = Long.toString(second);

        //  get num of digits in first and second input longs, store as n and m
        int n, m;
        n = firstStr.length();
        m = secondStr.length();
        int k = n + m - 3;

        //  Get four pieces and split the two input long strings into them
        String a, b, c, d;
        a = firstStr.substring(0,n/2);
        b = firstStr.substring(n/2);
        c = secondStr.substring(0, m/2);
        d = secondStr.substring(m/2);

        //  Get the maximum of the two nums of digits
        long ac, bd, md;


        ac = longmult(Long.parseLong(a),Long.parseLong(c));
        bd = longmult(Long.parseLong(b),Long.parseLong(d));
        md = longmult(Long.parseLong(a)+Long.parseLong(b),Long.parseLong(c)+Long.parseLong(d)) - (ac + bd);
        System.out.println(ac + " " + md + " " + bd);
        double res1 =  (ac * Math.pow(10,k) + md * Math.pow(10, k/2) + bd);
        System.out.println(res1 + "\n");
        //  return result
        long res = 1;
        return res;
    }
}


class polynomial {
    private int k;
    private int length;
    private long[] coeff;
    private boolean isSplittable;

    //  This constructor serves to create a polynomial of degree size 1 shifted left k
    public polynomial (long[] vals) {
        if (vals.length == 1) {
            length = 2;
            coeff = new long[length];
            coeff[0] = 0;
            coeff[1] = vals[0];
            isSplittable = false;
        } else {
            length = vals.length;
            coeff = new long[length];

            for (int i = 0; i < length; i++) {
                coeff[i] = vals[i];
            }
            isSplittable = true;
        }
    }

    //  This constructor creates a polynomial of size 1
    public polynomial (long val) {
        k = 1;
        length = 2;
        coeff = new long[length];

        coeff[1] = val;
    }

    //  Create getters to grab length and coefficients
    public int getLength() {
        return length;
    }

    public long[] getCoeffs() {
        return coeff;
    }

    public long getCoeff(int i) {
        return coeff[i];
    }

    public int getK() {
        return k;
    }

    //  Create a method that adds a long value to the coefficients
    public void addCoeff(int i, long x) {
        coeff[i] += x;
    }

    public void subCoeff(int i, long x) {
        coeff[i] -= x;
    }

    public boolean checkIsSplittable() {
        return isSplittable;
    }

    //  takes left half of the polynomial
    public polynomial getLeft() {
        long[] vals = new long[length/2];

        for (int i = 0; i < length/2; i++){
            vals[i] = coeff[i];
        }

        return new polynomial(vals);
    }

    //  takes right half of the polynomial
    public polynomial getRight() {
        long[] vals = new long[length - length/2];

        for (int i = length/2; i < length; i++){
            vals[i-(length/2)] = coeff[i];
        }

        return new polynomial(vals);
    }

    public void printPoly() {
        for (int i = 0; i < length; i++){
            System.out.print(coeff[i] + " ");
        }
        System.out.println();
    }

    public StringBuilder buildOutput() {
        //  Create a String Builder
        StringBuilder output = new StringBuilder();

        //  Go through the coefficients and add each to the output
        for (int i = 1; i < length; i++) {
            output.append(coeff[i] + "\n");
        }
        //  return the output
        return output;
    }
}