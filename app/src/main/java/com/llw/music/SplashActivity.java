package com.llw.music;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

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
                    final String[] PERMISSIONS_STORAGE = { "android.permission.READ_EXTERNAL_STORAGE",
                            "android.permission.WRITE_EXTERNAL_STORAGE"};
                    if (Build.VERSION.SDK_INT >= 23){
                        if (ContextCompat.checkSelfPermission(MyApplication.getContext(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                            if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                                AlertDialog.Builder builder=new AlertDialog.Builder(MyApplication.getContext());
                                builder.setCancelable(false)
                                        .setMessage("需要授予权限")
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(MyApplication.getContext(),"点击了取消按钮",Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ActivityCompat.requestPermissions(SplashActivity.this, PERMISSIONS_STORAGE, 1);
                                            }
                                        }).show();
                            }else {
                                ActivityCompat.requestPermissions(SplashActivity.this, PERMISSIONS_STORAGE, 1);
                            }
                        }else {
                            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
//                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//                    startActivity(intent);
//                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length >0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //用户同意授权
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    //用户拒绝授权
                }
                break;
        }
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