/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.final_proyek;

/**
 *
 * @author Perdi Ruhiyat
 */
public class User {
    private int idUser;
    private String username;
    private String password;
    private String role;
    private String nim;

    public User(String username, String password, String role, String nim) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.nim = (role.equals("admin")) ? null : nim;
    }
    
    public User(int idUser, String username, String password, String role, String nim) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.role = role;
        this.nim = (role.equals("admin")) ? null : nim;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idKhs) {
        this.idUser = idUser;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = (role.equals("admin")) ? null : nim;
    }
}