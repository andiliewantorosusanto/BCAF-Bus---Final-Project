package com.bcaf.bus.controller.ui.bus;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bcaf.bus.R;
import com.bcaf.bus.helper.TicketListAdapter;
import com.bcaf.bus.helper.TripScheduleListAdapter;
import com.bcaf.bus.model.TripSchedule.TripSchedule;
import com.bcaf.bus.model.ticket.Ticket;
import com.bcaf.bus.network.BaseApiService;
import com.bcaf.bus.network.RetrofitInstance;
import com.bcaf.bus.session.MySession;
import com.bcaf.bus.utils.MyUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusFragment extends Fragment {


    private View root;
    private BaseApiService baseApiService;
    private MySession mySession;
    private MyUtils customUtils;

    private String sToken, sFirstName, sLastName, sRoleId, sRoleName,sId;


    private RecyclerView rvListTicket;
    private RecyclerView.Adapter ticketAdapter;
    private RecyclerView.LayoutManager ticketLayoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_bus, container, false);
        rvListTicket = root.findViewById(R.id.rvListTicket);
        ticketLayoutManager = new LinearLayoutManager(getActivity());
        rvListTicket.setLayoutManager(ticketLayoutManager);

        viewData();

        return root;
    }

    private void getTicket() {
        Call<List<Ticket>> listCall = baseApiService.getTickets(sId);
        listCall.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                List<Ticket> ticketList = response.body();
                ticketAdapter = new TicketListAdapter(ticketList, getContext());
                rvListTicket.setAdapter(ticketAdapter);
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
            }
        });
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
            sId = sUser.get(MySession.KEY_ID);
        }

        baseApiService = RetrofitInstance.getRetrofitInstance(sToken).create(BaseApiService.class);

        getTicket();
    }

}