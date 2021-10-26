package com.example.myapplication.UI.AdminBooking;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;



import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.myapplication.API.Model.Admin.UserInfo;
import com.example.myapplication.API.Model.Admin.UserNumberResponse;
import com.example.myapplication.API.Model.Appointment_user.AppointmentResponse;
import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.AdminActivity;
import com.example.myapplication.UI.AdminBooking.DashboardGeneric_UserInfo;
import com.example.myapplication.ApiClient;
import com.example.myapplication.Helpers.CenterVaccineHelper;
import com.example.myapplication.Helpers.DashboardGenericHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.AdminBooking.DashboardGeneric_Adapter;
import com.example.myapplication.UI.AdminBooking.DashboardGeneric_Cell;
import com.example.myapplication.UI.LoadingAnimation;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardGeneric_Admin extends Fragment {
    private String[] CountyItems = new String[]{"Choose County","Blekinge", "Dalarna", "Gotland", "Gävleborg", "Halland",
            "Jämtland", "Jönköping", "Kalmar", "Kronoberg", "Norrbotten", "Skåne", "Stockholm", "Södermanland", "Uppsala",
            "Värmland", "Västerbotten", "Västernorrland", "Västmanland", "Västra Götaland", "Örebro", "Östergötland"};
    private final String[] DefaultListview = new String[]{"No booked appointments"}; //default settings for listview
    private String[] CenterItems = new String[]{"Choose Center"};
    private String[] Centers;   //for function SetCenters
    private List<AppointmentResponse> appointmentResponse = new ArrayList<>();
    private List<Center> allCenters2 = new ArrayList<>();
    private List<String> UserIdArray;
    private DashboardGenericHelper dgh;
    private CenterVaccineHelper cvh;
    private AdminActivity activity;
    private UserResponse user;
    private UserInfo userInfo;
    private int userNumberResponse;
    private Button SchedAPP;

    public View view;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_admin_dashboard_generic, container, false);
        activity = (AdminActivity) getActivity();
        user = activity.getUserData();
        SchedAPP = (Button) view.findViewById(R.id.schedappButton);
        if(SchedAPP.getText().equals("Bokade tider")){
            CountyItems = new String[]{"Välj Län","Blekinge", "Dalarna", "Gotland", "Gävleborg", "Halland",
                    "Jämtland", "Jönköping", "Kalmar", "Kronoberg", "Norrbotten", "Skåne", "Stockholm", "Södermanland", "Uppsala",
                    "Värmland", "Västerbotten", "Västernorrland", "Västmanland", "Västra Götaland", "Örebro", "Östergötland"};
           CenterItems = new String[]{"Välj Center"};
        }

        getAppointmentApi();
        getUserNumberApi();
        setupDropdownMenus();
        return view;
    }

    private void setupDropdownMenus(){ // sets up the dropdown menues
        Spinner County_dropdown = (Spinner) view.findViewById(R.id.spinner1); //set up spinners
        Spinner Center_dropdown = (Spinner) view.findViewById(R.id.spinner2);

        //Show string array on spinners
        ArrayAdapter<String> adapterCounty = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, CountyItems);
        ArrayAdapter<String> adapterCenter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, /*getCenters(County_dropdown.getSelectedItem().toString())*/ CenterItems);

        //Set array adapter on spinners
        County_dropdown.setAdapter(adapterCounty);
        Center_dropdown.setAdapter(adapterCenter);
        County_dropdown.setOnItemSelectedListener( //listener for county spinner
                new AdapterView.OnItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) { // här ska funktion kallas på så centers sorteras och blir displayade beroende på county
                        cvh = new CenterVaccineHelper(getFragment());
                        cvh.API_getCenters(getActivity(), user, new Runnable() {
                            @Override
                            public void run() {
                                allCenters2 = new ArrayList<>(); //wipe the array from previously chosen county
                                //AllCenters = cvh.getCenters();  //unused?
                                //AllCenterCounties = cvh.getCenterCounty();//unused?
                                LoadingAnimation.dismissLoadingAnimation();
                                Object CountyItem = parent.getItemAtPosition(pos);
                                System.out.println(CountyItem.toString());     //prints the text in spinner item.
                                getCenters(CountyItem.toString(),Center_dropdown); //sorts centers depending on county
                                setupListView(DefaultListview,0);            //sets up listview with default settings
                            }
                        });
                        LoadingAnimation.startLoadingAnimation(getActivity());
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        Center_dropdown.setOnItemSelectedListener(  //listener for center spinner
            new AdapterView.OnItemSelectedListener() {
              @RequiresApi(api = Build.VERSION_CODES.O)
              public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) { // här ska funktion kallas på så att appointments sorteras och displayas beroende på center
                  if(allCenters2.size()==0){}
                  else{
                      String centerID = allCenters2.get(pos).getCenterId(); //get the ID of the chosen center
                      //Object CenterItem = parent.getItemAtPosition(pos);
                      //System.out.println(CenterItem.toString());     //prints the text in spinner item.
                      //System.out.println(centerID);
                      getAppointments(centerID);
                  }
              }
              public void onNothingSelected(AdapterView<?> parent) {
              }
            }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getAppointments(String centerID){
        List<String> Appointmentarray = new ArrayList<>();
        UserIdArray = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        for(int i=0; i<appointmentResponse.size(); i++){
            if(appointmentResponse.get(i).getCenterId().equals(centerID)){
                UserIdArray.add(appointmentResponse.get(i).getUserId());
                Appointmentarray.add(appointmentResponse.get(i).getTime().format(formatter));
            }
        }
        String [] SortedAppointment = new String[Appointmentarray.size()];
        SortedAppointment = Appointmentarray.toArray(SortedAppointment);
        setupListView(SortedAppointment,SortedAppointment.length);
    }

    private Fragment getFragment(){
        return this;
    }

    private void getCenters(String county,Spinner Center_dropdown){
        if(county.equals("Choose County")){
            CenterItems = new String[]{"Choose Center"};
            ArrayAdapter<String> adapterCenter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item,CenterItems);
            Center_dropdown.setAdapter(adapterCenter);
            return;
        }
        else if(county.equals("Välj Län")){
            CenterItems = new String[]{"Välj Center"};
            ArrayAdapter<String> adapterCenter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item,CenterItems);
            Center_dropdown.setAdapter(adapterCenter);
            return;
        }
        List<Center> allCenters1 = cvh.getCentersObjects();
        for(int i = 0; i < cvh.getCenters().length; i++){
            if(allCenters1.get(i).getCenterCounty().equals(county)){
                allCenters2.add(allCenters1.get(i));
            }
        }
        Centers = cvh.getCentersNames(allCenters2);
        setCenter(Center_dropdown);
    }

    private void setCenter(Spinner Center_dropdown){
        if(Centers.length==0){
            Centers = new String[]{"Choose center"};
        }
        ArrayAdapter<String> adapterCenter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item,Centers);
        Center_dropdown.setAdapter(adapterCenter);
        Centers = new String[]{};
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getUserNumberApi(){
        Call<Integer> UserNumberCall = ApiClient.getUserService().numberOfUsersCall(user.getToken());
        UserNumberCall.enqueue(new Callback<Integer>(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                //errorhandling
                if (response.isSuccessful()) {
                    userNumberResponse = response.body(); //i userNumberResponse ligger all information om användaren
                    if(userNumberResponse > 0) {
                        System.out.println("userNumberResponse = " + userNumberResponse);
                        NumberOfUsers(userNumberResponse);
                    }
                    else System.out.println("Empty appointmentResponse");
                }else{ //Unsuccessful response
                    Toast.makeText(activity,"Number of users error", Toast.LENGTH_LONG).show();
                    System.out.println("Fail - else");
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(activity,"Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println("Fail - onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void NumberOfUsers(int UsersRegistered){ //sets textview to the number of users registered (from getUserNumberApi)
        TextView nrOfUsers = (TextView) view.findViewById(R.id.NumberOfUsers);
        String UR = Integer.toString(UsersRegistered);
        nrOfUsers.setText(nrOfUsers.getText() + UR);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getAppointmentApi(){ //calls Api for all booked appointments in the database
        Call<List<AppointmentResponse>> appointmentResponseCall = ApiClient.getUserService().getAllAppointments(user.getToken(), false);
        appointmentResponseCall.enqueue(new Callback<List<AppointmentResponse>>() {
            @RequiresApi(api = Build.VERSION_CODES.O) // OLD - Delete
            @Override
            public void onResponse(Call<List<AppointmentResponse>> call, Response<List<AppointmentResponse>> response) {
                //errorhandling
                if (response.isSuccessful()) {
                    appointmentResponse = response.body(); //i userResponse ligger all information om användaren
                    if(appointmentResponse.size() > 0) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupListView(String[] SortedAppointments, int size) { // sets up list view with booked appointments depending on the filter
        ListView appointments = (ListView) view.findViewById(R.id.list);
        //makes an arraylist of custom datatype
        ArrayList<DashboardGeneric_Cell> app_list  = new ArrayList<>();
        DashboardGeneric_Cell[] cells = new DashboardGeneric_Cell[appointmentResponse.size()];//appointmentResponse.size()
        //for loop that adds times from api call to a list
        for(int i=0; i<size; i++){
            cells[i] = new DashboardGeneric_Cell(SortedAppointments[i]); //appointmentResponse.get(i).getTime()
            app_list.add(cells[i]);
        }
        //creates and sets custom adapter to the listview
        DashboardGeneric_Adapter AppAdapter = new DashboardGeneric_Adapter(this.getContext(), 0, app_list);
        appointments.setAdapter(AppAdapter);
        appointments.setOnItemClickListener((adapterView, view, pos, l) -> {
            System.out.println("user id: " + UserIdArray.get(pos));
            dgh = new DashboardGenericHelper(getFragment());
            dgh.getUserInfoApi(user,UserIdArray.get(pos), new Runnable() {
                @Override
                public void run() {
                    LoadingAnimation.dismissLoadingAnimation();
                    DashboardGeneric_UserInfo dashFragment = new DashboardGeneric_UserInfo();
                    Bundle bundle = new Bundle();
                    userInfo = dgh.getUserInfo();
                    System.out.println("userinfo: " + userInfo.getFirstName());
                    bundle.putParcelable("userInfo", userInfo); // Key, value
                    dashFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, dashFragment).commit();
                }
            });
            LoadingAnimation.startLoadingAnimation(getActivity());
        });
    }

}
