package com.example.myapplication.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.API.Model.User.FullUserResponse;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Helpers.NewQuestionnaireHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.UI.UserAppointment.Appointment_make;

import java.util.List;

public class AdminQuestionnaireFragment extends Fragment {
    private NewQuestionnaireHelper newQuestionnaireHelper;
    private UserResponse user;
    private FullUserResponse [] userArray;
    private List<FullUserResponse> userList;

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
        view = inflater.inflate(R.layout.questionnaire_admin, container, false);

        AdminNewQuestionnaires newFragment = new AdminNewQuestionnaires();
        currentFragment = newFragment;
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frameAdminQ, newFragment).commit();


        setupTopButtons();
        setUpBottomButtons();
        return view;
    }

    private void setupTopButtons(){
        Button newQ = view.findViewById(R.id.button4);
        Button answQ = view.findViewById(R.id.button5);

        newQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminNewQuestionnaires newFragment = new AdminNewQuestionnaires();
                currentFragment = newFragment;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminQ, newFragment).commit();
            }
        });
        answQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminQuestMessages newFragment = new AdminQuestMessages();
                currentFragment = newFragment;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminQ, newFragment).commit();
            }
        });
    }

    public Fragment getFragment(){return this;}




    private void setUpBottomButtons(){
        Button approve = view.findViewById(R.id.approveB);
        Button sendQuestion = view.findViewById(R.id.addQuestB);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentFragment instanceof AdminNewQuestionnaires){
                    //Get incorrect quetionnares
                    newQuestionnaireHelper = new NewQuestionnaireHelper(getFragment(), null);
                    newQuestionnaireHelper.API_getIncorrect(user, new Runnable() {
                        @Override
                        public void run() {
                            //LoadingAnimation.dismissLoadingAnimation();
                            userList = newQuestionnaireHelper.getResp();
                            userArray = newQuestionnaireHelper.getListResp(userList);
                            System.out.println(userArray[0].getFirstName());

                        }
                    });
                }
                else if(currentFragment instanceof AdminQuestMessages){
                    //approve questionnaire from messages
                    System.out.println("approve old");
                }
            }
        });
        sendQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentFragment instanceof AdminNewQuestionnaires){
                    System.out.println("question new");
                    //send message to user with id from new questionnaire
                }
                else if(currentFragment instanceof AdminQuestMessages){
                    //send message to user with id from messages
                    System.out.println("question old");
                }
            }
        });

    }
}