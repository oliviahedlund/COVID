package com.example.myapplication.UI.AdminBooking;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.R;
import com.example.myapplication.API.Model.Admin.UserInfo;
import com.example.myapplication.UI.Adapter.AppointmentInfoListViewAdapter;
import com.example.myapplication.UI.AdminBooking.DashboardGeneric_Admin;
import com.example.myapplication.UI.ViewCells.AppointmentInfo_ListViewCell;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class DashboardGeneric_UserInfo extends Fragment {
    private UserInfo user;
    private TextView firstName;
    private TextView lastName;
    private TextView birthdate;
    private TextView dose;
    private Button backButton;

    View view;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.display_userinfo_admin_dashboardgeneric, container, false);
        user = new UserInfo();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user = bundle.getParcelable("userInfo"); // Key
            System.out.println("NUUUU " + user.getFirstName());
        }
        setUpTextViews();
        setupFilledQuestionnaire();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpTextViews(){
        //findview
        backButton = (Button) view.findViewById(R.id.BackButton);
        firstName = (TextView) view.findViewById(R.id.Firstname);
        lastName = (TextView) view.findViewById(R.id.Lastname);
        birthdate = (TextView) view.findViewById(R.id.Birthdate);
        dose = (TextView) view.findViewById(R.id.NrOfDoses1);
        //set text
        firstName.setText(firstName.getText()+": " + user.getFirstName());
        lastName.setText(lastName.getText()+": " + user.getLastName());
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dose.setText(dose.getText()+": " + user.getAppointment().getTime().format(formatter1));
        birthdate.setText(birthdate.getText()+": " + user.getBirthDate().format(formatter2));

        //button on click listener
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                DashboardGeneric_Admin dashFragment = new DashboardGeneric_Admin();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, dashFragment).commit();
            }
        });
    }

    private void setupFilledQuestionnaire() {
        if (user.getQuestionare() != null) {
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
            for (int i = 0; i < labelArray.length; i++) {
                listViewItems.add(new AppointmentInfo_ListViewCell(labelArray[i], answerArray[i]));
            }
            listView = (ListView) view.findViewById(R.id.QuestionnaireListView);
            AppointmentInfoListViewAdapter adapter = new AppointmentInfoListViewAdapter(this.getContext(), 0, listViewItems);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);
        }
    }
}
