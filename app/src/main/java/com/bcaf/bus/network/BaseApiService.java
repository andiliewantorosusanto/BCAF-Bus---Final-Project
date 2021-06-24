package com.bcaf.bus.network;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.bcaf.bus.model.TripSchedule.TripSchedule;
import com.bcaf.bus.model.stop.Stop;
import com.bcaf.bus.model.ticket.Ticket;
import com.bcaf.bus.model.ticket.TicketReservation;
import com.bcaf.bus.model.user.ResponseUser;
import com.bcaf.bus.model.user.User;

import java.util.List;

public interface BaseApiService {

    @POST("auth/login")
    Call<ResponseUser> getLogin(@Body RequestBody params);

    @POST("auth/register")
    Call<User> register(@Body RequestBody params);

    @GET("stop")
    Call<List<Stop>> getStop();

    @GET("ticket")
    Call<List<Ticket>> getTickets(@Query("passengerId") String id);

    @GET("tripSchedule")
    Call<List<TripSchedule>> getTripSchedules();

    @GET("tripSchedule")
    Call<List<TripSchedule>> getTripSchedulesParam(@Query("destStopId") Integer destStopId, @Query("from") String from, @Query("sourceStopId") Integer sourceStopId, @Query("to") String to);

    @GET("tripSchedule/{id}")
    Call<TripSchedule> getTripSchedule(@Path("id") String id);

    @POST("ticket")
    Call<Ticket> createTicket(@Body TicketReservation ticketReservation);

}
