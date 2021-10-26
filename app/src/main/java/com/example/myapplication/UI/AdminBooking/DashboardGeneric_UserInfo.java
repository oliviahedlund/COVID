package com.example.myapplication.UI.AdminBooking;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private TextView Firstname;
    private TextView Lastname;
    private TextView Birthdate;
    private TextView Doses;
    View view;

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


    private void setUpTextViews(){
        Firstname = (TextView) view.findViewById(R.id.Firstname);
        Lastname = (TextView) view.findViewById(R.id.Lastname);
        Birthdate = (TextView) view.findViewById(R.id.Birthdate);
        Doses = (TextView) view.findViewById(R.id.NrOfDoses);


        Firstname.setText(Firstname.getText()+": "+user.getFirstName());
        Lastname.setText(Lastname.getText()+": "+user.getLastName());
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

        Birthdate.setText(Birthdate.getText()+": "+user.getBirthDate());
        Doses.setText(Doses.getText()+": "+user.getFirstDoseDate());
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
