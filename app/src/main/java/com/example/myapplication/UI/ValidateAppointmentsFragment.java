package com.example.myapplication.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.myapplication.API.Model.Appointment_user.AppointmentRequest;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Helpers.AppointmentsManageExtraHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.QuestionnaireAdapter;
import com.example.myapplication.UI.Adapter.ValidateAppointmentAdapter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidateAppointmentsFragment extends Fragment {

    public ValidateAppointmentsFragment() {
        // Required empty public constructor
    }

    private View view;
    private RecyclerView recyclerView;
    private List<AppointmentRequest> appointments;
    private List<Integer> filteredPositions;
    ValidateAppointmentAdapter adapter;
    private UserResponse user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_validate_appointments, container, false);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        recyclerView = view.findViewById(R.id.recViewValidate);


        setupCheckbox();
        setupButton();
        setupAppointments();



        /*
         //appointments = new ArrayList<AppointmentRequest>();
        for (int i = 0; i < 9; i++) {
            AppointmentRequest temp = new AppointmentRequest();
            temp.setId("Appoint-ID"+i);
            temp.setUserId("User-ID"+i);
            temp.setCenterId("Center-ID"+i);
            temp.setTime(ZonedDateTime.now());

            appointments.add(temp);
        }


        adapter = new ValidateAppointmentAdapter(getContext(), appointments);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
*/
        return view;
    }

    private void setupAppointments() {
        LoadingAnimation.startLoadingAnimation(getActivity());
        AppointmentsManageExtraHelper apiHelper = new AppointmentsManageExtraHelper(ValidateAppointmentsFragment.this);
        Runnable next = new Runnable() {
            @Override
            public void run() {
                appointments = apiHelper.getResponseList();
                //TODO: ev. filter appointments

                adapter = new ValidateAppointmentAdapter(getContext(), appointments);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                LoadingAnimation.dismissLoadingAnimation();
            }
        };
        apiHelper.API_getAppointments(user, next, true);
    }

    private void setupButton() {
        Button validateButton = view.findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean[] valList = adapter.getCheckedList();
                for (int i = 0; i < valList.length; i++) {
                    if(valList[i]){
                        adapter.getAppointmentId(i);
                        System.out.println(adapter.getAppointmentId(i));
                        //TODO: post API
                    }
                }

            }
        });
    }

    private void setupCheckbox() {
        CheckBox checkBoxAll = view.findViewById(R.id.checkBoxAll);
        checkBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapter.setAll(isChecked);
                if(isChecked){
                    checkBoxAll.setText("Uncheck all");
                }
                else{
                    checkBoxAll.setText("Check all");
                }
            }
        });
    }
}