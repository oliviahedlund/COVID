package com.example.myapplication.UI.UserAppointment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Helpers.AppointmentHelper;
import com.example.myapplication.Helpers.CenterVaccineHelper;
import com.example.myapplication.Helpers.UserAPIHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.AppointmentInfoListViewAdapter;
import com.example.myapplication.UI.GenericMessageFragment;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.UI.ViewCells.AppointmentInfo_ListViewCell;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Appointment_Info extends Fragment {
    private View view;
    private UserResponse user;
    private CenterVaccineHelper centerVaccineHelper;
    private String centerName;
    private String vaccineName;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.appointment_info, container, false);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");
        centerVaccineHelper = new CenterVaccineHelper(getFragment());
        LoadingAnimation.startLoadingAnimation(getActivity());


        UserAPIHelper userAPIHelper = new UserAPIHelper(this);
        userAPIHelper.API_getUser(user, new Runnable() {
            @Override
            public void run() {
                user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");
                centerVaccineHelper = new CenterVaccineHelper(getFragment());
                if(user.getAppointment() != null){
                    centerVaccineHelper.API_getCenterName(getActivity(), user, new Runnable() {
                        @Override
                        public void run() {
                            centerName = centerVaccineHelper.getCenterName();
                            centerVaccineHelper.API_getVaccineName(getActivity(), user, new Runnable() {
                                @Override
                                public void run() {
                                    vaccineName = centerVaccineHelper.getVaccineName();
                                    LoadingAnimation.dismissLoadingAnimation();
                                    showViewLogics();
                                }
                            });
                        }
                    });
                } else{
                    LoadingAnimation.dismissLoadingAnimation();
                    showViewLogics();
                }
            }
        });
        LoadingAnimation.startLoadingAnimation(getActivity());


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showViewLogics(){
        if(user.getAppointment() != null && user.getQuestionare() != null){
            setupFilledQuestionnaire();
            setupFilledAppointment();
            setupCancelRebookButtons();
        }
        if(user.getQuestionare() != null && user.getAppointment() == null){
            setupFilledQuestionnaire();
            setupEmptyAppointment();
            setupBookButton();
        }

        if(user.getQuestionare() == null && user.getAppointment() != null){
            setupEmptyQuestionnaire();
            setupFilledAppointment();
            // create a textview here to show that admin has to intervene.
        }
        if(user.getQuestionare() == null && user.getAppointment() == null){
            setupEmptyQuestionnaire();
            setupEmptyAppointment();
            setupBookButton();
        }
    }

    private void setupBookButton(){
        LinearLayout layout = view.findViewById(R.id.bookButtongroup);
        Button bookButton = view.findViewById(R.id.appointmentBook);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment;
                if(user.isCanBook()){
                    newFragment = new Appointment_make();
                } else{
                    if(user.isGotFirstDose()){
                        newFragment = new GenericMessageFragment(getResources().getString(R.string.contactAdmin));
                    } else{
                        if(user.getQuestionare() == null){
                            newFragment = new Appointment_questionnaire();
                        } else{
                            newFragment = new GenericMessageFragment("Admin has to book for you or allow you to book, please contact admin");
                        }
                    }
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, newFragment).commit();
            }
        });
        layout.setVisibility(View.VISIBLE);
    }

    private void setupEmptyQuestionnaire(){
        LinearLayout layout = view.findViewById(R.id.emptyQuestionnaireGroup);
        layout.setVisibility(View.VISIBLE);
    }

    private void setupEmptyAppointment(){
        LinearLayout layout = view.findViewById(R.id.emptyAppointmentGroup);
        layout.setVisibility(View.VISIBLE);
    }

    private void setupFilledQuestionnaire() {
        ArrayList<AppointmentInfo_ListViewCell> listViewItems = new ArrayList<AppointmentInfo_ListViewCell>();
        ListView listView;

        String[] labelArray = getResources().getStringArray(R.array.questions);
        String[] answerArray = new String[]{
                user.getQuestionare().getNeededHelpDuetoVax() ? getString(R.string.yes) : getString(R.string.no),
                user.getQuestionare().getTraveledInLast14Days() ? getString(R.string.yes) : getString(R.string.no),
                user.getQuestionare().getIsAllergicToVax() ? getString(R.string.yes) : getString(R.string.no),
                user.getQuestionare().getHasBloodProblems() ? getString(R.string.yes) : getString(R.string.no),
                user.getQuestionare().getIsPregnant() ? getString(R.string.yes) : getString(R.string.no),
        };

        for(int i = 0; i < labelArray.length; i++){
            listViewItems.add(new AppointmentInfo_ListViewCell(labelArray[i], answerArray[i]));
        }

        listView = (ListView) view.findViewById(R.id.appointmentInfoListView);
        AppointmentInfoListViewAdapter adapter = new AppointmentInfoListViewAdapter(this.getContext(), 0, listViewItems);
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupFilledAppointment() {
        LinearLayout textViewGroup = view.findViewById(R.id.appointmentGroup);
        TextView center = (TextView) view.findViewById(R.id.appointmentInfoCenter);
        TextView vaccine = (TextView) view.findViewById(R.id.appointmentInfoVaccine);
        TextView date = (TextView) view.findViewById(R.id.appointmentInfoDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        String formattedString = user.getAppointment().getTime().format(formatter);

        center.setText("Center: \t\t" + centerName);
        vaccine.setText("Vaccine: \t" + vaccineName);
        date.setText("Date: \t\t\t\t" + formattedString);
        textViewGroup.setVisibility(View.VISIBLE);
    }

    private void setupCancelRebookButtons(){
        LinearLayout layout = view.findViewById(R.id.cancelRebookButtonGroup);

        Button cancelButton = view.findViewById(R.id.appointmentCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                AppointmentHelper helper = new AppointmentHelper(getFragment());
                helper.API_deleteAppointment_user(user, new Runnable() {
                    @Override
                    public void run() {
                        LoadingAnimation.dismissLoadingAnimation();
                        Appointment_Info info = new Appointment_Info();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, info).commit();
                    }
                });
                LoadingAnimation.startLoadingAnimation(getActivity());
            }
        });

        Button rebookButton = view.findViewById(R.id.appointmentRebook);
        rebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment_make appointment_make = new Appointment_make();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointment_make).commit();
            }
        });

        layout.setVisibility(View.VISIBLE);
    }

    private Fragment getFragment(){
        return this;
    };
}
