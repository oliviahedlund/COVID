package com.example.myapplication;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Booking.BookingRequest;
import com.example.myapplication.Booking.BookingResponse;
import com.example.myapplication.Booking.DateTimeHelper;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsFragment extends Fragment{
    Fragment thisFragment = this;
    View view;
    UserResponse user;
    DateTimeHelper dateTimeHelper;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_language, container, false);

        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        setUpButtons();

        ///////////
        Button testB = view.findViewById(R.id.testbutton);
        Button testB2 = view.findViewById(R.id.testbutton2);
        testB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                dateTimeHelper = new DateTimeHelper();
                dateTimeHelper.CallBookingAPI(getActivity(), user, 9,2021,0);

//                Log.d("hahaSettings", "" + dateTimeHelper.getArray().size());
            }
        });
        testB2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Log.d("hahaSettings1", "" + dateTimeHelper.getArray().size());
                dateTimeHelper.getDates();
                dateTimeHelper.getTimes(29);
            }
        });

        ///////////

        return view;
    }

    private void setUpButtons(){
        Button enBut = (Button) view.findViewById(R.id.button);
        Button svBut = (Button) view.findViewById(R.id.button3);

        enBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage("en");
            }
        });
        svBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage("sv");
            }
        });
    }

    private void setLanguage(String language){
        Context context = getActivity().getBaseContext();
        //update only if language is not already set
        if(! language.equals(LanguageHelper.getLanguage(context))) {
            //set language
            LanguageHelper.setLocale(context, language);
            //update view
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            getActivity().recreate();
        }
    }
/*
    ////////////////////////////////////////////////////////////
    private void CallBookingAPI(){

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setMonth(9);
        bookingRequest.setYear(2021);
        bookingRequest.setCenter(0);
        //System.out.println(user.getToken());
        Call<List<BookingResponse>> bookingResponseCall = ApiClient.getUserService().booking(user.getToken(), 9,2021,0);
        bookingResponseCall.enqueue(new Callback<List<BookingResponse>>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<BookingResponse>> call, Response<List<BookingResponse>> response) {
                //errorhandling
                if (response.isSuccessful()) {
                    //Toast.makeText(MainActivity.this, "ok, got user", Toast.LENGTH_LONG).show();
                    bookingResponse = response.body(); //i userResponse ligger all information om användaren
                    //System.out.println(bookingResponse);
                    for (int i = 0; i < bookingResponse.size(); i++) {
                        System.out.println(bookingResponse.get(i).getTime());
                    }
                    System.out.println("här");
                    dateTimeHelper = new DateTimeHelper(bookingResponse);
                    ArrayList<Integer> days = dateTimeHelper.getDates();
                    ArrayList<LocalTime> times = dateTimeHelper.getTimes(29);
                    for (int i = 0; i < days.size(); i++) {
                        System.out.println(days.get(i));
                    }

                    for (int i = 0; i < times.size(); i++) {
                        System.out.println(times.get(i));
                    }
                    //bookingResponse.getTime();

                }else{
                    Toast.makeText(getActivity(),"Appointments/Booking failed", Toast.LENGTH_LONG).show();
                    System.out.println("else");
                }
            }

            @Override
            public void onFailure(Call<List<BookingResponse>> call, Throwable t) {
                Toast.makeText(getActivity(),"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println("fail");

            }
        });

    }
    ////////////////////////////////////////////////////////////
*/
}