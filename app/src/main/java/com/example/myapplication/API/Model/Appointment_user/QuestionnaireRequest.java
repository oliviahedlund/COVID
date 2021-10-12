package com.example.myapplication.API.Model.Appointment_user;

public class QuestionnaireRequest {
    private boolean neededHelpDuetoVax;
    private boolean traveledInLast14Days;
    private boolean isAllergicToVax;
    private boolean hasBloodProblems;
    private boolean isPregnant;

    public boolean isNeededHelpDuetoVax() {
        return neededHelpDuetoVax;
    }

    public void setNeededHelpDuetoVax(boolean neededHelpDuetoVax) {
        this.neededHelpDuetoVax = neededHelpDuetoVax;
    }

    public boolean isTraveledInLast14Days() {
        return traveledInLast14Days;
    }

    public void setTraveledInLast14Days(boolean traveledInLast14Days) {
        this.traveledInLast14Days = traveledInLast14Days;
    }

    public boolean isAllergicToVax() {
        return isAllergicToVax;
    }

    public void setAllergicToVax(boolean allergicToVax) {
        isAllergicToVax = allergicToVax;
    }

    public boolean isHasBloodProblems() {
        return hasBloodProblems;
    }

    public void setHasBloodProblems(boolean hasBloodProblems) {
        this.hasBloodProblems = hasBloodProblems;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

}
