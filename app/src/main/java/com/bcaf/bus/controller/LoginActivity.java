package com.bcaf.bus.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bcaf.bus.R;
import com.bcaf.bus.model.user.ResponseUser;
import com.bcaf.bus.model.user.User;
import com.bcaf.bus.network.BaseApiService;
import com.bcaf.bus.network.RetrofitInstance;
import com.bcaf.bus.session.MySession;
import com.bcaf.bus.utils.MyUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private String PasswordHolder, EmailHolder;
    private TextInputEditText edUser, edPass;
    private Button bLogin;
    private Boolean CheckEditText;
    private MySession session;
    private TextView txtRegister;

    private BaseApiService baseApiService;
    private User listUser;
    private String xToken = "";

    private MyUtils customUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Session
        session = new MySession(LoginActivity.this);
        //Utils
        customUtils = new MyUtils(LoginActivity.this);
        //Api Service use Retrofit
        baseApiService = RetrofitInstance.getRetrofitInstance("").create(BaseApiService.class);

        edUser = findViewById(R.id.inp_email);
        edPass = findViewById(R.id.inp_password);
        bLogin = findViewById(R.id.btn_login);
        txtRegister = findViewById(R.id.txt_register);

        //Check Login
        if (session.isLoggedIn()) {
            //Get Session
            HashMap<String, String> sUsernya = session.getUserDetails();
            String sFirstName = sUsernya.get(MySession.KEY_FIRST_NAME);
            String sLastName = sUsernya.get(MySession.KEY_LAST_NAME);
            String sFullName = sFirstName+ " "+sLastName;
            String sToken = sUsernya.get(MySession.KEY_TOKEN);

            baseApiService = RetrofitInstance.getRetrofitInstance(sToken).create(BaseApiService.class);

            //Call Main Activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

            //Toast Selamat Datang
            Toast.makeText(this, "Selamat Datang "+sFullName+"...", Toast.LENGTH_SHORT).show();
        }

        //Button Login
        bLogin.setOnClickListener(view -> cekLoginTwo());

        //Register
        txtRegister.setOnClickListener(v -> routeRegister());
    }

    private void routeRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    //Check Edit Text
    public void CheckEditTextIsEmptyOrNot(){
        EmailHolder = edUser.getText().toString();
        PasswordHolder = edPass.getText().toString();
        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){
            CheckEditText = false;
        }else {
            CheckEditText = true ;
        }
    }

    //Jika Edit Text Terisi
    private void cekLoginTwo(){
        CheckEditTextIsEmptyOrNot();
        if(CheckEditText){
            SyncCek();
        }else {
            Toast.makeText(LoginActivity.this, "Data Masih Kosong !", Toast.LENGTH_LONG).show();
        }
    }

    //Loading
    private void SyncCek() {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                    if (progressDialog.isShowing()) progressDialog.dismiss();
                    callValidateTrue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //Get Retrofit
    private void callValidateTrue(){

        try {
            runOnUiThread(() -> {
                Map<String, Object> jsonParams = new ArrayMap<>();
                jsonParams.put("email", EmailHolder);
                jsonParams.put("password", PasswordHolder);

                RequestBody body = RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),(
                                new JSONObject(jsonParams)).toString());

                Call<ResponseUser> callUserList = baseApiService.getLogin(body);
                callUserList.enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        try {
                            if (response.body() != null){
                                xToken = response.body().getAccessToken();
                                listUser = response.body().getUser();
                                generateUserList();
                            }else {
                                Log.d("Login : ", response.message().toString());
                                Toast.makeText(getApplicationContext(), "Username/Passsword Tidak Sesuai !!", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.wtf("Error : ",e.getMessage());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
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

    //Get Data Retrofit
    private void generateUserList() {
        int lengthRoles = listUser.getRoles().size();
        final String[] sTitleRoles = new String[lengthRoles];
        final Integer[] sIdRoles = new Integer[lengthRoles];

        for (int i=0; i < lengthRoles; i++){
            sTitleRoles[i] = listUser.getRoles().get(i).getRole();
            sIdRoles[i] = listUser.getRoles().get(i).getId();
        }

        menuDialog(sIdRoles, sTitleRoles);
    }

    //View Dialog Roles
    private void menuDialog(Integer[] idRoles,  String[] titleRoles){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Menu Roles");
        alertDialog.setCancelable(false);
        alertDialog.setItems(titleRoles, (dialog, which) -> {
            //String selectedPre = Arrays.asList(sPre).get(which);
            String xTitle = titleRoles[which];
            int xIdRoles = idRoles[which];
            callRoles(xIdRoles, xTitle);
            dialog.dismiss();
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    //Call Roles
    private void callRoles(int idRoles, String titleRoles){
        //Generate Session
        int xId  = listUser.getId();
        String xEmail = listUser.getEmail();
        String xfirstName  = listUser.getFirstName();
        String xlastName  = listUser.getLastName();
        String xmobileNumber  = listUser.getMobileNumber();
        session.createLoginSession(xId, xEmail, xfirstName, xlastName, xmobileNumber, idRoles, titleRoles, xToken);

        //Start Activity
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

        //Toast Activity
        Toast.makeText(this, "Berhasil Login !!\nAnda Login Sebagai "+titleRoles+"...", Toast.LENGTH_SHORT).show();

        //Destroy Login
        finish();
    }


}