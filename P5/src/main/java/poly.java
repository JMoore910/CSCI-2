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

    //  in main:
    //  Get input:
    //  n
    //  poly 1 is of size 2^n
    //  poly 2 is of size 2^n

    //  start timer and run slowmult using n^2 algorithm

    //  output end - start to show total time of base case

    //

    //  Read in two lines as strings, then split them into an array of longs


    //  add polys method
    //  takes two polynomials, must be of the same length
    //  create array of longs, loop through the two polys,
    //  add the values into the array of longs until thru
    //  return the array of longs as a new polynomial

    //  sub polys method
    //  takes two polynomials, must be of the same length
    //  create array of longs, loop through the two polys,
    //  sub the values into the array of longs until thru
    //  return the array of longs as a new polynomial

    //  multSlow method
    //  Only run this for the base case to compare against
    //  move through each element of the first polynomial, multiply it to every other element of the other polynomial,
    //  and add them into the degree in which they fit aka 10^x
    //  for (int i = 0; i < first.getLength(); i++) {
    //      for (int j = 0; j < second.getLength(); j++)
    //
    //
    //

    //  mult method
    //  Split the polynomials in half into polys a, b, c, d
    //  a = getLeft(first)
    //  b = getRight(first)
    //  c = getLeft(second)
    //  d = getRight(second)

    //  If the split polynomials have reached single elements, then run karatsuba's alg
    //  the two polys MUST BE SAME SIZE
    //  on the individual


    //  longmult method
    //  split two longs in half by digits into: a, b, c, d longs
    //  long ac, bd, md
    //
    //  get num of digits in first and second input longs, store as n and m
    //  n = max(n , m)
    //  ac = longmult(a,c)
    //  bd = longmult(b,d)
    //  md = longmult(a+b,c+d) - (ac + bd)
    //  res = ac * 10^n + md * 10^n/2 + bd

    //  return result
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

    //  Create a method that adds a long value to the coefficients
    public void addCoeff(int i, long x) {
        coeff[i] += x;
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
            vals[i] = coeff[i];
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