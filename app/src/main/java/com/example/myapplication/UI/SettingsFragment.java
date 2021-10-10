package com.example.myapplication.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.LanguageHelper;
import com.example.myapplication.R;


public class SettingsFragment extends Fragment{
    Fragment thisFragment = this;
    View view;
    UserResponse user;

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