package com.llw.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.llw.music.databinding.ActivitySplashBinding;

import static java.lang.Thread.sleep;


public class SplashActivity extends AppCompatActivity {

    //位移动画
    private TranslateAnimation translateAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
//        initView();
//        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//        startActivity(intent);
        Thread myThread = new Thread(){

            @Override
            public void run(){
                try {
                    sleep(1500);
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();

    }

    /**
     * 初始化
     */
//    private void initView() {
//        /**在build.gradle中要加入使用dataBinding
//         android {
//         ...
//         dataBinding {
//         enabled = true
//         }
//         ...
//         }*/
//        ActivitySplashBinding binding = DataBindingUtil.setContentView(SplashActivity.this, R.layout.activity_splash);
//        TextView tvTranslate = binding.tvTranslate;
//
//        tvTranslate.post(new Runnable() {
//
//            @Override
//            public void run() {
//                //通过post拿到的tvTranslate.getWidth()不会为0。
//                translateAnimation = new TranslateAnimation(0, tvTranslate.getWidth(), 0, 0);
//                translateAnimation.setDuration(1000);
//                translateAnimation.setFillAfter(true);
//                tvTranslate.startAnimation(translateAnimation);
//
//                //动画监听
//                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        //动画结束时跳转到主页面
//                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//            }
//        });
//    }
}