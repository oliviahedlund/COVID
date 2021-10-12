package com.example.myapplication.Helpers;

import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Appointment_user.QuestionnaireRequest;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewQuestionnaireHelper {
    private Fragment fragment;
    private QuestionnaireRequest questionnaireRequest;

    public NewQuestionnaireHelper(Fragment fragment, QuestionnaireRequest questionnaireRequest){
        this.fragment = fragment;
        this.questionnaireRequest = questionnaireRequest;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_sendNewQuestionnaire(UserResponse user, Runnable runnable){
        Call call = ApiClient.getUserService().postNewQuestionnaire(user.getToken(), questionnaireRequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){

                    new Handler().postDelayed(runnable, 600);
                }
                else{
                    LoadingAnimation.dismissLoadingAnimation();
                    try {
                        new AlertWindow(fragment).createAlertWindow(response.errorBody().string());
                    } catch (IOException e) {
                        new AlertWindow(fragment).createAlertWindow("Unknown error");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("haha fail", "" + t);
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }
}
