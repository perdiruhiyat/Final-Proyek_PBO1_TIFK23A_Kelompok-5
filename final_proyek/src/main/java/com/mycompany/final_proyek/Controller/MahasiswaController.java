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
public class MahasiswaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private String nim;
    @FXML
    private BorderPane rootLayout;

    @FXML
    private void loadDashboard() {
        loadPage("/com/mycompany/final_proyek/fxml/Mahasiswa-Dashboard.fxml");
    }

    @FXML
    private void loadNilai() {
        loadPage("/com/mycompany/final_proyek/fxml/Mahasiswa-Nilai.fxml");
    }

    @FXML
    private void loadPassword() {
        loadPage("/com/mycompany/final_proyek/fxml/Mahasiswa-Password.fxml");
    }
    private void loadPage(String fxmlFile) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent page = loader.load();
        Object controller = loader.getController();
        if (controller instanceof MahasiswaNilaiController) {
            ((MahasiswaNilaiController) controller).setNim(nim);
        } else if (controller instanceof MahasiswaUserController) {
            ((MahasiswaUserController) controller).setNim(nim);
        }
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
                Parent loginPage = FXMLLoader.load(getClass().getResource("/com/mycompany/final_proyek/fxml/LoginForm.fxml"));
                Stage stage = (Stage) rootLayout.getScene().getWindow();
                stage.setScene(new Scene(loginPage));
                stage.setTitle("Login");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDashboard();
    }    
    
}