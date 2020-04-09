package com.saifi.shoppurchase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.saifi.shoppurchase.constants.SessonManager;
import com.saifi.shoppurchase.constants.Url;
import com.saifi.shoppurchase.retrofitmodel.LoginModel;
import com.saifi.shoppurchase.retrofitmodel.ResponseError;
import com.saifi.shoppurchase.service.ApiInterface;
import com.saifi.shoppurchase.util.Views;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText editTextMobile, editTextPassword;
    ImageView imgCross;
    SessonManager sessonManager;
    Views views;
    RadioGroup radioLogin;
    String UserType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        sessonManager = new SessonManager(getApplicationContext());
        views = new Views();

        loginButton = findViewById(R.id.loginButton);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextPassword = findViewById(R.id.editTextPassword);
        imgCross = findViewById(R.id.imgCross);
        radioLogin = findViewById(R.id.radioLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                finishAffinity();
                if (editTextMobile.getText().toString().isEmpty()) {
                    editTextMobile.setError("Can't be Blank");
                    editTextMobile.requestFocus();
                } else if (editTextMobile.getText().toString().length() != 10) {
                    editTextMobile.setError("MObile no. should be 10 digits");
                    editTextMobile.requestFocus();
                } else if (editTextPassword.getText().toString().isEmpty()) {
                    editTextPassword.setError("Can't be Blank");
                    editTextPassword.requestFocus();
                }
                else if(UserType.equals("")){
                    Toast.makeText(LoginActivity.this, "Please Select User or Managaer", Toast.LENGTH_SHORT).show();
                }
                else {
                    hitApi();
                }

            }
        });
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        radioLogin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedButton = radioLogin.findViewById(i);
                boolean checked = checkedButton.isChecked();
                if(checked){
                    if(checkedButton.getText().toString().equals("User")) {
                        UserType = "User";
                    }
                    if(checkedButton.getText().toString().equals("Manager")) {
                        UserType = "Manager";
                    }
                }
            }
        });
    }

    private void hitApi() {
        views.showProgress(LoginActivity.this);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<LoginModel> call = api.hitLogin(Url.key, editTextMobile.getText().toString(),
                editTextPassword.getText().toString(), "shop");

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                views.hideProgress();
                if (response.isSuccessful()) {
                    LoginModel loginModel = response.body();

                    if (loginModel.getCode().equals("200")) {
                        views.showToast(getApplicationContext(), loginModel.getMsg());

                        String name = loginModel.getName();
                        String mobile = loginModel.getMobile();
                        int userId = loginModel.getUserid();
                        sessonManager.setToken(String.valueOf(userId));
                        sessonManager.setMobile(mobile);
                        sessonManager.setUserName(name);
                        sessonManager.setUserType(UserType);

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finishAffinity();
                    }
                    else {
                       // Gson gson = new GsonBuilder().create();
//                        ResponseError responseError = gson.fromJson(gson.toJson(loginModel.getMsg()),ResponseError.class);
                        views.showToast(getApplicationContext(), ""+loginModel.getMsg());
                    }
                }
                else
                {
                    Gson gson = new GsonBuilder().create();
                    ResponseError responseError = gson.fromJson(response.errorBody().charStream(),ResponseError.class);
                    views.showToast(getApplicationContext(), responseError.getMsg());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                views.showToast(getApplicationContext(), t.getMessage());

            }
        });
    }
}
