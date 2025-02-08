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

public class LoginFormController implements Initializable {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPW;

    @FXML
    private Button btnLogin;
    
    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = DatabaseConnection.getConnection(); // Pastikan koneksi valid
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

        try {
            String query = "SELECT nim, role FROM userdb WHERE username = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password); // **Pastikan password di-hash jika di database juga di-hash!**

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                String nim = rs.getString("nim"); // **Ambil NIM mahasiswa**

                if ("admin".equals(role)) {
                    loadScene("/com/mycompany/final_proyek/fxml/Admin.fxml", null);
                } else if ("mahasiswa".equals(role)) {
                    loadScene("/com/mycompany/final_proyek/fxml/Mahasiswa.fxml", nim);
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Gagal", "Username atau Password salah");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    private void loadScene(String fxmlPath, String nim) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            if (nim != null) {
                MahasiswaController mahasiswaController = loader.getController();
                mahasiswaController.setNim(nim);
            }

            Stage stage = (Stage) txtUser.getScene().getWindow();
            stage.setTitle("Dashboard");
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
