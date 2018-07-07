package com.kybcwockhardt.models;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private static final long serialVersionUID = 7524558747L;
    private int code;
    private boolean status;
    private String message;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable {
        private static final long serialVersionUID = 752879654147L;
        private int id;
        private int emp_no;
        private String hq;
        private String name;
        private String mobile_no;
        private String personal_email;
        private String corporate_email;
        private String designation;
        private String status;
        private String device_type;
        private String device_token;
        private String parent_id;
        private String created_at;
        private Profile profile;
        private List<Camp.Data> camp;
        private int campCount;
        private int campExeCount;

        public int getCampCount() {
            return campCount;
        }

        public void setCampCount(int campCount) {
            this.campCount = campCount;
        }

        public int getCampExeCount() {
            return campExeCount;
        }

        public void setCampExeCount(int campExeCount) {
            this.campExeCount = campExeCount;
        }

        public List<Camp.Data> getCamp() {
            return camp;
        }

        public void setCamp(List<Camp.Data> camp) {
            this.camp = camp;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }

    public class Profile implements Serializable {
        private static final long serialVersionUID = 752452358747L;
        private int id;
        private String add1;
        private String add2;
        private String add3;
        private String city;
        private String tel_no;
        private String dob;
        private String doj;
        private String doc;
        private String dop;
        private String age_in_year;
        private String age_in_service;
        private String gender;
        private String grade;
        private String education;
        private String state;
        private String terr_desig;
        private String zone;
        private String region;
        private String name_of_rm;
        private String name_of_zsm;
        private String status;
        private String div;
        private String tridoss;
        private String hq_status;
        private String class_of_city;
        private String zone_1;
        private String name_of_sm;
        private String remarks;
        private String intimation_date;
        private String psr_code;
        private String user_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAdd1() {
            return add1;
        }

        public void setAdd1(String add1) {
            this.add1 = add1;
        }

        public String getAdd2() {
            return add2;
        }

        public void setAdd2(String add2) {
            this.add2 = add2;
        }

        public String getAdd3() {
            return add3;
        }

        public void setAdd3(String add3) {
            this.add3 = add3;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getTel_no() {
            return tel_no;
        }

        public void setTel_no(String tel_no) {
            this.tel_no = tel_no;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getDoj() {
            return doj;
        }

        public void setDoj(String doj) {
            this.doj = doj;
        }

        public String getDoc() {
            return doc;
        }

        public void setDoc(String doc) {
            this.doc = doc;
        }

        public String getDop() {
            return dop;
        }

        public void setDop(String dop) {
            this.dop = dop;
        }

        public String getAge_in_year() {
            return age_in_year;
        }

        public void setAge_in_year(String age_in_year) {
            this.age_in_year = age_in_year;
        }

        public String getAge_in_service() {
            return age_in_service;
        }

        public void setAge_in_service(String age_in_service) {
            this.age_in_service = age_in_service;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTerr_desig() {
            return terr_desig;
        }

        public void setTerr_desig(String terr_desig) {
            this.terr_desig = terr_desig;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getName_of_rm() {
            return name_of_rm;
        }

        public void setName_of_rm(String name_of_rm) {
            this.name_of_rm = name_of_rm;
        }

        public String getName_of_zsm() {
            return name_of_zsm;
        }

        public void setName_of_zsm(String name_of_zsm) {
            this.name_of_zsm = name_of_zsm;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDiv() {
            return div;
        }

        public void setDiv(String div) {
            this.div = div;
        }

        public String getTridoss() {
            return tridoss;
        }

        public void setTridoss(String tridoss) {
            this.tridoss = tridoss;
        }

        public String getHq_status() {
            return hq_status;
        }

        public void setHq_status(String hq_status) {
            this.hq_status = hq_status;
        }

        public String getClass_of_city() {
            return class_of_city;
        }

        public void setClass_of_city(String class_of_city) {
            this.class_of_city = class_of_city;
        }

        public String getZone_1() {
            return zone_1;
        }

        public void setZone_1(String zone_1) {
            this.zone_1 = zone_1;
        }

        public String getName_of_sm() {
            return name_of_sm;
        }

        public void setName_of_sm(String name_of_sm) {
            this.name_of_sm = name_of_sm;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getIntimation_date() {
            return intimation_date;
        }

        public void setIntimation_date(String intimation_date) {
            this.intimation_date = intimation_date;
        }

        public String getPsr_code() {
            return psr_code;
        }

        public void setPsr_code(String psr_code) {
            this.psr_code = psr_code;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
