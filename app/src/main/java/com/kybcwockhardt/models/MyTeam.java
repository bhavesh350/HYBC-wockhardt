package com.kybcwockhardt.models;

import java.util.List;

public class MyTeam {
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
        private int parent_id;
        private int emp_no;
        private String hq;
        private String name;
        private String mobile_no;
        private String personal_email;
        private String corporate_email;
        private String designation;
        private int status;
        private String device_token;
        private List<Data> child;
        private Data parent;
        private List<Camp.Data> camps;
        private int zsm_camp_count;
        private int zsm_patient_count_expected;
        private int zsm_patient_count_orignal;
        private int rm_patient_count;
        private int rm_camp_count;
        private int rm_patient_count_expected;

        public int getRm_patient_count_expected() {
            return rm_patient_count_expected;
        }

        public void setRm_patient_count_expected(int rm_patient_count_expected) {
            this.rm_patient_count_expected = rm_patient_count_expected;
        }

        private int rm_patient_count_orignal;

        public int getZsm_camp_count() {
            return zsm_camp_count;
        }

        public void setZsm_camp_count(int zsm_camp_count) {
            this.zsm_camp_count = zsm_camp_count;
        }

        public int getZsm_patient_count_expected() {
            return zsm_patient_count_expected;
        }

        public void setZsm_patient_count_expected(int zsm_patient_count_expected) {
            this.zsm_patient_count_expected = zsm_patient_count_expected;
        }

        public int getZsm_patient_count_orignal() {
            return zsm_patient_count_orignal;
        }

        public void setZsm_patient_count_orignal(int zsm_patient_count_orignal) {
            this.zsm_patient_count_orignal = zsm_patient_count_orignal;
        }

        public int getRm_patient_count_orignal() {
            return rm_patient_count_orignal;
        }

        public void setRm_patient_count_orignal(int rm_patient_count_orignal) {
            this.rm_patient_count_orignal = rm_patient_count_orignal;
        }

        public List<Camp.Data> getCamps() {
            return camps;
        }

        public void setCamps(List<Camp.Data> camps) {
            this.camps = camps;
        }

        public int getRm_patient_count() {
            return rm_patient_count;
        }

        public void setRm_patient_count(int rm_patient_count) {
            this.rm_patient_count = rm_patient_count;
        }

        public int getRm_camp_count() {
            return rm_camp_count;
        }

        public void setRm_camp_count(int rm_camp_count) {
            this.rm_camp_count = rm_camp_count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getEmp_no() {
            return emp_no;
        }

        public void setEmp_no(int emp_no) {
            this.emp_no = emp_no;
        }

        public String getHq() {
            return hq;
        }

        public void setHq(String hq) {
            this.hq = hq;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public String getPersonal_email() {
            return personal_email;
        }

        public void setPersonal_email(String personal_email) {
            this.personal_email = personal_email;
        }

        public String getCorporate_email() {
            return corporate_email;
        }

        public void setCorporate_email(String corporate_email) {
            this.corporate_email = corporate_email;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }

        public List<Data> getChild() {
            return child;
        }

        public void setChild(List<Data> child) {
            this.child = child;
        }

        public Data getParent() {
            return parent;
        }

        public void setParent(Data parent) {
            this.parent = parent;
        }
    }
}
