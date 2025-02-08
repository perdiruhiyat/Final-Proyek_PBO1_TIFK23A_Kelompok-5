/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.final_proyek;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author Perdi Ruhiyat
 */
public class User extends Operations {
    private int idUser;
    private String username;
    private String password;
    private String role;
    private String nim;

    public User(){}
    
    public User(String username, String password, String role, String nim) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.nim = (role.equals("admin")) ? null : nim;
    }
    
    public User(String username, String password){
        this.username = username;
        this.password = password;
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
    
    @Override
    public void tambah() {
        String sql = "INSERT INTO userdb (username, password, role, nim) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.setString(4, nim);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        String sql = "UPDATE userdb SET username = ?, password = ?, role = ?, nim = ? WHERE id_user = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.setString(4, nim);
            stmt.setInt(5, idUser);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hapus() {
        String sql = "DELETE FROM userdb WHERE id_user = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUser);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<User> showTable() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM userdb";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                userList.add(new User(
                    rs.getInt("id_user"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("nim")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}