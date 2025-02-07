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
public class UserOperation extends Operations<User>{
    @Override
    public void tambah(User user){
      String sql = "INSERT INTO userdb (username, password, role, nim) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            if ("admin".equals(user.getRole())) {
                stmt.setNull(4, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(4, user.getNim());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(User user){
        String sql = "UPDATE userdb SET username = ?, password = ?, role = ?, nim = ? WHERE id_user = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            if ("admin".equals(user.getRole())) {
                stmt.setNull(4, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(4, user.getNim());
            }
            stmt.setInt(5, user.getIdUser());
            stmt.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void hapus(User user){
        String sql = "DELETE FROM userdb WHERE id_user = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getIdUser());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public ObservableList<User> showTable(){
        ObservableList<User> userList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM userdb";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id_user"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("nim")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
