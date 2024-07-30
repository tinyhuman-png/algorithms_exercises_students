package sorting;

import java.util.*;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,1,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Each entry in the matrix represents an altitude point at the corresponding grid coordinate.
 * The goal is to implement a GlobalWarmingImpl class that extends the GlobalWarming abstract class described below.
 *
 * Given a global water level, all positions in the matrix with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3, all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4), 1 ,(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is nbSafePoints
 * - calculating the number of safe points for a given water level
 *
 * Complete the code below. Pay attention to the expected time complexity of each method.
 * To meet this time complexity, you need to do some pre-computation in the constructor.
 * Feel free to create internal classes in GlobalWarmingImpl to make your implementation easier.
 * Feel free to use any method or data structure available in the Java language and API.
 */

abstract class GlobalWarming {


    protected final int[][] altitude;

    /**
     * @param altitude is a n x n matrix of int values representing altitudes (positive or negative)
     */
    public GlobalWarming(int[][] altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @param waterLevel
     * @return the number of entries in altitude matrix that would be above
     *         the specified waterLevel.
     *         Warning: this is not the waterLevel given in the constructor/
     */
    public abstract int nbSafePoints(int waterLevel);

}


public class GlobalWarmingImpl extends GlobalWarming {

    //    private final TreeMap<Integer, Integer> treemap;
    private int[] altitudeFlat;
    private int nAltitudePoints;

    public GlobalWarmingImpl(int[][] altitude) {
        super(altitude);
        nAltitudePoints = altitude.length * altitude.length;
        // expected pre-processing time in the constructror : O(n^2 log(n^2))

//        first try
//        treemap = new TreeMap<>();
//        for (int i = 0; i < altitude.length; i++) {
//            for (int j = 0; j < altitude[0].length; j++) {
//                if (treemap.containsKey(altitude[i][j])) {
//                    treemap.compute(altitude[i][j], (k, v) -> v + 1);
//                } else {
//                    treemap.put(altitude[i][j], 1);
//                }
//            }
//        }
//
//        int unsafecount = 0;
//        for (Integer key : treemap.keySet()) {
//            unsafecount += treemap.get(key);
//            treemap.replace(key, unsafecount);
//        }

        altitudeFlat = new int[nAltitudePoints];
        for (int i = 0; i < altitude.length; i++) {
            System.arraycopy(altitude[i], 0, altitudeFlat, (i * altitude.length), altitude[0].length);
        }

        altitudeFlat = Arrays.stream(altitudeFlat).sorted().toArray();
    }

    /**
     * Returns the number of safe points given a water level
     *
     * @param waterLevel the level of water
     */
    public int nbSafePoints(int waterLevel) {
        // TODO
        // expected time complexity O(log(n^2))

//        first try
//         return (treemap.floorKey(waterLevel) != null)? nAltitudePoints - treemap.floorEntry(waterLevel).getValue() : nAltitudePoints;

        return nAltitudePoints - (dichotomicSearch(0, nAltitudePoints -1, waterLevel) +1);
    }

    //    return index of the last flooded altitude
    private int dichotomicSearch(int from, int to, int waterlevel){
        int mid = (to +from)/2;
        if (altitudeFlat[mid] == waterlevel) {
//          search that right neighbour is > waterlevel
            while(mid < nAltitudePoints -1 && altitudeFlat[mid+1] == waterlevel) {
                mid++;
            }
            return mid;
        } else if (altitudeFlat[mid] > waterlevel) {
//            we're too far, go search left
            if (mid == 0) return -1;
            return dichotomicSearch(from, mid-1, waterlevel);
        } else if (altitudeFlat[mid] < waterlevel && mid < nAltitudePoints -1 && altitudeFlat[mid+1] > waterlevel) {
//            we found it
            return mid;
        } else {
//            go search right
            if (mid == nAltitudePoints -1) return mid;
            return dichotomicSearch(mid +1, to, waterlevel);
        }
    }



}
