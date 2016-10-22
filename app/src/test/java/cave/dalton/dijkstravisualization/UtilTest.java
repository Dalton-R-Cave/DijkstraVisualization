package cave.dalton.dijkstravisualization;

import org.junit.Test;

import static cave.dalton.dijkstravisualization.Util.getDistance;
import static cave.dalton.dijkstravisualization.Util.squared;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by dalton on 10/22/16.
 */

public class UtilTest {

    @Test
    public void testGetDistance(){

        int x1 = 1;
        int y1 = 1;
        int x2 = 2;
        int y2 = 1;

        assertEquals(1.0, getDistance(x1, x2, y1, y2));
    }

    @Test
    public void testSquared(){
        double x = 2;

        assertEquals(4.0, squared(x));

        x = 2.5;

        assertEquals(6.25, squared(x));

        assertTrue(7 > squared(x));
    }

}
