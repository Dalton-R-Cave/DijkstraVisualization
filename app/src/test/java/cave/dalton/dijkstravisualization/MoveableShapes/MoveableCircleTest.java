package cave.dalton.dijkstravisualization.MoveableShapes;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by dalton on 10/22/16.
 */

public class MoveableCircleTest {


    @Test
    public void testContains(){
        int x = 5;
        int y = 7;
        int radius = 2;
        int x1 = 5;
        int y1 = 7;
        MoveableCircle mc = new MoveableCircle(x, y, radius);

        boolean check = mc.contains(x1, y1);

        assertTrue(check);

        check = mc.contains(6, 7);

        assertTrue(check);

        check = mc.contains(8, 7);

        assertFalse(check);
    }
}
