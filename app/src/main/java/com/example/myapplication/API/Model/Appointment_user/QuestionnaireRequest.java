package com.example.myapplication.API.Model.Appointment_user;

import java.io.Serializable;

public class QuestionnaireRequest implements Serializable {
    private boolean neededHelpDuetoVax;
    private boolean traveledInLast14Days;
    private boolean isAllergicToVax;
    private boolean hasBloodProblems;
    private boolean isPregnant;

    public boolean getNeededHelpDuetoVax() {
        return neededHelpDuetoVax;
    }

    public void setNeededHelpDuetoVax(boolean neededHelpDuetoVax) {
        this.neededHelpDuetoVax = neededHelpDuetoVax;
    }

    public boolean getTraveledInLast14Days() {
        return traveledInLast14Days;
    }

    public void setTraveledInLast14Days(boolean traveledInLast14Days) {
        this.traveledInLast14Days = traveledInLast14Days;
    }

    public boolean getIsAllergicToVax() {
        return isAllergicToVax;
    }

    public void setIsAllergicToVax(boolean allergicToVax) {
        isAllergicToVax = allergicToVax;
    }

    public boolean getHasBloodProblems() {
        return hasBloodProblems;
    }

    public void setHasBloodProblems(boolean hasBloodProblems) {
        this.hasBloodProblems = hasBloodProblems;
    }

    public boolean getIsPregnant() {
        return isPregnant;
    }

    public void setIsPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

}
