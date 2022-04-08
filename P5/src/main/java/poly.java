/*
 *  COP 3503 Spring 2022 Programming Assignment P5: Poly
 *  Copyright 2022 Jeanne Claire Moore
 */

// Arup Guha
// 3/27/2018
// Implementation of integer multiplication algorithm with run time n^(log 2 3).

import java.util.*;
import java.math.*;


public class poly {
    //  Create a global: recursions = 0
    //  iterate upwards every time either mult or longmult are called

    public static void main(String[] args) throws Exception {
        //  in main:
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

        //  start timer and run slowmult using n^2 algorithm
        polynomial res = poly.mult(new polynomial(vals1,n-1), new polynomial(vals2,n-1));
        System.out.println(res.getCoeffs());
        //  output end - start to show total time of base case


        //  Read in two lines as strings, then split them into an array of longs
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

        int k = a.getK()+1;
        int n = 1<<(a.getK()+1);

        long[] vals = new long[n];
        for (int i = 0; i < n; i++) {
            vals[i] = 0;
        }

        for (int i = 0; i < a.getLength(); i++) {
            for (int j = 0; j < b.getLength(); j++) {
                vals[i+j] += a.getCoeff(i) * b.getCoeff(j);
            }
        }

        polynomial res = new polynomial(vals, k);
        return res;
    }

    //  mult method
    static polynomial mult(polynomial first, polynomial second) throws Exception {
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

        if (a.getLength() == 1) {
            //  find ac, bd, and ad + bc
            ac = new polynomial(longmult(a.getCoeff(0),c.getCoeff(0)));
            bd = new polynomial(longmult(b.getCoeff(0),d.getCoeff(0)));
            md = new polynomial(longmult(addPolys(a,b).getCoeff(0),addPolys(c,d).getCoeff(0)));

        } else {
            //  If the polys are still larger than one, recurse into mult again
            ac = mult(a,c);
            bd = mult(b,d);
            md = mult(addPolys(a,b),addPolys(c,d));
        }

        //  In addition, sub out ac and bd
        md = subPolys(md, ac, bd);

        //  Build an array of longs using ac, md, and bd
        long[] vals = new long[md.getLength()+ac.getLength()+ bd.getLength()];

        int k = first.getK()+1;

        polynomial result = new polynomial(vals, k);
        return result;
    }


    //  longmult method
    static long longmult(long first, long second) {
        if (first < 10 || second < 10)
            return first * second;


        String firstStr, secondStr;

        //  split two longs in half by digits into: a, b, c, d longs
        //  long ac, bd, md
        firstStr = Long.toString(first);
        secondStr = Long.toString(second);

        //  get num of digits in first and second input longs, store as n and m
        int n, m;
        n = firstStr.length();
        m = secondStr.length();

        //  Get four pieces and split the two input long strings into them
        String a, b, c, d;
        a = firstStr.substring(0,n/2);
        b = firstStr.substring(n/2);
        c = secondStr.substring(0, m/2);
        d = secondStr.substring(m/2);

        //  Get the maximum of the two nums of digits
        n = Math.max(n, m);
        long ac, bd, md;

        ac = longmult(Long.parseLong(a),Long.parseLong(c));
        bd = longmult(Long.parseLong(b),Long.parseLong(d));
        md = longmult(Long.parseLong(a)+Long.parseLong(b),Long.parseLong(c)+Long.parseLong(d)) - (ac + bd);
        long res = (long) (ac * Math.pow(10,n) + md * Math.pow(10, n/2) + bd);

        //  return result
        return res;
    }
}


class polynomial {
    private int k;
    private int length;
    private long[] coeff;

    //  This constructor serves to create a polynomial of degree size 1 shifted left k
    public polynomial (long[] vals, int k) {
        this.k = k;
        length = 1<<k;
        coeff = new long[length];

        for (int i = 0; i < length; i++){
            coeff[i] = vals[i];
        }
    }

    //  This constructor creates a polynomial of size 1
    public polynomial (long val) {
        k = 0;
        length = 1;
        coeff = new long[length];

        coeff[0] = val;
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

    //  takes left half of the polynomial
    public polynomial getLeft() {
        long[] vals = new long[length/2];

        for (int i = 0; i < length/2; i++){
            vals[i] = coeff[i];
        }

        return new polynomial(vals, k-1);
    }

    //  takes right half of the polynomial
    public polynomial getRight() {
        long[] vals = new long[length - length/2];

        for (int i = length/2; i < length; i++){
            vals[i-(length/2)] = coeff[i];
        }

        return new polynomial(vals, k-1);
    }

}






//  The following code is copyrighted by ARUP GUHA and was slightly modified for this assignment

// Arup Guha
// 3/27/2018
// Implementation of integer multiplication algorithm with run time n^(log 2 3).


/*
class intmult {

    final public static int SIZE = 250000;

    public static void main(String[] args) {

        Random r = new Random();

        // Generate one random multiplication
        String s = getRandNum(r);
        String t = getRandNum(r);

        long[] a = convert(s);
        long[] b = convert(t);

        long startMine = System.currentTimeMillis();
        long[] tmp = mult(a, b);
        long endMine = System.currentTimeMillis();

        // Smooth it out so digits are all in between 0 and 9.
        ArrayList<Integer> res = smooth(tmp);

        char[] str = new char[res.size()];
        for (int i=res.size()-1,j=0; i>=0; i--,j++)
            str[j] = (char)(res.get(i) + '0');
        String myans = new String(str);

        // Now do big integers to double check.
        BigInteger mya = new BigInteger(s);
        BigInteger myb = new BigInteger(t);
        long startJava = System.currentTimeMillis();
        BigInteger prod = mya.multiply(myb);
        long endJava = System.currentTimeMillis();

        if (myans.equals(prod.toString()))
            System.out.println("correct! my time = "+(endMine-startMine)+" ms, Java time = "+(endJava-startJava)+" ms.");
    }

    public static String getRandNum(Random r) {
        char[] res = new char[SIZE];
        res[0] = (char)(1 + r.nextInt(9) + '0');
        for (int i=1; i<res.length; i++)
            res[i] = (char)(r.nextInt(10) + '0');
        return new String(res);
    }

    // Return the digit representation of s with lsb stored in index 0. s is stored in usual way.
    public static int[] convert(String s) {

        // Make the real length a perfect power of two.
        int reallen = 1;
        while (reallen < s.length()) reallen <<= 1;

        // Just copy non-zero values.
        int[] res = new int[reallen];
        for (int i=0; i<s.length(); i++)
            res[s.length()-1-i] = s.charAt(i) - '0';
        return res;
    }

    public static ArrayList<Integer> smooth(int[] num) {

        ArrayList<Integer> res = new ArrayList<Integer>();
        int left = 0;

        // Just mod by 10 to get digit, carrying over rest.
        for (int i=0; i<num.length; i++) {
            left += num[i];
            res.add(left%10);
            left /= 10;
        }

        // Keep on carrying until all leftover is gone.
        while (left > 0) {
            res.add(left%10);
            left /= 10;
        }

        // Strip 0s.
        while (res.get(res.size()-1) == 0) res.remove(res.size()-1);
        return res;
    }


    public static long[] mult(long[] a, long[] b) {

        // Base case - don't wait till 1.
        if (a.length < 32) {
            long[] res = new long[a.length+b.length-1];
            for (int i=0; i<a.length; i++)
                for (int j=0; j<b.length; j++)
                    res[i+j] += (a[i]*b[j]);
            return res;
        }

        long[] aLow = Arrays.copyOf(a, a.length/2);
        long[] bLow = Arrays.copyOf(b, b.length/2);
        long[] aHigh = Arrays.copyOfRange(a, a.length/2, a.length);
        long[] bHigh = Arrays.copyOfRange(b, b.length/2, b.length);

        // Two recursive cases for "low" half and "high" half of poly.
        long[] lowRec = mult(aLow, bLow);
        long[] highRec = mult(aHigh, bHigh);

        // This turns out to be the "middle" part, when we foil.
        // Notice how we use one recursive call instead of two.
        long[] aSum = add(aHigh, aLow);
        long[] bSum = add(bHigh, bLow);
        long[] midRec = mult(aSum, bSum);
        subOut(midRec, lowRec, highRec);

        // Put the result back together, shifting each sub-answer as necessary.
        long[] res = new long[a.length << 1];
        addIn(res, lowRec, 0);
        addIn(res, highRec, a.length);
        addIn(res, midRec, a.length/2);
        return res;
    }

    public static void addIn(int[] total, int[] vals, int shift) {
        for (int i=shift; i<shift+vals.length; i++)
            total[i] += vals[i-shift];
    }

    public static int[] add(int[] a, int[] b) {
        int[] res = new int[Math.max(a.length,b.length)];
        for (int i=0; i<res.length; i++)
            res[i] = a[i] + b[i];
        return res;
    }

    public static void subOut(int[] a, int[] b, int[] c) {
        for (int i=0; i<a.length; i++)
            a[i] -= (b[i] + c[i]);
    }
}
*/