package com.example.myapplication.Booking;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CenterVaccineHelper {

    private ArrayList<String> centerBuffer;
    private List<Center> centers;
    private String[] centerArray;

    private ArrayList<String> vaccineBuffer;
    private List<Vaccine> vaccines;
    private String[] vaccineArray;

    private int selectedCenter;

    public void apiGetCenterList(){
        String json = "[{ 'id': '3fa85f64-5717-4562-b3fc-2c963f66afa6', 'name': 'Karlstad CCC', 'vaccines': [{ 'id': '3fa85f64-5717-4562-b3fc-2c963f66afa6', 'name': 'Pfizer' }],'address': 'string' }" +
                ",{ 'id': '3fa85f64-5717-4562-b3fc-2c963f66afa7', 'name': 'Arena', 'vaccines': [{ 'id': '3fa85f64-5717-4562-b3fc-2c963f66afa6', 'name': 'Moderna' }],'address': 'string' }]";
        Type centerListType = new TypeToken<ArrayList<Center>>(){}.getType();
        centers = new Gson().fromJson(json, centerListType);
//        Log.d("haha", centers.get(0).getVaccines().get(0).getId() + "");
    }

//    public String apiGetDateTimeList(String center, String vaccine){
//        // what do you want for parameters for all the available dates and times list, names or ids?
//    }

    public String [] getCenters(){
        centerBuffer = new ArrayList<String>();
        for(Center center: centers){
            if(center != null) centerBuffer.add(center.getName());
        }

        centerArray = new String[centerBuffer.size()];
        centerArray = centerBuffer.toArray(centerArray);

        return centerArray;
    }

    public String [] getVaccines(int center){
        vaccineBuffer = new ArrayList<String>();
        for(Vaccine vaccine: centers.get(center).getVaccines()){
            if(vaccine != null) vaccineBuffer.add(vaccine.getName());
        }

        vaccineArray = new String[vaccineBuffer.size()];
        vaccineArray = vaccineBuffer.toArray(vaccineArray);

        return vaccineArray;
    }

    public String getSelectedCenter(int center){
        selectedCenter = center;
        return centers.get(center).getId();
    }

    public String getSelectedVaccine(int vaccine){
        return centers.get(selectedCenter).getVaccines().get(vaccine).getId();
    }


}
