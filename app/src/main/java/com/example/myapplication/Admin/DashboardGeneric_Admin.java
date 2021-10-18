package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.myapplication.API.Model.Appointment_user.AppointmentResponse;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Admin.AdminActivity;
import com.example.myapplication.ApiClient;
import com.example.myapplication.Helpers.CenterVaccineHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.LoadingAnimation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardGeneric_Admin extends Fragment {
    private ListView appointments;
    private String[] CountyItems = new String[]{"Choose county","Blekinge", "Dalarna", "Gotland", "Gävleborg", "Halland",
            "Jämtland", "Jönköping", "Kalmar", "Kronoberg", "Norrbotten", "Skåne", "Stockholm", "Södermanland", "Uppsala",
            "Värmland", "Västerbotten", "Västernorrland", "Västmanland", "Västra Götaland", "Örebro", "Östergötland"};
    private String[] CenterItems = new String[]{"Choose center"};
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
    private CenterVaccineHelper cvh ;
    private String[] AllCenters;
    private String[] AllCenterCounties;
    //private List<UserInfo> userInfo = new ArrayList<UserInfo>();

    View view;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_admin_dashboard_generic, container, false);
        activity = (AdminActivity) getActivity();
        user = activity.getUserData();
        getAppointmentApi();
        setupDropdownMenus();
        return view;
    }

    private void setupDropdownMenus(){ // sets up the dropdown menues
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

        County_dropdown.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) { // här ska funktion kallas på så centers sorteras och blir displayade beroende på county
                        cvh = new CenterVaccineHelper(getFragment());
                        cvh.API_getCenters(getActivity(), user, new Runnable() {
                            @Override
                            public void run() {
                                AllCenters = cvh.getCenters();
                                AllCenterCounties = cvh.getCenterCounty();
                                LoadingAnimation.dismissLoadingAnimation();
                                Object CountyItem = parent.getItemAtPosition(pos);
                                System.out.println(CountyItem.toString());     //prints the text in spinner item.
                                getCenters(CountyItem.toString(),Center_dropdown);
                            }
                        });
                        LoadingAnimation.startLoadingAnimation(getActivity());
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        Center_dropdown.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) { // här ska funktion kallas på så att appointments sorteras och displayas beroende på center
                        Object CenterItem = parent.getItemAtPosition(pos);
                        System.out.println(CenterItem.toString());     //prints the text in spinner item.
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }

    private Fragment getFragment(){
        return this;
    }

    private void getCenters(String county,Spinner Center_dropdown){
        if(county.equals("Choose county")){
            System.out.println("Default choice (choose county)");
            CenterItems = new String[]{"Choose center"};
            ArrayAdapter<String> adapterCenter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item,CenterItems);
            Center_dropdown.setAdapter(adapterCenter);
            return;
        }
        ArrayList<String> Centers = new ArrayList<String>();// sätt till enbart {}
        Centers.add("Choose centerr");
        for(int i=0; i< AllCenterCounties.length ;i++) { //sorts the centers depending on county
            if (AllCenterCounties[i].equals(county)) {
                Centers.add(AllCenters[i]);
            }
        }
        String[] array = new String[Centers.size()];
        array = Centers.toArray(array);
        setCenter(array,Center_dropdown);
    }// end of getCenter

    private void setCenter(String[] Centers,Spinner Center_dropdown){
        System.out.println("Center Set");
        ArrayAdapter<String> adapterCenter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item,Centers);
        Center_dropdown.setAdapter(adapterCenter);
    }

    /*private void getUserInfoApi(){
        Call<UserInfo> userInfoCall = ApiClient.getUserService().getUserInfoAll(user.getToken(), appointmentResponse.get(0).getUserId());
    }*/

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getAppointmentApi(){
        Call<List<AppointmentResponse>> appointmentResponseCall = ApiClient.getUserService().getAllAppointments(user.getToken(), false);
        appointmentResponseCall.enqueue(new Callback<List<AppointmentResponse>>() {
            @RequiresApi(api = Build.VERSION_CODES.O) // OLD - Delete
            @Override
            public void onResponse(Call<List<AppointmentResponse>> call, Response<List<AppointmentResponse>> response) {
                //errorhandling
                if (response.isSuccessful()) {
                    appointmentResponse = response.body(); //i userResponse ligger all information om användaren
                    if(appointmentResponse.size() > 0) {
                        System.out.println("Size: " + appointmentResponse.size());
                        System.out.println("ID: " + appointmentResponse.get(0).getId());
                        System.out.println("User ID: " + appointmentResponse.get(0).getUserId());
                        System.out.println("Center ID: " + appointmentResponse.get(0).getCenterId());
                        System.out.println("Vaccine ID: " + appointmentResponse.get(0).getVaccineId());
                        System.out.println("Time: " + appointmentResponse.get(0).getTime());
                        System.out.println("Length: " + appointmentResponse.get(0).getLength());
                        setupListView();
                    }
                    else System.out.println("Empty appointmentResponse");
                }else{
                    Toast.makeText(activity,"Appointments error", Toast.LENGTH_LONG).show();
                    System.out.println("Fail - else");
                }
            }
            @Override
            public void onFailure(Call<List<AppointmentResponse>> call, Throwable t) {
                Toast.makeText(activity,"Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println("Fail - onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void setupListView() { // sets up list view with booked appointments depending on the filter
        appointments = (ListView) view.findViewById(R.id.list);
        String name;
        //makes an arraylist of custom datatype
        ArrayList<DashboardGeneric_Cell> app_list  = new ArrayList<DashboardGeneric_Cell>();
        DashboardGeneric_Cell[] cells = new DashboardGeneric_Cell[appointmentResponse.size()];//appointmentResponse.size()

        //for loop that adds times from api call to a list
        for(int i =0; i<appointmentResponse.size(); i++){ //appointmentResponse.size()
            cells[i] = new DashboardGeneric_Cell(appointmentResponse.get(i).getTime()); //appointmentResponse.get(i).getTime()
            System.out.println("cells :"+ cells[i]);
            app_list.add(cells[i]);
        }
        //creates and sets custom adapter to the listview
        DashboardGeneric_Adapter AppAdapter = new DashboardGeneric_Adapter(this.getContext(), 0, app_list);
        appointments.setAdapter(AppAdapter);
    }
}
