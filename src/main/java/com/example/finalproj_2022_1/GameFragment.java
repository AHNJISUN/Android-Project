package com.example.finalproj_2022_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;


public class GameFragment extends Fragment {

    ArrayList<Point> list;
    ArrayList<Bitmap> imgList;
    Bitmap bitAns;
    Point posAns;
    boolean isPika = false;
    int score = 0;
    int src[] = new int[]{R.drawable.pikachu, R.drawable.eevee, R.drawable.emolga, R.drawable.piplup, R.drawable.snorlax, R.drawable.eevee, R.drawable.emolga, R.drawable.piplup, R.drawable.snorlax,R.drawable.eevee, R.drawable.emolga, R.drawable.piplup, R.drawable.snorlax,R.drawable.eevee, R.drawable.emolga, R.drawable.piplup, R.drawable.snorlax,R.drawable.eevee, R.drawable.emolga, R.drawable.piplup, R.drawable.snorlax};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        MyView mv = new MyView(getContext());

        return mv;
    }

    private class MyView extends View {
        public MyView(Context context) {
            super(context);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (score <= 10000) {
                        SystemClock.sleep(3000);
                        invalidate();
                    }
                }
            }.start();
        }
        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            Paint paint = new Paint();
            paint.setTextSize(85);
            paint.setColor(Color.rgb(100, 100, 0));

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int dWidth = metrics.widthPixels;
            int dHeight = metrics.heightPixels;

            Random random = new Random();
            list = new ArrayList<>();
            imgList = new ArrayList<>();

            for (int i = 0; i <= 20; i++) {
                Point pos = new Point(random.nextInt(dWidth), random.nextInt(dHeight));
                list.add(pos);
            }

            for(int i : src){
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), i);
                if(R.drawable.emolga == i || R.drawable.piplup == i){
                    bitmap = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                }
                else{
                    bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                    if(R.drawable.pikachu == i) bitAns = bitmap;
                }
                imgList.add(bitmap);
            }

            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.back3),0,0,null);
            canvas.drawText("score: " + score, 100, 100, paint);

            int listIdx=0;

            for (Point pos : list){
                if(isPika) listIdx = random.nextInt(20);
                else listIdx = 0;
                if(listIdx == 0) posAns = pos;

                canvas.drawBitmap(imgList.get(listIdx), pos.x, pos.y, paint);
                isPika = true;
            }
        }
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if ((event.getX() >= posAns.x && event.getX() <= posAns.x + 200)
                            && (event.getY() >= posAns.y && event.getY() <= posAns.y + 200)) {
                        score = score+100;
                        isPika = false;
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}