/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.final_proyek.Controller;

/**
 *
 * @author IT 4
 */

import com.mycompany.final_proyek.DatabaseConnection;
import com.mycompany.final_proyek.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AdminUserController {

    @FXML
    private TableView<User> userTabel;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> usernameColumn, passwordColumn, roleColumn, nimColumn;
    
    @FXML
    private TextField txtID, txtUsername, txtPassword;
    
    @FXML
    private ComboBox<String> cmboxRole, cmboxNim;
    
    @FXML
    private Button btnTambah, btnUpdate, btnHapus, btnClear;

    private Connection connection =  DatabaseConnection.getConnection();
    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        nimColumn.setCellValueFactory(new PropertyValueFactory<>("nim"));
        
        cmboxRole.setItems(FXCollections.observableArrayList("admin", "mahasiswa"));

        loadDataUser();
        loadNim();
        
        btnUpdate.setDisable(true);
        btnHapus.setDisable(true);
        userTabel.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean selected = newSelection != null;
            btnUpdate.setDisable(!selected);
            btnHapus.setDisable(!selected);

            if (selected) {
                isiForm(newSelection);
            }
        });
    }

    private void loadDataUser() {
        User userOperation = new User();
        userList = userOperation.showTable();
        userTabel.setItems(userList);
    }
    
    @FXML
    private void pilihRole() {
        String selectedRole = cmboxRole.getValue();

        if ("admin".equals(selectedRole)) {
            cmboxNim.setDisable(true);
            cmboxNim.setValue(null); 
        } else if ("mahasiswa".equals(selectedRole)) {
            cmboxNim.setDisable(false);
        }
    }
    
    private void loadNim() {
        ObservableList<String> nimList = FXCollections.observableArrayList();
        String sql = "SELECT nim, nama FROM mahasiswa";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nim = rs.getString("nim");
                String nama = rs.getString("nama");
                nimList.add(nim + " - " + nama);
            }

            cmboxNim.setItems(nimList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void tambah() {
        String selectedNim = cmboxNim.getValue();
        if (validasiInput()) {
            String nim = selectedNim.split(" - ")[0];
            User user = new User(
                txtUsername.getText(),
                txtPassword.getText(),
                cmboxRole.getValue(),
                nim
            );

            user.tambah();
            loadDataUser();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "User berhasil ditambahkan!");
        }
    }
    
    @FXML
    private void update(){
        User selectedUser = userTabel.getSelectionModel().getSelectedItem();
        if(selectedUser != null && validasiInput()){
            User user = new User(
            Integer.parseInt(txtID.getText()),
            txtUsername.getText(),
            txtPassword.getText(),
            cmboxRole.getValue(),
            cmboxNim.getValue()
            );
            user.update();
            loadDataUser();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "User berhasil diperbaharui!");
        } else {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih User yang ingin diperbarui!");
        }
    }
    
    @FXML
    private void hapus(){
        User selectedUser = userTabel.getSelectionModel().getSelectedItem();
        if(selectedUser != null){
            selectedUser.hapus();
            loadDataUser();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "User berhasil dihapus!");
        } else {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih User yang ingin dihapus!");
        }
    }
    
    private boolean validasiInput() {
        if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty() || cmboxRole.getValue() == null || cmboxNim.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Semua kolom harus diisi!");
            return false;
        }
        return true;
    }
    
    private void isiForm(User user) {
        txtUsername.setText(user.getUsername());
        txtPassword.setText(user.getPassword());
        cmboxRole.setValue(user.getRole());
        cmboxNim.setValue(user.getNim());
        txtID.setText(String.valueOf(user.getIdUser()));
        cmboxNim.setDisable(true);
    }
    
    @FXML
    private void clearFields() {
        txtID.clear();
        txtUsername.clear();
        txtPassword.clear();
        cmboxRole.setValue(null);
        cmboxNim.setValue(null);
        btnUpdate.setDisable(true);
        btnHapus.setDisable(true);
        cmboxNim.setDisable(false);
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
        
}

