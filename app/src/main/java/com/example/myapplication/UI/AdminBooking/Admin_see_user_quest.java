package com.example.myapplication.UI.AdminBooking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.API.Model.Appointment_user.QuestionnaireRequest;
import com.example.myapplication.API.Model.User.FullUserResponse;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Helpers.NewQuestionnaireHelper;
import com.example.myapplication.UI.Adapter.AppointmentInfoListViewAdapter;
import com.example.myapplication.UI.Adapter.QuestionnaireAdapter;
import com.example.myapplication.R;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.UI.ViewCells.AppointmentInfo_ListViewCell;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Admin_see_user_quest extends Fragment {

    private View view;
    private AdminQuestionnaireFragment adminQuestionnaireFragment;
    private FullUserResponse user;
    private String admin;
    private NewQuestionnaireHelper newQuestionnaireHelper;
    private boolean flag;


    public Admin_see_user_quest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.admin_quest_user, container, false);
        user = new FullUserResponse();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user = bundle.getParcelable("userInfo"); // Key
            admin = bundle.getString("adminToken");
            flag = bundle.getBoolean("flag");
        }

        LoadingAnimation.dismissLoadingAnimation();
        setupFilledQuestionnaire();
        setupUser();

        return view;
    }

    private void setupUser(){
        TextView firstName = view.findViewById(R.id.firstName);
        TextView lastName = view.findViewById(R.id.lastName);
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
    }

    private void setupButtons(){

        Button back = view.findViewById(R.id.btnBack);
        Button approve = view.findViewById(R.id.btnApprove);
        if(flag==true){
            approve.setVisibility(View.VISIBLE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new AdminQuestionnaireFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, newFragment).commit();

            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update user Questionnaire
                newQuestionnaireHelper = new NewQuestionnaireHelper(getFragment(), null);
                QuestionnaireRequest quest = new QuestionnaireRequest();
                quest.setHasBloodProblems(false);
                quest.setIsAllergicToVax(false);
                quest.setIsPregnant(false);
                quest.setNeededHelpDuetoVax(false);
                quest.setTraveledInLast14Days(false);
                LoadingAnimation.startLoadingAnimation(getActivity());
                newQuestionnaireHelper.API_updateQuest(admin, user.getId(),quest ,new Runnable() {
                    @Override
                    public void run() {
                        LoadingAnimation.dismissLoadingAnimation();
                        getUpdatedUser();
                    }
                });
            }
        });

    }

    private void getUpdatedUser(){
        LoadingAnimation.startLoadingAnimation(getActivity());
        newQuestionnaireHelper.API_getUserFromEmail(admin, user.getEmail(), new Runnable() {
            @Override
            public void run() {
                user = newQuestionnaireHelper.getRetrievedUser();
                setupFilledQuestionnaire();
                LoadingAnimation.dismissLoadingAnimation();
            }
        });
    }

    public Fragment getFragment(){return this;}

    private void setupFilledQuestionnaire() {
        if(user.getQuestionare() != null) {
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

            listView = (ListView) view.findViewById(R.id.appointmentInfoListView2);
            AppointmentInfoListViewAdapter adapter = new AppointmentInfoListViewAdapter(this.getContext(), 0, listViewItems);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);
        }
        else{
            TextView noQuest;
            noQuest = view.findViewById(R.id.noQuest);
            noQuest.setVisibility(View.VISIBLE);
            flag = false;
        }
        setupButtons();
    }

}