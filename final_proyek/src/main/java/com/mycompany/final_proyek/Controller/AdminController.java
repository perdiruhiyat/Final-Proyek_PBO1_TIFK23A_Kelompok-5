/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.final_proyek.Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author IT 4
 */
public class AdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDashboard();
    }
    @FXML
    private BorderPane rootLayout;

    @FXML
    private void loadDashboard() {
        loadPage("/com/mycompany/final_proyek/fxml/Admin-Dashboard.fxml");
    }

    @FXML
    private void loadDataMhs() {
        loadPage("/com/mycompany/final_proyek/fxml/Admin-DataMhs.fxml");
    }

    @FXML
    private void loadNilai() {
        loadPage("/com/mycompany/final_proyek/fxml/Admin-Nilai.fxml");
    }

    @FXML
    private void loadUser() {
        loadPage("/com/mycompany/final_proyek/fxml/Admin-User.fxml");
    }
    private void loadPage(String fxmlFile) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource(fxmlFile));
            rootLayout.setCenter(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Logout");
        alert.setHeaderText("Anda yakin ingin logout?");
        alert.setContentText("Pilih OK untuk logout atau Cancel untuk tetap berada di aplikasi.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Load halaman login
                Parent loginPage = FXMLLoader.load(getClass().getResource("/com/mycompany/final_proyek/fxml/LoginForm.fxml"));
                Stage stage = (Stage) rootLayout.getScene().getWindow(); // Mendapatkan stage saat ini
                stage.setScene(new Scene(loginPage)); // Mengatur scene ke halaman login
                stage.setTitle("Login"); // Mengatur judul window
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}