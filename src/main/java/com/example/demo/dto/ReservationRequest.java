package com.example.demo.dto;

public class ReservationRequest {
    private String chambreId;
    private String prenom;
    private String nom;
    private String cin;
    private String tel;
    private String startDate;
    private String endDate;

    // Getters and Setters
    public String getChambreId() {
        return chambreId;
    }
    public void setChambreId(String chambreId) {
        this.chambreId = chambreId;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCin() {
        return cin;
    }
    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
