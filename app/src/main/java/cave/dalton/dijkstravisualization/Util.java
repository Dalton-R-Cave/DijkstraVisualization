package cave.dalton.dijkstravisualization;

/**
 * Created by dalton on 10/22/16.
 */

public class Util {

    public static double getDistance(int x1, int x2, int y1, int y2){
        return Math.sqrt(squared((x2 - x1)) + squared(y2 - y1));
    }

    public static double squared(double num){
        return num*num;
    }
}
