package vn.edwardle.alwaysdict;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class HeadService extends Service {
    WindowManager windowManager;
    ImageView imageView;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        imageView=new ImageView(this);
        imageView.setImageResource(R.drawable.ic_launcher);
        final WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams(
             WindowManager.LayoutParams.WRAP_CONTENT,
             WindowManager.LayoutParams.WRAP_CONTENT,
             WindowManager.LayoutParams.TYPE_PHONE,
             WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
             PixelFormat.TRANSLUCENT
        );
        imageView.setOnTouchListener(new View.OnTouchListener() {
            private int initX,initY;
            private float initTouchX,initTouchY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        initX=layoutParams.x;
                        initY=layoutParams.y;
                        initTouchX=event.getRawX();
                        initTouchY=event.getRawY();
                    return true;
                    case MotionEvent.ACTION_UP:
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        layoutParams.x=initX+(int)(event.getRawX()-initTouchX);
                        layoutParams.y=initY+(int)(event.getRawY()-initTouchY);
                        windowManager.updateViewLayout(imageView,layoutParams);
                    return true;
                }
                return false;
            }
        });
        layoutParams.gravity= Gravity.TOP | Gravity.LEFT;
        layoutParams.x=0;
        layoutParams.y=100;

        windowManager.addView(imageView,layoutParams);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(imageView!=null){
            windowManager.removeView(imageView);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
