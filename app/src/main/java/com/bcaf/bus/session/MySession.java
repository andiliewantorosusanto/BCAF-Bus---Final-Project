package com.bcaf.bus.session;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bcaf.bus.controller.LoginActivity;

import java.util.HashMap;

public class MySession {

    SharedPreferences pref;

    Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "User";
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_ID = "_id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_MOBILE_NUMBER = "mobile_number";
    public static final String KEY_ROLE_ID = "role_id";
    public static final String KEY_ROLE_NAME = "role_name";
    public static final String KEY_TOKEN = "token";

    public MySession(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(int _id, String email, String first_name, String last_name,
                                   String mobile_number, int role_id, String role_name, String token){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, String.valueOf(_id));
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_FIRST_NAME, first_name);
        editor.putString(KEY_LAST_NAME, last_name);
        editor.putString(KEY_MOBILE_NUMBER, mobile_number);
        editor.putString(KEY_ROLE_ID, String.valueOf(role_id));
        editor.putString(KEY_ROLE_NAME, role_name);
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();

        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_ID, pref.getString(KEY_ID,null));
        user.put(KEY_FIRST_NAME, pref.getString(KEY_FIRST_NAME, null));
        user.put(KEY_LAST_NAME, pref.getString(KEY_LAST_NAME, null));
        user.put(KEY_MOBILE_NUMBER, pref.getString(KEY_MOBILE_NUMBER, null));
        user.put(KEY_ROLE_ID, pref.getString(KEY_ROLE_ID, null));
        user.put(KEY_ROLE_NAME, pref.getString(KEY_ROLE_NAME, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));

        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
