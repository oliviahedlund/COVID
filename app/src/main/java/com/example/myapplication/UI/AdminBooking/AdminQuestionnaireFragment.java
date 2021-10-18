package com.example.myapplication.UI.AdminBooking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.API.Model.User.FullUserResponse;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Helpers.NewQuestionnaireHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.UI.UserAppointment.Appointment_make;


import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class AdminQuestionnaireFragment extends Fragment {
    private NewQuestionnaireHelper newQuestionnaireHelper;
    private UserResponse user;
    private FullUserResponse [] userArray;
    private List<FullUserResponse> userList;
    private ListView listView;
    private ArrayList<String> names;
    private FullUserResponse userClicked;

    public AdminQuestionnaireFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    Fragment currentFragment;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");
        view = inflater.inflate(R.layout.admin_incorrect_quest_fragment, container, false);

        /*AdminNewQuestionnaires newFragment = new AdminNewQuestionnaires();
        currentFragment = newFragment;
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frameAdminQ, newFragment).commit();*/

        //setUpBottomButtons();
        LoadingAnimation.startLoadingAnimation(getActivity());

        test();


        return view;
    }



    public Fragment getFragment(){return this;}

    public void test(){
        newQuestionnaireHelper = new NewQuestionnaireHelper(getFragment(), null);
        newQuestionnaireHelper.API_getIncorrect(user, new Runnable() {
            @Override
            public void run() {
                userList = newQuestionnaireHelper.getResp();
                userArray = newQuestionnaireHelper.getListResp(userList);
                System.out.println(userArray[0].getFirstName());
                setupDropdown();
            }
        });

    }

    private void setupDropdown(){
        names = new ArrayList<>();
        for(int i = 0; i<userArray.length; i++){
            names.add(userArray[i].getFirstName() + " " + userArray[i].getLastName());
        }
        listView = view.findViewById(R.id.listViewQuest);
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, names);
        listView.setAdapter(namesAdapter);
        LoadingAnimation.dismissLoadingAnimation();
        getUserInfo();

    }

    private void getUserInfo(){
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String testar = parent.getItemAtPosition(position).toString();
                System.out.println(testar);
                getSelectedUser(testar);
                Fragment newFragment = new Admin_see_user_quest();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, newFragment).commit();
            }
        });
    }

    private void getSelectedUser(String name){
        int i = 0;
        while(!name.equals(userArray[i].getFirstName() + " " + userArray[i].getLastName())){
            i++;
        }
        userClicked = new FullUserResponse();
        userClicked.setFirstName(userArray[i].getFirstName());
        userClicked.setLastName(userArray[i].getLastName());
        userClicked.setEmail(userArray[i].getEmail());
        userClicked.setAppointment(userArray[i].getAppointment());
        userClicked.setQuestionare(userArray[i].getQuestionare());
        System.out.println(userClicked.getEmail());

    }

    private void setUpBottomButtons(){
        Button get_quest = view.findViewById(R.id.Get_quest);

        get_quest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(currentFragment instanceof AdminNewQuestionnaires){
                    //Get incorrect quetionnares
                    newQuestionnaireHelper = new NewQuestionnaireHelper(getFragment(), null);
                    newQuestionnaireHelper.API_getIncorrect(user, new Runnable() {
                        @Override
                        public void run() {
                            userList = newQuestionnaireHelper.getResp();
                            userArray = newQuestionnaireHelper.getListResp(userList);
                            System.out.println(userArray[0].getFirstName());
                        }
                    });
                //}
                /*else if(currentFragment instanceof AdminQuestMessages){
                    //approve questionnaire from messages
                    System.out.println("approve old");
                }*/
            }
        });


    }
}