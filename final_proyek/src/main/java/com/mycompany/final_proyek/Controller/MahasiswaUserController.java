/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.final_proyek.Controller;

import com.mycompany.final_proyek.DatabaseConnection;
import com.mycompany.final_proyek.User;
//import com.mycompany.final_proyek.UserOperation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author Perdi Ruhiyat
 */
public class MahasiswaUserController {
    private String nim;
    
    @FXML
    private Button btnEdit, btnBatal, btnSimpan;

    @FXML
    private Text lbPassword;

    @FXML
    private Text lbUsername;
    
    @FXML
    private Pane paneEdit;

    @FXML
    private TextField txtPassword, txtUsername;

    @FXML
    public void initialize() {
        paneEdit.setVisible(false);
        btnBatal.setVisible(false);
        btnSimpan.setVisible(false);
        
    }
    
    public void setNim(String nim) {
        this.nim = nim;
        loadUser();
    }
    
    private void loadUser(){
        Connection conn = DatabaseConnection.getConnection();
        if (nim == null || nim.isEmpty()) return;
        
        String query = "SELECT username, password FROM userdb where nim = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1 , nim);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password")
                );
                lbUsername.setText(user.getUsername());
                lbPassword.setText(user.getPassword());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void edit(){
        paneEdit.setVisible(true);
        btnBatal.setVisible(true);
        btnSimpan.setVisible(true);
    }
    
    @FXML
    private void batal(){
        paneEdit.setVisible(false);
        btnBatal.setVisible(false);
        btnSimpan.setVisible(false);
    }
    
    @FXML
    private void simpan(){
        Connection conn = DatabaseConnection.getConnection();
        if (nim == null || nim.isEmpty()) return;
        if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()){
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Username dan Password tidak boleh kosong!");
        }
        String query = "UPDATE userdb SET username = ?, password = ? WHERE nim = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, txtUsername.getText());
            stmt.setString(2, txtPassword.getText());
            stmt.setString(3, nim);
            stmt.executeUpdate();            
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data berhasil diperbarui!");
            batal();
            loadUser();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
