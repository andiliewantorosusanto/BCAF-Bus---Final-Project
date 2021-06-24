package com.bcaf.bus.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bcaf.bus.R;
import com.bcaf.bus.model.TripSchedule.TripSchedule;
import com.bcaf.bus.model.ticket.Ticket;
import com.bcaf.bus.model.ticket.TicketReservation;
import com.bcaf.bus.network.BaseApiService;
import com.bcaf.bus.network.RetrofitInstance;
import com.bcaf.bus.session.MySession;

import java.util.HashMap;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {

    private MySession mySession;
    private BaseApiService baseApiService;
    private TripSchedule tripSchedule;
    private TextView tvAsal, tvTujuan, tvTanggal, tvKursi, tvAgensi, tvBus, tvHarga;
    private Button btnPesanTiket,btnBatal;
    private Integer passengerId;
    private TicketReservation ticketReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mySession = new MySession(BookingActivity.this);
        HashMap<String, String> userSession = mySession.getUserDetails();
        passengerId = Integer.valueOf(userSession.get(MySession.KEY_ID));

        tvAsal = findViewById(R.id.txt_asal);
        tvTujuan = findViewById(R.id.txt_tujuan);
        tvTanggal = findViewById(R.id.txt_tanggal);
        tvKursi = findViewById(R.id.txt_kursi);
        tvAgensi = findViewById(R.id.txt_agent);
        tvBus = findViewById(R.id.txt_bus);
        tvHarga = findViewById(R.id.txt_harga);
        btnPesanTiket = findViewById(R.id.btn_pesan);
        btnBatal = findViewById(R.id.btn_batal);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        getTripSchedule(id);

        btnPesanTiket.setOnClickListener(v -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
            builder.setTitle("Pesan Tiket");
            builder.setMessage("Apakah anda yakin ingin memesan tiket ini?");
            builder.setPositiveButton("ya",
                    (dialog, which) -> {
                        dialog.dismiss();
                        pesanTiket();
                    });
            builder.setNegativeButton("tidak",
                    (dialog, which) -> dialog.dismiss());
            builder.setCancelable(false);
            builder.show();
        });

        btnBatal.setOnClickListener(v -> btnBatalClick());
    }

    private void btnBatalClick() {
        Intent i = new Intent(BookingActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void getTripSchedule(String id) {
        baseApiService = RetrofitInstance.getRetrofitInstance("").create(BaseApiService.class);

        Call<TripSchedule> tripScheduleCall = baseApiService.getTripSchedule(id);

        tripScheduleCall.enqueue(new Callback<TripSchedule>() {
            @Override
            public void onResponse(Call<TripSchedule> call, Response<TripSchedule> response) {
                tripSchedule = response.body();
                tvTanggal.setText(tripSchedule.getTripDate());
                tvKursi.setText(tripSchedule.getAvailableSeats().toString());
                tvHarga.setText("Rp."+tripSchedule.getTripDetail().getFare());
                tvAsal.setText(tripSchedule.getTripDetail().getSourceStop().getName());
                tvTujuan.setText(tripSchedule.getTripDetail().getDestStop().getName());
                tvAgensi.setText(tripSchedule.getTripDetail().getAgency().getName());
                tvBus.setText(tripSchedule.getTripDetail().getBus().getCode());
            }

            @Override
            public void onFailure(Call<TripSchedule> call, Throwable t) {

            }
        });
    }

    private void pesanTiket() {
        Random rand = new Random();
        ticketReservation = new TicketReservation(rand.nextInt((Integer.valueOf(tvKursi.getText().toString()) - 1) + 1) + 1, false, tripSchedule.getTripDate(), tripSchedule.getId(), passengerId);
        baseApiService = RetrofitInstance.getRetrofitInstance("").create(BaseApiService.class);
        Call<Ticket> ticketCall = baseApiService.createTicket(ticketReservation);
        ticketCall.enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                Toast.makeText(BookingActivity.this, "Tiket berhasil dipesan", Toast.LENGTH_LONG).show();
                Intent i = new Intent(BookingActivity.this, MainActivity.class);
                i.putExtra("ticketFragment",1);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {

            }
        });
    }
}