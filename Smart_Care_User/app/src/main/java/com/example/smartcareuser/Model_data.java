package com.example.smartcareuser;

public class Model_data {

    String bpm,doctor_id, nurse_id,patient_name,patient_age,patient_weight, patient_gender;

    Model_data()
    {

    }

    public Model_data(String bpm, String doctor_id, String nurse_id, String patient_name, String patient_age, String patient_weight, String patient_gender) {
        this.bpm = bpm;
        this.doctor_id = doctor_id;
        this.nurse_id = nurse_id;
        this.patient_name = patient_name;
        this.patient_age = patient_age;
        this.patient_weight = patient_weight;
        this.patient_gender = patient_gender;
    }

    public String getBPM() {
        return bpm;
    }

    public void setBPM(String bpm) {
        this.bpm = bpm;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getNurse_id() {
        return nurse_id;
    }

    public void setNurse_id(String nurse_id) {
        this.nurse_id = nurse_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_age() {
        return patient_age;
    }

    public void setPatient_age(String patient_age) {
        this.patient_age = patient_age;
    }

    public String getPatient_weight() {
        return patient_weight;
    }

    public void setPatient_weight(String patient_weight) {
        this.patient_weight = patient_weight;
    }

    public void setPatient_gender(String patient_gender) {
        this.patient_gender = patient_gender;
    }
}
