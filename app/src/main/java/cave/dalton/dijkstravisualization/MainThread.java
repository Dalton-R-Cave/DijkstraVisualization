package cave.dalton.dijkstravisualization;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by dalton on 10/22/16.
 */

public class MainThread extends Thread {

    private static final String TAG = MainThread.class.getSimpleName();

    //Framerate Variables:
    private static final int MAX_FPS = 60; //Max/Desired frames per second
    private static final int MAX_FRAME_SKIPS = 5; //Maximum number of frames that can be skipped
    private static final int FRAME_PERIOD = 1000/MAX_FPS;

    //Display Variables:
    private SurfaceHolder surfaceHolder; //Surface hodler that can access the physical surface
    private View mainView; //The actual view that handles inputs and draws to the surface

    //Flags:
    private boolean running; //flag to hold the current state

    public MainThread(SurfaceHolder surfaceHolder, View mainView){
        super();
        this.surfaceHolder = surfaceHolder;
        this.mainView = mainView;
    }

    @Override
    public void run(){
        Canvas canvas;
        Log.d(TAG, "Starting Main Loop");

        //for fps management
        long beginTime; //the time when the cycle begins
        long timeDiff; //the time it took for the cycle to execute
        int sleepTime = 0; //ms to sleep(< 0 if we're behind)
        int framesSkipped; //number of frames being skipped

        while (running) {
            canvas = null;

            //try locking the canvas for exclusive pixel editing on the surface
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0; //reset the frames skipped
                    mainView.render(canvas); //render state to the screen
                    timeDiff = System.currentTimeMillis() - beginTime;
                    sleepTime = (int) (FRAME_PERIOD - timeDiff); //calculate the sleep time

                    if (sleepTime > 0) { //we are okay, not behind
                        try {
                            //send the thread to sleep to save battery
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            //do nothing
                        }
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) { //We need to catch up
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                }
            } finally {
                //in case of an exception the surface is not left in an inconsitent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public boolean getRunning(){
        return running;
    }

    public void setRunning(boolean running){
        this.running = running;
    }
}
