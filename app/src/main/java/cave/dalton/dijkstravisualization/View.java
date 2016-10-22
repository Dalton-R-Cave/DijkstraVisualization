package cave.dalton.dijkstravisualization;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import cave.dalton.dijkstravisualization.MoveableShapes.MoveableCircle;

import static cave.dalton.dijkstravisualization.Util.getDistance;

/**
 * Created by dalton on 10/22/16.
 */

public class View extends SurfaceView implements SurfaceHolder.Callback{

    private static final String TAG = View.class.getSimpleName();

    private MainThread mainThread;
    private MoveableCircle mc;

    private MoveableCircle mc2;

    private CircleButton selectionButton;

    private static final int RADIUS = 100;

    private boolean selectionMode = false;
    private Point[] toConnect;
    private int pointCount;

    public View(Context context){
        super(context);

        mc = new MoveableCircle(100, 100, RADIUS);
        mc2 = new MoveableCircle(200, 200, RADIUS);

        selectionButton = new CircleButton(190, 1320, (int)Math.floor(1.25 * RADIUS), "Edge Selection");
        toConnect = new Point[2];
        pointCount = 0;

        getHolder().addCallback(this);
        mainThread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //do nothing
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");

        boolean retry = true;
        while(retry){
            try{
                mainThread.join();
                retry = false;
            }
            catch(InterruptedException e){
                //retry shutting down the thread
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        selectionMode = selectionButton.handleTouch(event);

        if(mc.handleTouch(event, selectionMode)){
            if(pointCount < 2){
                toConnect[pointCount++] = new Point(mc.getX(), mc.getY());
            }
        }
        if(mc2.handleTouch(event, selectionMode)){
            if(pointCount < 2){
                toConnect[pointCount++] = new Point(mc2.getX(), mc2.getY());
            }
        }

        return true;
    }

    public void render(Canvas canvas){

        //Draw the background
        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);
        if(paint != null){
            canvas.drawPaint(paint);
        }

        //Draw the circle
        mc.draw(canvas);
        mc2.draw(canvas);
        selectionButton.draw(canvas);

        //check to see if we need to draw edges and do so
        drawEdges(canvas);
    }

    private void drawEdges(Canvas canvas){

        if(pointCount >= 2){
            int x1 = toConnect[0].x;
            int x2 = toConnect[1].x;
            int y1 = toConnect[0].y;
            int y2 = toConnect[1].y;
            int dist = (int)Math.floor(getDistance(x1, x2, y1, y2));
            Paint paint = new Paint();
            paint.setColor(Color.MAGENTA);
            paint.setTextSize(30);
            canvas.drawLine(x1, y1, x2, y2, paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(String.valueOf(dist), ((x1 + x2) / 2), ((y1 + y2) / 2), paint);
        }
    }
}
