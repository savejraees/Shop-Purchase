package com.saifi.shoppurchase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.saifi.shoppurchase.constants.SessonManager;
import com.saifi.shoppurchase.constants.Url;
import com.saifi.shoppurchase.retrofitmodel.StatusModel;
import com.saifi.shoppurchase.service.ApiInterface;
import com.saifi.shoppurchase.util.Views;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {

  SessonManager sessonManager;
  String token;
  Views views;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessonManager = new SessonManager(SplashActivity.this);
        views = new Views();
        token = sessonManager.getToken();

        getSupportActionBar().hide();

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation animation_2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.antirotate);
        final Animation animation_3 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);
        imageView.startAnimation(animation_2);
        animation_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animation_1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation_1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animation_3);
                if(token.isEmpty() || token==null || token.equals("")){
                    finish();
                    Intent i = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(i);
                }else {
                    hitStatusApi();
                }

            }

            private void hitStatusApi() {
                views.showProgress(SplashActivity.this);

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Url.BASE_URL)
                        .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(
                                HttpLoggingInterceptor.Level.BODY
                        ))
                                .build())
                        .build();

                ApiInterface api = retrofit.create(ApiInterface.class);
                Call<StatusModel> call = api.hitStatusApi(Url.key,sessonManager.getToken());
                call.enqueue(new Callback<StatusModel>() {
                    @Override
                    public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                        views.hideProgress();
                        if(response.isSuccessful()){
                            StatusModel statusModel = response.body();
                            if(statusModel.getCode().equalsIgnoreCase("200")){
                                views.showToast(getBaseContext(),statusModel.getMsg());
                                finish();
                                Intent i = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(i);
                            }
                            else {
                                views.showToast(getBaseContext(),statusModel.getMsg());
                                finish();
                                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                                startActivity(i);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusModel> call, Throwable t) {
                        views.hideProgress();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}