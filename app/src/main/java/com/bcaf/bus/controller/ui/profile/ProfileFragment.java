package com.bcaf.bus.controller.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.bcaf.bus.R;
import com.bcaf.bus.helper.DownloadImageTask;
import com.bcaf.bus.network.BaseApiService;
import com.bcaf.bus.network.RetrofitInstance;
import com.bcaf.bus.session.MySession;
import com.bcaf.bus.utils.MyUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;


public class ProfileFragment extends Fragment {


    private View root;
    private BaseApiService baseApiService;
    private MySession mySession;
    private MyUtils customUtils;
    private String sToken, sFirstName, sLastName, sRoleId, sRoleName, sEmail, sMobileNumber;

    private TextInputEditText tietFirstName,tietLastName,tietEmail,tietMobileNumber;

    private Button btnLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_profile, container, false);

        new DownloadImageTask(root.findViewById(R.id.img_profile))
                .execute("http://13.213.140.15:8080/bus/profile.png");

        tietFirstName = root.findViewById(R.id.inp_depan);
        tietLastName = root.findViewById(R.id.inp_belakang);
        tietEmail = root.findViewById(R.id.inp_email);
        tietMobileNumber = root.findViewById(R.id.inp_mobileNumber);
        btnLogout = root.findViewById(R.id.btn_logout);

        viewData();

        btnLogout.setOnClickListener(v -> btnLogoutClick());
        return root;
    }

    private void btnLogoutClick() {
        mySession.logoutUser();
    }

    private void viewData() {
        customUtils = new MyUtils(root.getContext());

        mySession = new MySession(root.getContext());
        mySession.checkLogin();

        if(mySession.isLoggedIn()) {
            HashMap<String, String> sUser = mySession.getUserDetails();
            sToken = sUser.get(MySession.KEY_TOKEN);
            sFirstName = sUser.get(MySession.KEY_FIRST_NAME);
            sLastName = sUser.get(MySession.KEY_LAST_NAME);
            sRoleId = sUser.get(MySession.KEY_ROLE_ID);
            sRoleName = sUser.get(MySession.KEY_ROLE_NAME);
            sEmail = sUser.get(MySession.KEY_EMAIL);
            sMobileNumber = sUser.get(MySession.KEY_MOBILE_NUMBER);
        }
        baseApiService = RetrofitInstance.getRetrofitInstance(sToken).create(BaseApiService.class);

        tietFirstName.setText(sFirstName);
        tietLastName.setText(sLastName);
        tietEmail.setText(sEmail);
        tietMobileNumber.setText(sMobileNumber);
    }
}