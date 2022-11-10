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

public class GameView extends View {

    private int viewWidth;
    private int viewHeight;
    private int points = 0;
    private Sprite playerBird;
    private Sprite enemyBird;
    private Sprite enemyBird1;
    private Sprite enemyBird2;
    private final int timerInterval = 30;
    protected void update () {
        playerBird.update(timerInterval);
        enemyBird.update(timerInterval);
        enemyBird1.update(timerInterval);
        enemyBird2.update(timerInterval);
        invalidate();
        if (playerBird.getY() + playerBird.getFrameHeight() >
                viewHeight) {
            playerBird.setY(viewHeight -
                    playerBird.getFrameHeight());
            playerBird.setVy(-playerBird.getVy());
            points--;
        }
        else if (playerBird.getY() < 0) {
            playerBird.setY(0);
            playerBird.setVy(-playerBird.getVy());
            points--;
        }
        if (enemyBird.getX() < - enemyBird.getFrameWidth()) {
            teleportEnemy (enemyBird);
            points +=10;
        }
        if (enemyBird.intersect(playerBird)) {
            teleportEnemy (enemyBird);
            points -= 40;
        }

        if (enemyBird1.getX() < - enemyBird1.getFrameWidth()) {
            teleportEnemy (enemyBird1);
            points +=10;
        }
        if (enemyBird1.intersect(playerBird)) {
            teleportEnemy (enemyBird1);
            points -= 40;
        }
        if (enemyBird2.getX() < - enemyBird2.getFrameWidth()) {
            teleportEnemy (enemyBird2);
            points +=10;
        }
        if (enemyBird2.intersect(playerBird)) {
            teleportEnemy (enemyBird2);
            points -= 40;
        }
    }
    private void teleportEnemy (Sprite enemy) {
        enemy.setX(viewWidth + Math.random() * 500);
        enemy.setY(Math.random() * (viewHeight - enemy.getFrameHeight()));
    }


    public GameView(Context context) {
        super(context);
        Bitmap b = BitmapFactory.decodeResource(getResources(),
                R.drawable.player);
        int w = b.getWidth()/5;
        int h = b.getHeight()/3;
        Rect firstFrame = new Rect(0, 0, w, h);
        playerBird = new Sprite(10, 0, 0, 400, firstFrame, b);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (i == 2 && j == 3) {
                    continue;
                }
                playerBird.addFrame(new Rect(j * w, i * h, j * w + w, i
                        * w + w));
            }
        }

        b = BitmapFactory.decodeResource(getResources(),R.drawable.enemy);
        w = b.getWidth()/5;
        h = b.getHeight()/3;
        firstFrame = new Rect(4*w, 0, 5*w, h);
        enemyBird = new Sprite(2000, 250, -300, 0, firstFrame, b);
        for (int i = 0; i < 3; i++) {
            for (int j = 4; j >= 0; j--) {
                if (i ==0 && j == 4) {
                    continue;
                }
                if (i ==2 && j == 0) {
                    continue;
                }
                enemyBird.addFrame(new Rect(j*w, i*h, j*w+w, i*w+w));
            }
        }
        enemyBird1 = new Sprite(2000, 250, -300, 0, firstFrame, b);
        for (int i = 0; i < 3; i++) {
            for (int j = 4; j >= 0; j--) {
                if (i ==0 && j == 4) {
                    continue;
                }
                if (i ==2 && j == 0) {
                    continue;
                }
                enemyBird1.addFrame(new Rect(j*w, i*h, j*w+w, i*w+w));
            }
        }
        enemyBird2 = new Sprite(2000, 250, -300, 0, firstFrame, b);
        for (int i = 0; i < 3; i++) {
            for (int j = 4; j >= 0; j--) {
                if (i ==0 && j == 4) {
                    continue;
                }
                if (i ==2 && j == 0) {
                    continue;
                }
                enemyBird2.addFrame(new Rect(j*w, i*h, j*w+w, i*w+w));
            }
        }

        Timer t = new Timer();
        t.start();
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
        enemyBird.draw(canvas);
        enemyBird1.draw(canvas);
        enemyBird2.draw(canvas);

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
