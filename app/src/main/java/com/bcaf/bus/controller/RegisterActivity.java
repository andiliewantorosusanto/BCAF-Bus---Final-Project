package com.bcaf.bus.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.bcaf.bus.R;
import com.bcaf.bus.model.user.ResponseUser;
import com.bcaf.bus.model.user.User;
import com.bcaf.bus.network.BaseApiService;
import com.bcaf.bus.network.RetrofitInstance;
import com.bcaf.bus.session.MySession;
import com.bcaf.bus.utils.MyUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private String FirstNameHolder,LastNameHolder,EmailHolder,PasswordHolder,RePasswordHolder,MobileNumberHolder;
    private TextInputEditText tietFirstName,tietLastName,tietEmail,tietPassword,tietRePassword,tietMobileNumber;
    private Button btnRegister;

    private BaseApiService baseApiService;
    private MySession session;
    private MyUtils customUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        session = new MySession(RegisterActivity.this);
        customUtils = new MyUtils(RegisterActivity.this);
        baseApiService = RetrofitInstance.getRetrofitInstance("").create(BaseApiService.class);

        tietFirstName = findViewById(R.id.inp_firstName);
        tietLastName = findViewById(R.id.inp_lastName);
        tietEmail = findViewById(R.id.inp_email);
        tietPassword = findViewById(R.id.inp_password);
        tietRePassword = findViewById(R.id.inp_rePassword);
        tietMobileNumber = findViewById(R.id.inp_mobileNumber);

        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(v -> btnRegisterEvent());
    }

    private void btnRegisterEvent() {
        assignValue();

        if(!isError()) {
            register();
        }
    }

    private void assignValue() {
        FirstNameHolder = tietFirstName.getText().toString();
        LastNameHolder = tietLastName.getText().toString();
        EmailHolder = tietEmail.getText().toString();
        PasswordHolder = tietPassword.getText().toString();
        RePasswordHolder = tietRePassword.getText().toString();
        MobileNumberHolder = tietMobileNumber.getText().toString();
    }

    private boolean isError() {
        String error = "";

        if(!isPasswordMatch()) {
            error = "Password tidak sama";
        } else if(!isRequiredFieldFilled()) {
            error = "Form masih ada yang kosong";
        }

        if(error.equals("")) {
            return false;
        } else {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private boolean isPasswordMatch() {
        return PasswordHolder.equals(RePasswordHolder);
    }

    private boolean isRequiredFieldFilled() {
        return !(TextUtils.isEmpty(FirstNameHolder)
                || TextUtils.isEmpty(LastNameHolder)
                || TextUtils.isEmpty(EmailHolder)
                || TextUtils.isEmpty(PasswordHolder)
                || TextUtils.isEmpty(RePasswordHolder)
                || TextUtils.isEmpty(MobileNumberHolder));
    }

    private void routeLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void register(){
        try {
            runOnUiThread(() -> {
                Map<String, Object> jsonParams = new ArrayMap<>();
                jsonParams.put("email", EmailHolder);
                jsonParams.put("password", PasswordHolder);
                jsonParams.put("firstName", FirstNameHolder);
                jsonParams.put("lastName", LastNameHolder);
                jsonParams.put("mobileNumber", MobileNumberHolder);
                jsonParams.put("roles","['ROLE_USER','ROLE_ADMIN']");

                RequestBody body = RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),(
                                new JSONObject(jsonParams)).toString());

                Call<User> callUserList = baseApiService.register(body);
                callUserList.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        try {
                            if (response.body() != null){
                                Toast.makeText(RegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                                routeLogin();
                            }else {
                                Toast.makeText(getApplicationContext(), "Terjadi kesalahan saat register", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.wtf("Error : ",e.getMessage());
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                        Log.wtf("Failure : ",t.getMessage());
                    }
                });
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.wtf("Error Exception : ",e.getMessage());
        }
    }
}