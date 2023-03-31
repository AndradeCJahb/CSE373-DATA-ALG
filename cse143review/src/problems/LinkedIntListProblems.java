package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        list.front.next.next.next = list.front.next;
        ListNode temp = list.front;
        list.front = list.front.next.next;
        list.front.next.next = temp;
        temp.next = null;
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {

        ListNode temp = list.front;
        if (temp != null) {
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = list.front;
            list.front = list.front.next;
            temp.next.next = null;
        }
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {

        if (a.front != null) {
            ListNode frontNew = new ListNode(a.front.data);
            LinkedIntList ret = new LinkedIntList(frontNew);


            ListNode temp = a.front;
            while (temp.next != null) {
                frontNew.next = new ListNode(temp.next.data);
                frontNew = frontNew.next;
                temp = temp.next;
            }
            frontNew.next = b.front;
            return ret;
        } else {
            return b;
        }
    }
}
