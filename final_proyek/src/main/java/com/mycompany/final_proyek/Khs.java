/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.final_proyek;

/**
 *
 * @author Perdi Ruhiyat
 */
public class Khs {
    private int idKhs;
    private String nim;
    private String kodeMk;
    private String mataKuliah;
    private int sks;
    private String grade;
    private double bobot;
    private double nilaiMutu;

    public Khs(String nim, String kodeMk, String grade) {
        this.nim = nim;
        this.kodeMk = kodeMk;
        this.grade = grade;
    }
    
    public Khs(int idKhs, String nim, String kodeMk, String grade) {
        this.idKhs = idKhs;
        this.nim = nim;
        this.kodeMk = kodeMk;
        this.grade = grade;
    }
    
    public Khs(int idKhs, String nim, String kodeMk, String mataKuliah, int sks, String grade){
        this.idKhs = idKhs;
        this.nim = nim;
        this.kodeMk = kodeMk;
        this.mataKuliah = mataKuliah;
        this.sks = sks;
        this.grade = grade;
    }
    
    public Khs(int idKhs, String kodeMk, String mataKuliah, int sks, String grade){
        this.idKhs = idKhs;
        this.kodeMk = kodeMk;
        this.mataKuliah = mataKuliah;
        this.sks = sks;
        this.grade = grade;
        this.bobot = konversiGradeKeBobot(grade); 
        this.nilaiMutu = this.bobot * this.sks;
    }

    public int getIdKhs() {
        return idKhs;
    }

    public void setIdKhs(int idKhs) {
        this.idKhs = idKhs;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getKodeMk() {
        return kodeMk;
    }

    public void setKodeMk(String kodeMk) {
        this.kodeMk = kodeMk;
    }
    
    public String getMataKuliah() { 
        return mataKuliah; 
    }
    
    public int getSks() { 
        return sks; 
    }
    
    public void setSks(int sks) {
        this.sks = sks;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double konversiGradeKeBobot(String grade) {
        switch (grade) {
            case "A":
                return 4.00;
            case "AB":
                return 3.50;
            case "B":
                return 3.00;
            case "BC":
                return 2.50;
            case "C":
                return 2.00;
            case "D":
                return 1.00;
            case "E":
                return 0.00;
            default:
                return 0.00;
        }
    }
    public Double getBobot() {
        return bobot;
    }
    public double getNilaiMutu(){
        return nilaiMutu;
    }
}
