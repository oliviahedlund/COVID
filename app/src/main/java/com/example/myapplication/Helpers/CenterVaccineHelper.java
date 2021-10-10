package com.example.myapplication.Helpers;

import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.API.Model.Appointment_user.Vaccine;

import java.util.ArrayList;
import java.util.List;

public class CenterVaccineHelper {
    private List<Center> centers;
    private int selectedCenter;

    public CenterVaccineHelper(List<Center> centers){
        this.centers = centers;
    }

    public String [] getCenters(){
        ArrayList<String> centerBuffer = new ArrayList<String>();
        for(Center center: centers){
            if(center != null) centerBuffer.add(center.getName());
        }

        String [] centerArray = new String[centerBuffer.size()];
        centerArray = centerBuffer.toArray(centerArray);

        return centerArray;
    }

    public String [] getVaccines(int center){
        ArrayList<String> vaccineBuffer = new ArrayList<String>();
        for(Vaccine vaccine: centers.get(center).getVaccines()){
            if(vaccine != null) vaccineBuffer.add(vaccine.getVaccineName());
        }

        String [] vaccineArray = new String[vaccineBuffer.size()];
        vaccineArray = vaccineBuffer.toArray(vaccineArray);

        return vaccineArray;
    }

    public String getSelectedCenter(int center){
        selectedCenter = center;
        return centers.get(center).getId();
    }

    public String getSelectedVaccine(int vaccine){
        return centers.get(selectedCenter).getVaccines().get(vaccine).getVaccineId();
    }
}
