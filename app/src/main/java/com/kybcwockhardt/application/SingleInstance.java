package com.kybcwockhardt.application;


import com.kybcwockhardt.models.Camp;
import com.kybcwockhardt.models.Doctor;
import com.kybcwockhardt.models.MyTeam;
import com.kybcwockhardt.models.Patient;

import java.util.ArrayList;
import java.util.List;

public class SingleInstance {
    private static final SingleInstance ourInstance = new SingleInstance();

    private Doctor.Data selectedDoctor = null;
    private Patient.Data patient;
    private Patient.Question currentQuestionReport;
    private MyTeam.Data zsmHistoryData;
    private Camp historyCamp ;

    public Camp getHistoryCamp() {
        return historyCamp;
    }

    public void setHistoryCamp(Camp historyCamp) {
        this.historyCamp = historyCamp;
    }

    public MyTeam.Data getZsmHistoryData() {
        return zsmHistoryData;
    }

    public void setZsmHistoryData(MyTeam.Data zsmHistoryData) {
        this.zsmHistoryData = zsmHistoryData;
    }

    public Patient.Question getCurrentQuestionReport() {
        return currentQuestionReport;
    }

    public void setCurrentQuestionReport(Patient.Question currentQuestionReport) {
        this.currentQuestionReport = currentQuestionReport;
    }

    public Patient.Data getPatient() {
        return patient;
    }

    public void setPatient(Patient.Data patient) {
        this.patient = patient;
    }

    private Camp.Data selectedCamp = null;
    private List<MyTeam.Data> nextTeam = new ArrayList<>();

    public List<MyTeam.Data> getNextTeam() {
        return nextTeam;
    }

    public void setNextTeam(List<MyTeam.Data> nextTeam) {
        this.nextTeam = nextTeam;
    }

    public Camp.Data getSelectedCamp() {
        return selectedCamp;
    }

    public void setSelectedCamp(Camp.Data selectedCamp) {
        this.selectedCamp = selectedCamp;
    }

    public Doctor.Data getSelectedDoctor() {
        return selectedDoctor;
    }

    public void setSelectedDoctor(Doctor.Data selectedDoctor) {
        this.selectedDoctor = selectedDoctor;
    }

    public static SingleInstance getInstance() {
        return ourInstance;
    }

    private SingleInstance() {
    }


}
