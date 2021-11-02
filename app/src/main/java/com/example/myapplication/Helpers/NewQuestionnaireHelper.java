package com.example.myapplication.Helpers;

import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Appointment_user.QuestionnaireRequest;
import com.example.myapplication.API.Model.User.FullUserResponse;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewQuestionnaireHelper {
    private Fragment fragment;
    private QuestionnaireRequest questionnaireRequest;
    private List<FullUserResponse> resp;
    private FullUserResponse retrievedUser;

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
    //


    public List<FullUserResponse> getResp() {
        return resp;
    }

    public FullUserResponse getRetrievedUser(){ return retrievedUser;}

    public FullUserResponse [] getListResp(List<FullUserResponse> response){
        ArrayList<FullUserResponse> respBuffer = new ArrayList<FullUserResponse>();
        for(FullUserResponse person: response){
            if(person != null && !person.isCanBook()) respBuffer.add(person);
        }

        FullUserResponse [] respArray = new FullUserResponse[respBuffer.size()];
        respArray = respBuffer.toArray(respArray);

        return respArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_getIncorrect(UserResponse user, Runnable runnable){
        Call<List<FullUserResponse>> call = ApiClient.getUserService().getIncorrectQuest(user.getToken());
        call.enqueue(new Callback<List<FullUserResponse>>() {
            @Override
            public void onResponse(Call<List<FullUserResponse>> call, Response <List<FullUserResponse>> response) {
                if(response.isSuccessful()){
                    resp = response.body();
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
            public void onFailure(Call<List<FullUserResponse>> call, Throwable t) {
                Log.d("haha fail", "" + t);
                LoadingAnimation.dismissLoadingAnimation();
                //new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_getUserFromEmail(String admin,String email, Runnable runnable){
        System.out.println("MAILEN ÄR: " + email);
        Call<FullUserResponse> call = ApiClient.getUserService().getUserFromEmailAPI(admin, email);
        call.enqueue(new Callback<FullUserResponse>() {
            @Override
            public void onResponse(Call<FullUserResponse> call, Response <FullUserResponse> response) {
                if(response.isSuccessful()){
                    retrievedUser = response.body();
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
            public void onFailure(Call<FullUserResponse> call, Throwable t) {
                Log.d("haha fail", "" + t);
                LoadingAnimation.dismissLoadingAnimation();
                //new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_updateQuest(String token,String id ,Runnable runnable){
        Call<Void> call = ApiClient.getUserService().updateCanBook(token, id, true);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response <Void> response) {
                if(response.isSuccessful()){
                    System.out.println("i success");
                    new Handler().postDelayed(runnable, 600);
                }
                else{
                    LoadingAnimation.dismissLoadingAnimation();
                    try {
                        System.out.println("nu är vi i try: " + response.errorBody());
                        new AlertWindow(fragment).createAlertWindow(response.errorBody().string());
                    } catch (IOException e) {
                        new AlertWindow(fragment).createAlertWindow("Unknown error");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("haha fail", "" + t);
                LoadingAnimation.dismissLoadingAnimation();
                //new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }
}
