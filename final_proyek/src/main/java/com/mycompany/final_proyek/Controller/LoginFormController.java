/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.final_proyek.Controller;

import com.mycompany.final_proyek.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author IT 4
 */
public class LoginFormController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPW;

    @FXML
    private Button btnLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inisialisasi tambahan jika diperlukan
        btnLogin.setOnAction(this::handleLogin);
    }

    @FXML
    void handleLogin(ActionEvent event) {
        String username = txtUser.getText();
        String password = txtPW.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Username dan Password harus diisi");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT role FROM userdb WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                switch (role) {
                    case "admin":
                        loadScene("/com/mycompany/final_proyek/fxml/dbAdmin.fxml");
                        break;
                    case "mahasiswa":
                        loadScene("/com/mycompany/final_proyek/fxml/dbMahasiswa.fxml");
                        break;
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Gagal", "Username atau Password salah");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    private void loadScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) txtUser.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }   
    
}
