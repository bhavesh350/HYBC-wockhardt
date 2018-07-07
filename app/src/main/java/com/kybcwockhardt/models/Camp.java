package com.kybcwockhardt.models;

import java.util.List;

public class Camp {
    private boolean status;
    private String message;
    private List<Data> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {
        private int id;
        private String msl_code;
        private String camp_date;
        private String created_at;
        private int patient_count;
        private int user_id;
        private int doct_id;
        private int status;
        private Doctor.Data doctor;
        private List<Patient.Data> patients;
        private User.Data employee;

        public User.Data getEmployee() {
            return employee;
        }

        public void setEmployee(User.Data employee) {
            this.employee = employee;
        }

        public List<Patient.Data> getPatients() {
            return patients;
        }

        public void setPatients(List<Patient.Data> patients) {
            this.patients = patients;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMsl_code() {
            return msl_code;
        }

        public void setMsl_code(String msl_code) {
            this.msl_code = msl_code;
        }

        public String getCamp_date() {
            return camp_date;
        }

        public void setCamp_date(String camp_date) {
            this.camp_date = camp_date;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getPatient_count() {
            return patient_count;
        }

        public void setPatient_count(int patient_count) {
            this.patient_count = patient_count;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getDoct_id() {
            return doct_id;
        }

        public void setDoct_id(int doct_id) {
            this.doct_id = doct_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Doctor.Data getDoctor() {
            return doctor;
        }

        public void setDoctor(Doctor.Data doctor) {
            this.doctor = doctor;
        }
    }
}
