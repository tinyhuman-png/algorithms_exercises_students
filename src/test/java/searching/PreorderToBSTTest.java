package searching;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.javagrader.GradeFeedback;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;



@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class PreorderToBSTTest {


    @Test
    @Grade(value=1, cpuTimeout = 1000)
    @GradeFeedback(message="Sorry, something is wrong with your algorithm. Debug first on this small example")
    @Order(1)
    public void testSimple() {
        int[] preOrderInput = {10,5,7,14,12};
        PreorderToBST student = new PreorderToBST(preOrderInput);
        PreorderToBST teacher = new PreorderToBST();
        teacher.root = new PreorderToBST.Node(new PreorderToBST.Node(null, new PreorderToBST.Node(null, null, 7), 5), new PreorderToBST.Node(new PreorderToBST.Node(null, null, 12), null, 14), 10);
        assertEquals(teacher, student);

    }

    @Test
    @Grade(value=1, cpuTimeout = 1000)
    @GradeFeedback(message="Sorry, something is wrong with your algorithm. Debug first on this small example")
    @Order(1)
    public void testSimple2() {
        int[] preOrderInput = {10,5,3,1,4,7,6,8,15,12,11,13,19,17,23};
        PreorderToBST student = new PreorderToBST(preOrderInput);
        PreorderToBST teacher = new PreorderToBST();
        teacher.root = new PreorderToBST.Node(new PreorderToBST.Node(new PreorderToBST.Node(new PreorderToBST.Node(null, null, 1), new PreorderToBST.Node(null, null, 4), 3), new PreorderToBST.Node(new PreorderToBST.Node(null, null, 6), new PreorderToBST.Node(null, null, 8), 7), 5), new PreorderToBST.Node(new PreorderToBST.Node(new PreorderToBST.Node(null, null, 11), new PreorderToBST.Node(null, null, 13), 12), new PreorderToBST.Node(new PreorderToBST.Node(null, null, 17), new PreorderToBST.Node(null, null, 23), 19), 15), 10);
        assertEquals(teacher, student);

    }

}
