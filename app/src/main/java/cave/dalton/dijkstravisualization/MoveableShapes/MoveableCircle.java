package cave.dalton.dijkstravisualization.MoveableShapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import static cave.dalton.dijkstravisualization.Util.getDistance;

/**
 * Created by dalton on 10/22/16.
 */

public class MoveableCircle {

    private static final String TAG = MoveableCircle.class.getSimpleName();

    private int x, y, radius;
    private boolean pickedUp;
    private int color;

    public MoveableCircle(int x, int y, int radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = Color.RED;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getRadius(){
        return radius;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    /*Any point contained within the circle must be
     *have a distance from the center that is less
     * than or equal to the radius
     */
    public boolean contains(int xCo, int yCo){
        double dist = getDistance(x, xCo, y, yCo);
        return (dist <= radius);
    }

    public boolean getPickedUp(){
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp){
        this.pickedUp = pickedUp;
    }

    public int getColor(){
        return color;
    }

    public void setColor(int color){
        this.color = color;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(x, y, radius, paint);
    }

    public boolean handleTouch(MotionEvent event, boolean selectionMode){
        int eX = (int)event.getX();
        int eY = (int)event.getY();

        if(pickedUp && !selectionMode){
            setLocation(eX, eY);
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(contains(eX, eY)){
                if(selectionMode){
                    setColor(Color.GREEN);
                    return true;
                }
                else {
                    pickedUp = true;
                    setColor(Color.BLUE);
                }
            }
        }

        if(event.getAction() == MotionEvent.ACTION_UP){
            if(pickedUp){
                setColor(Color.RED);
                pickedUp = false;
            }
        }

        return false;
    }
}
