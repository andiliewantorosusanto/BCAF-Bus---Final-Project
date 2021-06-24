package com.bcaf.bus.controller.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bcaf.bus.R;
import com.bcaf.bus.controller.LoginActivity;
import com.bcaf.bus.controller.MainActivity;
import com.bcaf.bus.helper.TripScheduleListAdapter;
import com.bcaf.bus.model.TripSchedule.TripSchedule;
import com.bcaf.bus.model.stop.Stop;
import com.bcaf.bus.model.user.ResponseUser;
import com.bcaf.bus.network.BaseApiService;
import com.bcaf.bus.network.RetrofitInstance;
import com.bcaf.bus.session.MySession;
import com.bcaf.bus.utils.MyUtils;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private View root;
    private BaseApiService baseApiService;
    private MySession mySession;
    private MyUtils customUtils;
    private Calendar myCalendar;
    private Button btnCari;
    private TextView tvGreeting;

    private String sToken, sFirstName, sLastName, sRoleId, sRoleName;

    private AutoCompleteTextView actvSourceStop,actvDestinationStop;
    private TextInputEditText tietTo,tietFrom,tietCalendar;
    private TextInputLayout tilTo,tilFrom;
    private DatePickerDialog.OnDateSetListener date;


    private RecyclerView rvListTripSchedule;
    private RecyclerView.Adapter tripScheduleAdapter;
    private RecyclerView.LayoutManager tripScheduleLayoutManager;

    private String from, to;
    private Integer destStopId, sourceStopId;


    public HomeFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        myCalendar = Calendar.getInstance();
        viewData();

        actvSourceStop = root.findViewById(R.id.inp_sourceStop);
        actvDestinationStop = root.findViewById(R.id.inp_destStop);

        tilTo = root.findViewById(R.id.lay_to);
        tilFrom = root.findViewById(R.id.lay_from);

        tietTo = root.findViewById(R.id.inp_to);
        tietFrom = root.findViewById(R.id.inp_from);

        btnCari = root.findViewById(R.id.btn_cari);

        tvGreeting = root.findViewById(R.id.txtGreeting);

        tvGreeting.setText("Halo!,"+sFirstName+ " " + sLastName);

        date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(tietCalendar);
        };

        tietTo.setOnClickListener((v) -> {
            showCalendar(date);
            tietCalendar = tietTo;
        });

        tietFrom.setOnClickListener((v) -> {
            showCalendar(date);
            tietCalendar = tietFrom;
        });

        actvSourceStop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                Stop selection = (Stop) parent.getItemAtPosition(position);
                sourceStopId = selection.getId();
            }
        });

        actvDestinationStop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                Stop selection = (Stop) parent.getItemAtPosition(position);
                destStopId = selection.getId();
            }
        });

        btnCari.setOnClickListener(v -> {
            from = tietFrom.getText().toString();
            to = tietTo.getText().toString();

            if (destStopId.equals(null) || destStopId.equals(null) || TextUtils.isEmpty(from) || TextUtils.isEmpty(to) ) {
                Toast.makeText(getActivity(), "Tanggal masih kosong!", Toast.LENGTH_LONG).show();
            } else {
                cariTripSchedule();
            }
        });


        rvListTripSchedule = root.findViewById(R.id.rvListTripSchedule);
        tripScheduleLayoutManager = new LinearLayoutManager(getActivity());
        rvListTripSchedule.setLayoutManager(tripScheduleLayoutManager);
        getTripSchedule();

        return root;
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
        }
        baseApiService = RetrofitInstance.getRetrofitInstance(sToken).create(BaseApiService.class);

        getStop();
    }

    private void updateLabel(TextInputEditText tiet) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tiet.setText(sdf.format(myCalendar.getTime()));
    }

    private void getStop(){
        Call<List<Stop>> stop = baseApiService.getStop();

        stop.enqueue(new Callback<List<Stop>>() {
            @Override
            public void onResponse(Call<List<Stop>> call, Response<List<Stop>> response) {
                try {
                    if (response.body() != null){
                        List<Stop> stops = response.body();

                        ArrayAdapter<Stop> adapter = new ArrayAdapter<Stop>(root.getContext(), R.layout.spinner_list_item, stops);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        sourceStopId = stops.get(0).getId();
                        destStopId = stops.get(0).getId();

                        actvSourceStop.setAdapter(adapter);
                        actvDestinationStop.setAdapter(adapter);

                    }else {
                        Log.d("Login : ", response.message().toString());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.wtf("Error : ",e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<List<Stop>> call, Throwable t) {
                t.printStackTrace();
                Log.wtf("Failure : ",t.getMessage());
            }
        });
    }

    private void showCalendar(DatePickerDialog.OnDateSetListener date) {
        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(new Date().getTime());
        dialog.show();
    }

    private void getTripSchedule() {
        Call<List<TripSchedule>> listCall = baseApiService.getTripSchedules();
        listCall.enqueue(new Callback<List<TripSchedule>>() {
            @Override
            public void onResponse(Call<List<TripSchedule>> call, Response<List<TripSchedule>> response) {
                List<TripSchedule> tripScheduleList = response.body();
                tripScheduleAdapter = new TripScheduleListAdapter(tripScheduleList, getContext());
                rvListTripSchedule.setAdapter(tripScheduleAdapter);
            }

            @Override
            public void onFailure(Call<List<TripSchedule>> call, Throwable t) {
            }
        });
    }

    private void cariTripSchedule() {
        Log.wtf("test",destStopId.toString());
        Log.wtf("test",from.toString());
        Log.wtf("test",sourceStopId.toString());
        Log.wtf("test",to.toString());

        Call<List<TripSchedule>> listCall = baseApiService.getTripSchedulesParam(destStopId, from, sourceStopId, to);
        listCall.enqueue(new Callback<List<TripSchedule>>() {
            @Override
            public void onResponse(Call<List<TripSchedule>> call, Response<List<TripSchedule>> response) {
                Log.wtf("WTF",response.raw().toString());
                List<TripSchedule> tripScheduleList = response.body();
                if(!tripScheduleList.isEmpty()) {
                    tripScheduleAdapter = new TripScheduleListAdapter(tripScheduleList, getContext());
                    rvListTripSchedule.setAdapter(tripScheduleAdapter);
                    rvListTripSchedule.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity(), "Jadwal perjalanan tidak ditemukan!", Toast.LENGTH_SHORT).show();
                    rvListTripSchedule.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<TripSchedule>> call, Throwable t) {
            }
        });
    }
}