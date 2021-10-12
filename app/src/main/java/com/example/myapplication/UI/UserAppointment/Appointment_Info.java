package com.example.myapplication.UI.UserAppointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.AppointmentInfoListViewAdapter;
import com.example.myapplication.UI.ViewCells.AppointmentInfo_ListViewCell;
import com.example.myapplication.UI.ViewCells.Covid_Tracking_ListViewCell;

import java.util.ArrayList;

public class Appointment_Info extends Fragment {
    private View view;
    private UserResponse user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.appointment_info, container, false);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");
        setupListViewItems();
        setupTextViews();

        // Inflate the layout for this fragment
        return view;
    }

    private void setupListViewItems() {
        ArrayList<AppointmentInfo_ListViewCell> listViewItems = new ArrayList<AppointmentInfo_ListViewCell>();
        ListView listView;

        String[] labelArray = getResources().getStringArray(R.array.questions);
        String[] answerArray = new String[]{
                user.getQuestionare().getNeededHelpDuetoVax() ? getString(R.string.yes) : getString(R.string.yes),
                user.getQuestionare().getTraveledInLast14Days() ? getString(R.string.yes) : getString(R.string.yes),
                user.getQuestionare().getIsAllergicToVax() ? getString(R.string.yes) : getString(R.string.yes),
                user.getQuestionare().getHasBloodProblems() ? getString(R.string.yes) : getString(R.string.yes),
                user.getQuestionare().getIsPregnant() ? getString(R.string.yes) : getString(R.string.yes),
        };

        for(int i = 0; i < labelArray.length; i++){
            listViewItems.add(new AppointmentInfo_ListViewCell(labelArray[i], answerArray[i]));
        }

        listView = (ListView) view.findViewById(R.id.appointmentInfoListView);
        AppointmentInfoListViewAdapter adapter = new AppointmentInfoListViewAdapter(this.getContext(), 0, listViewItems);
        listView.setAdapter(adapter);
    }

    private void setupTextViews() {
        TextView center = (TextView) view.findViewById(R.id.appointmentInfoCenter);
        TextView vaccine = (TextView) view.findViewById(R.id.appointmentInfoVaccine);
        TextView date = (TextView) view.findViewById(R.id.appointmentInfoDate);

        center.setText("Center: " + user.getAppointment().getCenterId());
        vaccine.setText("Vaccine: " + user.getAppointment().getVaccineId());
        date.setText("Date: " + user.getAppointment().getTime().toString());
    }
}
