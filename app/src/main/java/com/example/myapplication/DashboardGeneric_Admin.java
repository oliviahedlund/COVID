package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.myapplication.Admin.AdminActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardGeneric_Admin extends Fragment {
    //private String[] locations;
    //private String[] ageGroup;
    //private String[] product;
    private String[] CountyItems = new String[]{"Choose county","Blekinge", "Dalarna", "Gotland", "Gävleborg", "Halland",
            "Jämtland", "Jönköping", "Kalmar", "Kronoberg", "Norrbotten", "Skåne", "Stockholm", "Södermanland", "Uppsala",
            "Värmland", "Västerbotten", "Västernorrland", "Västmanland", "Västra Götaland", "Örebro", "Östergötland"};
    private String[] CenterItems = new String[]{"Choose center", "Center 1", "Center 2"};
    private String[] MonthItems = new String[]{"Choose month","Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Okt","Nov","Dec"};
    private String id;
    private String userId;
    private String centerId;
    private String vaccineId;
    private String time;
    private int length;
    private AdminActivity activity;
    private UserResponse user;
    private List<AppointmentResponse> appointmentResponse = new ArrayList<AppointmentResponse>();
    //private List<UserInfo> userInfo = new ArrayList<UserInfo>();

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_admin_dashboard_generic, container, false);
        setupDropdownMenus();

        activity = (AdminActivity) getActivity();
        user = activity.getUserData();

        getAppointmentApi();

        // Inflate the layout for this fragment
        return view;
    }

    private void setupDropdownMenus() {
        //Setup spinners
        Spinner County_dropdown = (Spinner) view.findViewById(R.id.spinner1);
        Spinner Center_dropdown = (Spinner) view.findViewById(R.id.spinner2);
        Spinner Date_dropdown = (Spinner) view.findViewById(R.id.spinner3);

        //Show string array on spinners
        ArrayAdapter<String> adapterCounty = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, CountyItems);
        ArrayAdapter<String> adapterCenter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, /*getCenters(County_dropdown.getSelectedItem().toString())*/ CenterItems);
        ArrayAdapter<String> adapterDate = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, MonthItems);

        //Set array adapter on spinners
        County_dropdown.setAdapter(adapterCounty);
        Center_dropdown.setAdapter(adapterCenter);
        Date_dropdown.setAdapter(adapterDate);
    }
    /*private String[] getCenters(String county){
        switch(county){
            case "Blekinge":
            case "Dalarna":
            case "Gotland":
            case "Gävleborg":
            case "Halland":
            case "Jämtland":
            case "Jönköping":
            case "Kalmar":
            case "Kronoberg":
            case "Norrbotten":
            case "Skåne":
            case "Stockholm":
            case "Södermansland":
            case "Uppsala":
            case "Värmland":
            case "Västerbotten":
            case "Västernorrland":
            case "Västmanland":
            case "Västra Götaland":
            case "Örebro":
            case "Östergötland":
                return new String[]{"Center", "Center 1", "Center 2"};
            default:
                return new String[]{"Center"};
        }
    }
*/
    /*private void getUserInfoApi(){
        Call<UserInfo> userInfoCall = ApiClient.getUserService().getUserInfoAll(user.getToken(), appointmentResponse.get(0).getUserId());
    }*/

    private void getAppointmentApi(){
        Call<List<AppointmentResponse>> appointmentResponseCall = ApiClient.getUserService().getAllAppointments(user.getToken(), false);
        appointmentResponseCall.enqueue(new Callback<List<AppointmentResponse>>() {

            @RequiresApi(api = Build.VERSION_CODES.O) // OLD - Delete
            @Override
            public void onResponse(Call<List<AppointmentResponse>> call, Response<List<AppointmentResponse>> response) {

                //errorhandling
                if (response.isSuccessful()) {
                    appointmentResponse = response.body(); //i userResponse ligger all information om användaren

                    //appointmentResponse.get(10).getId(); //Returnerar Id:t på 11e tiden
                    if(appointmentResponse.size() > 0) {
                        System.out.println("ID: " + appointmentResponse.get(0).getId());
                        System.out.println("User ID: " + appointmentResponse.get(0).getUserId());
                        System.out.println("Center ID: " + appointmentResponse.get(0).getCenterId());
                        System.out.println("Vaccine ID: " + appointmentResponse.get(0).getVaccineId());
                        System.out.println("Time: " + appointmentResponse.get(0).getTime());
                        System.out.println("Length: " + appointmentResponse.get(0).getLength());
                    }
                    else{
                        System.out.println("Empty appointmentResponse");
                    }
                }else{
                    Toast.makeText(activity,"Appointments error", Toast.LENGTH_LONG).show();
                    System.out.println("Fail - else");
                }
            }

            @Override
            public void onFailure(Call<List<AppointmentResponse>> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println("Fail - onFailure: " + t.getLocalizedMessage());
            }
        });

    }
}


