package com.droid.firmware.kasku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Splashscreen extends AppCompatActivity {

    TextView text;
    Animation frombottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Thread timer = new Thread(){
            public void run(){
                try {
                  sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };
        timer.start();

        text = (TextView) findViewById(R.id.my_text);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        text.setAnimation(frombottom);
    }
}
