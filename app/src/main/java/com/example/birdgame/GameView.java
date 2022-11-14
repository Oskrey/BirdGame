package com.example.birdgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameView extends View {

    private int viewWidth;
    private int viewHeight;
    private int points = 0;
    private Sprite playerBird;
    private final int timerInterval = 30;
    List<Sprite> list = new ArrayList<Sprite>();
    public void createEnemy(int count){
        for(int birdCount =0;birdCount<count;birdCount++)
        {
            Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.enemy);
            int w = b.getWidth()/5;
            int h = b.getHeight()/3;
            Rect firstFrame = new Rect(4*w, 0, 5*w, h);
            Sprite name = new Sprite(2000, 250, -300, 0, firstFrame, b);
            for (int i = 0; i < 3; i++) {
                for (int j = 4; j >= 0; j--) {
                    if (i ==0 && j == 4) {
                        continue;
                    }
                    if (i ==2 && j == 0) {
                        continue;
                    }
                    name.addFrame(new Rect(j*w, i*h, j*w+w, i*w+w));
                }
            }
            list.add(name);
        }
    }
    public GameView(Context context) {
        super(context);
        createBird();
        createEnemy(10);

        Timer t = new Timer();
        t.start();
    }
    protected void update () {
        playerBird.update(timerInterval);

        for (Sprite sp:list) {
            sp.update(timerInterval);
        }

        invalidate();
        if (playerBird.getY() + playerBird.getFrameHeight() >
                viewHeight) {
            playerBird.setY(viewHeight -  playerBird.getFrameHeight());
            playerBird.setVy(-playerBird.getVy());
            points--;

        }
        if (playerBird.getY() < 0) {
            playerBird.setY(0);
            playerBird.setVy(-playerBird.getVy());
            points--;
        }
        for (Sprite enemy:list) {
            if (enemy.getX() < - enemy.getFrameWidth()) {
                teleportEnemy (enemy);
                points +=10;
            }
            if (enemy.intersect(playerBird)) {
                teleportEnemy (enemy);
                points -= 40;
            }
        }

    }
    private void teleportEnemy (Sprite enemy) {
        enemy.setX(viewWidth + Math.random() * 500);
        enemy.setY(Math.random() * (viewHeight - enemy.getFrameHeight()));
    }

    private void createBird(){
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.player);
        int w = b.getWidth()/5;
        int h = b.getHeight()/3;
        Rect firstFrame = new Rect(0, 0, w, h);
        playerBird  = new Sprite(10, 0, 0, 400, firstFrame, b);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (i == 2 && j == 3) {
                    continue;
                }
                playerBird.addFrame(new Rect(j * w, i * h, j * w + w,
                        i* w + w));
            }
        }
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(250, 127, 199, 255); // заливаем цветом
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setTextSize(55.0f);
        p.setColor(Color.WHITE);

        canvas.drawText(points+"", viewWidth - 100, 70, p);

        playerBird.draw(canvas);
        for (Sprite enemy:list
             ) {
            enemy.draw(canvas);

        }


    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();
        if (eventAction == MotionEvent.ACTION_DOWN) {
            playerBird.setVy(playerBird.getVy() *-1);
        }
        return true;
    }

    class Timer extends CountDownTimer {
        public Timer() {
            super(Integer.MAX_VALUE, timerInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            update();
        }
        @Override
        public void onFinish() {
        }}

}
