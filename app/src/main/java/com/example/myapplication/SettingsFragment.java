package com.example.myapplication;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Booking.BookingRequest;
import com.example.myapplication.Booking.BookingResponse;
import com.example.myapplication.Booking.DateTimeHelper;

import java.time.LocalTime;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsFragment extends Fragment{
    Fragment thisFragment = this;
    View view;
    private BookingResponse bookingResponse; ////
    UserResponse user;
    String token;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_language, container, false);
        //View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        GeneralActivity activity = (GeneralActivity) getActivity();
        token = activity.getUserToken();

        setUpButtons();

        ///////////
        Button testB = view.findViewById(R.id.testbutton);
        testB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //CallBookingAPI();

                ArrayList<String> array = new ArrayList<String>();
                array.add("2021-09-26T11:00:00");
                array.add("2021-09-27T11:00:00");
                array.add("2021-09-27T11:40:00");
                DateTimeHelper dt = new DateTimeHelper(array);
                //dt.testDate();
                ArrayList<Integer> days = dt.getDates();
                ArrayList<LocalTime> times = dt.getTimes(27);

                for (int i = 0; i < days.size(); i++) {
                    System.out.println(days.get(i));
                }

                for (int i = 0; i < times.size(); i++) {
                    System.out.println(times.get(i));
                }
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
        //bookingRequest.setToken(token);
        bookingRequest.setMonth(9);
        bookingRequest.setYear(2021);
        bookingRequest.setCenter(0);
        System.out.println(token);
        Call<BookingResponse> bookingResponseCall = ApiClient.getUserService().booking(bookingRequest, token);
        bookingResponseCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                //errorhandling
                if (response.isSuccessful()) {
                    //Toast.makeText(MainActivity.this, "ok, got user", Toast.LENGTH_LONG).show();
                    bookingResponse = response.body(); //i userResponse ligger all information om användaren
                    System.out.println(bookingResponse.getTimes());
                    System.out.println("här");
                    //bookingResponse.getTime();

                }else{
                    Toast.makeText(getActivity(),"Appointments/Booking failed", Toast.LENGTH_LONG).show();
                    System.out.println("else");
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Toast.makeText(getActivity(),"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println("fail");

            }
        });

    }
    ////////////////////////////////////////////////////////////*/

}