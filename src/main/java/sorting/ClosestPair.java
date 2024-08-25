package sorting;

import java.util.Arrays;

/**
 * Let a be an array of integers.
 * In this exercise we are interested in finding
 * the two entries i and j such that a[i] + a[j] is the closest from a target x.
 * In other words, there are no entries k,l such that |x - (a[i] + a[j])| > |x - (a[k] + a[l])|.
 * Note that we can have i = j.
 * Your program must return the values a[i] and a[j].
 *
 * For example let a = [5, 10, 1, 75, 150, 151, 155, 18, 75, 50, 30]
 *
 * then for x = 20, your program must return the array [10, 10],
 *      for x = 153 you must return [1, 151] and
 *      for x = 170 you must return [18, 151]
 */
public class ClosestPair {

    /**
      * Finds the pair of integers which sums up to the closest value of x
      *
      * @param a the array in which the value are looked for
      * @param x the target value for the sum
      */
    public static int[] closestPair(int [] a, int x) {
//        first try, pas ok car trop lent
        int smallestDiff = Integer.MAX_VALUE;
        int[] indexes = {0, 0};

        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                int value = x - (a[i] + a[j]);
                if (value >= 0 && value <= smallestDiff) {
                    smallestDiff = value;
                    indexes[0] = a[i];
                    indexes[1] = a[j];
                } else if (value < 0 && -value <= smallestDiff) {
                    smallestDiff = -value;
                    indexes[0] = a[i];
                    indexes[1] = a[j];
                }
            }
        }
        return indexes;

//        idee pour faire mieux -> trier la liste et faire comme une recherche dichotomique avec low et high, se rapprocher petit a petit de x  ??
    }
}
