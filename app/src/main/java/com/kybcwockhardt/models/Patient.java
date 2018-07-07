package com.kybcwockhardt.models;

import java.util.List;

public class Patient {

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
        private int camp_id;
        private String name;
        private String sex;
        private String mobile;
        private String height;
        private String weight;
        private String abdominal_circumference;
        private int id;
        private int user_id;
        private Question question;
        private Prescribed prescribed;

        public Prescribed getPrescribed() {
            return prescribed;
        }

        public void setPrescribed(Prescribed prescribed) {
            this.prescribed = prescribed;
        }

        public Question getQuestion() {
            return question;
        }

        public void setQuestion(Question question) {
            this.question = question;
        }

        public int getCamp_id() {
            return camp_id;
        }

        public void setCamp_id(int camp_id) {
            this.camp_id = camp_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getAbdominal_circumference() {
            return abdominal_circumference;
        }

        public void setAbdominal_circumference(String abdominal_circumference) {
            this.abdominal_circumference = abdominal_circumference;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }

    public class Prescribed {
        private int id;
        private int patient_id;
        private String not_prescribed;
        private String prescribed;
        private String created_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPatient_id() {
            return patient_id;
        }

        public void setPatient_id(int patient_id) {
            this.patient_id = patient_id;
        }

        public String getNot_prescribed() {
            return not_prescribed;
        }

        public void setNot_prescribed(String not_prescribed) {
            this.not_prescribed = not_prescribed;
        }

        public String getPrescribed() {
            return prescribed;
        }

        public void setPrescribed(String prescribed) {
            this.prescribed = prescribed;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }

    public class Question {
        private int id;
        private int patient_id;
        private String score;
        private String signature;
        private String symtoms;
        private String qol;
        private String created_at;

        public String getSymtoms() {
            return symtoms;
        }

        public void setSymtoms(String symtoms) {
            this.symtoms = symtoms;
        }

        public String getQol() {
            return qol;
        }

        public void setQol(String qol) {
            this.qol = qol;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPatient_id() {
            return patient_id;
        }

        public void setPatient_id(int patient_id) {
            this.patient_id = patient_id;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }
}
