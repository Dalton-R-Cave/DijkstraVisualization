package cave.dalton.dijkstravisualization;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import static cave.dalton.dijkstravisualization.Util.getDistance;

/**
 * Created by dalton on 10/22/16.
 */

public class CircleButton {

    private static final String TAG = CircleButton.class.getSimpleName();

    private static final int TEXT_SIZE = 35;
    private static final int OFFSET_PER_PIXEL = 17;

    private int x;
    private int y;
    private int textXOffset, textYOffset;
    private int radius;
    private int color;
    private int textColor;
    private boolean toggle;

    private String text;

    public CircleButton(int x, int y, int radius, String text){
        this.x = x;
        this.y = y;
        textYOffset = TEXT_SIZE / 2;
        this.radius = radius;
        setText(text);
        color = Color.GRAY;
        textColor = Color.BLACK;
        toggle = false;
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

    public int getColor(){
        return color;
    }

    public String getText(){
        return text;
    }

    public boolean getToggle(){
        return toggle;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    public void setColor(int color){
        this.color = color;
    }

    public void setText(String text){
        this.text = text;
        if(text.length() == 1){
            textXOffset = 1;
        }
        else{
            textXOffset = text.length() / 2;
        }
    }

    public void setToggle(boolean toggle){
        this.toggle = toggle;
    }


    public boolean contains(int xCo, int yCo){
        double dist = getDistance(x, xCo, y, yCo);
        return (dist <= radius);
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(x, y, radius, paint);
        paint.setColor(textColor);
        paint.setTextSize(TEXT_SIZE);
        canvas.drawText(text, x-(textXOffset * OFFSET_PER_PIXEL), y + textYOffset, paint);
    }

    public boolean handleTouch(MotionEvent event){
        int eX = (int)event.getX();
        int eY = (int)event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            //We need to set a toggle a flag to enter "selection mode"
            if(contains(eX, eY)) {
                toggle = !toggle;
                Log.d(TAG, "Toggle: " + toggle);
            }
        }

        return toggle;
    }
}
