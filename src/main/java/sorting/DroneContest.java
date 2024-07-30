package sorting;
import java.util.*;
/**
 * Context
 * --------
 * You have been contacted by an organizer of a drone artistic figure contest, because he is facing a problem that is a bit too complicated for his Excel sheet.
 * During the contest, several participants are going to make exhibitions of their drone and beautiful figures. Each participant had to reserve a time slot
 * before the contest and indicate the maximum height at which his drone will fly during his performance.
 * As these are powerful drones, they can fly very high, and this can cause problems for civil aviation. The organization in charge of the Belgian airspace,
 * Skeyes, therefore asks you to send them the maximum height used by the drones at any given time (the "profile").
 * You want it to be as accurate as possible, because any reservation costs money...
 *
 * Example
 * -------
 * Let's take an example. Let's have the following reservations:
 *
 * +-------+-------+------+----------+
 * | Drone | Start | Stop | Altitude |
 * +-------+-------+------+----------+
 * | A     | 1     | 5    | 3        |
 * +-------+-------+------+----------+
 * | B     | 3     | 12   | 5        |
 * +-------+-------+------+----------+
 * | C     | 6     | 14   | 1        |
 * +-------+-------+------+----------+
 * | D     | 7     | 15   | 4        |
 * +-------+-------+------+----------+
 * | E     | 15    | 18   | 5        |
 * +-------+-------+------+----------+
 * | F     | 16    | 20   | 1        |
 * +-------+-------+------+----------+
 * | G     | 17    | 19   | 2        |
 * +-------+-------+------+----------+
 *
 * With these reservations, the profile is as follows:
 *
 * t ∈ [0,1[   ->  altitude = 0
 * t ∈ [1,3[   ->  altitude = 3
 * t ∈ [3,12[  ->  altitude = 5
 * t ∈ [12,15[ ->  altitude = 4
 * t ∈ [15,18[ ->  altitude = 5
 * t ∈ [18,19[ ->  altitude = 2
 * t ∈ [19,20[ ->  altitude = 1
 * t ∈ [20,∞[  ->  altitude = 0
 *
 * Details
 * -------
 * You need to implement a public static LinkedList<HeightChange> findHighest(Drone[] participants) method that takes as input an array of
 * Drone participants in the contest. Each drone is defined by a takeoff time (drone.start), landing time (drone.end) and a flight height (drone.height).
 * The drone is considered to be in flight during [drone.start,drone.end[.
 * You have the following properties:
 *
 *  1 ≤ drone.start < drone.end;
 *  1 ≤ drone.height;
 *  1 ≤ participants.length ≤100000.
 *
 *  As output, you need to provide the profile of the drones height as a function of time.
 *  This is a sequence of HeightChange objects that indicate that from the time change.time, the new maximum altitude is change.height.
 *  The HeightChange objects must be sorted by time (from smallest to largest time). The first change must have change.time=0.
 *  The last change must be when the last drone lands. Two successive changes must have different height.
 *
 *  Your profile must be optimal. In other words, given two successive changes a and b,
 *  the maximum altitude of drones flying between a.time (inclusive) and b.time (exclusive) is EXACTLY a.height.
 *  Therefore, there is a drone flying at altitude a.height between these two times.
 *
 */
public class DroneContest {

    /**
     * Given an array of participants (that starts their drones at a time given by drone.start (inclusive),
     * stops it at drone.end (exclusive) and goes at height drone.height),
     * output the heights changes for the use of Skeyes.
     * <p>
     * The first drone takes off strictly somewhere after time 0.
     * <p>
     * The height changes must be as described on INGInious.
     * Equivalently, as follows:
     * - They must be ordered by time
     * - The first height change must be at time 0, and at height 0.
     * - The last height change must be at the time the last drone stops (and thus must be at height 0!)
     * - Given two successive height changes A and B, the maximum height of any active drone between A.time (inclusive)
     * and B.time (exclusive) is EXACTLY A.height (i.e. there exists a drone with this height active between these
     * times). Moreover, A.height != B.height.
     */

    public static LinkedList<HeightChange> findHighest(Drone[] participants) {
        // TODO

        ArrayList<HeightChange> workingList = new ArrayList<>();
        workingList.add(new HeightChange(0,0));
        for (Drone drone : participants) {
            HeightChange current = workingList.get(0);
            int index = 1;
            while(index != workingList.size() && drone.start > current.time) {
                current = workingList.get(index);
                index++;
            }

            //here workingList[index] is the first to be > drone.start,
            //or every time is < drone.start and we made it to the end of list

        }

        return null;





//        Stack<HeightChange> principal_heap = new Stack<>();
//        Stack<HeightChange> secondary_heap = new Stack<>();
//
//        principal_heap.push(new HeightChange(0,0));
//
//        for (Drone drone : participants) {
//            HeightChange current, previous;
//            if (principal_heap.size() == 1) {
//                principal_heap.push(new HeightChange(drone.start, drone.height));
//                principal_heap.push(new HeightChange(drone.end,0));
//                continue;
//            }
//
//            do {
//                current = principal_heap.peek();
//                secondary_heap.push(principal_heap.pop());
//                previous = principal_heap.peek();
//            } while (drone.start < previous.time);
//
//            // here drone.start >= previous.time
//            if (drone.height > previous.height) {
//                //start
//                if (drone.start == previous.time) {
//                    principal_heap.pop();
//                }
//                principal_heap.push(new HeightChange(drone.start, drone.height));
//
//                //end
//                if (drone.end < current.time) {
//                    principal_heap.push(new HeightChange(drone.end, previous.height));
//                }
//            }
//
//            secondary_heap.pop();
//            if (!secondary_heap.isEmpty()) {
//                HeightChange temp = current;
//                current = secondary_heap.peek();
//                previous = temp;
//                while (drone.end >= current.time) {
//                    if (drone.height > previous.height) {
//                        //start
//                        if (drone.start == previous.time) {
//                            secondary_heap.pop();
//                        } else {
//                            principal_heap.push(secondary_heap.pop());
//                        }
//                        principal_heap.push(new HeightChange(drone.start, drone.height));
//
//                        //end
//                        if (drone.end < current.time) {
//                            principal_heap.push(new HeightChange(drone.end, previous.height));
//                        }
//                        if (secondary_heap.isEmpty()) break;
//
//                        previous = new HeightChange(drone.start, drone.height);
//                        current = secondary_heap.peek();
//                    } else if (drone.height < previous.height) {
//                        principal_heap.push(secondary_heap.pop());
//                        if (secondary_heap.isEmpty()) break;
//
//                        previous = current;
//                        current = secondary_heap.peek();
//                    } else {
//                        secondary_heap.pop();
//                        if (secondary_heap.isEmpty()) break;
//
//                        previous = current;
//                        current = secondary_heap.peek();
//                    }
//                }
//
//                if (!secondary_heap.isEmpty()) {
//                    while(!secondary_heap.isEmpty()) {
//                        principal_heap.push(secondary_heap.pop());
//                    }
//                }
//            }
//
//            if (drone.end >= current.time) {
//                if (drone.end > current.time && drone.height != previous.height) {
//                    principal_heap.push(new HeightChange(current.time, drone.height));
//                }
//                principal_heap.push(new HeightChange(drone.end, 0));
//            }
//
//            //here either secondary heap is empty while drone.end >= current.time (current is the last interval)
//        }
//
//        LinkedList<HeightChange> heightChanges = new LinkedList<>();
//
//        while (!principal_heap.isEmpty()) {
//            heightChanges.addLast(principal_heap.pop());
//        }
//        return heightChanges;
    }

}

class HeightChange {
    public int time;
    public int height;

    public HeightChange(int t, int h) {
        time = t; height = h;
    }
    public String toString() {
        return "Time: " + time + ", Height: " + height;
    }
}

class Drone {
    public final int start;
    public final int end;
    public final int height;

    public Drone(int s, int e, int h) {
        start = s; end = e; height = h;
    }

}
