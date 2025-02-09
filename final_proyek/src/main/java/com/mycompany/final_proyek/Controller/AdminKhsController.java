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
import com.mycompany.final_proyek.Khs;
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

public class AdminKhsController {

    @FXML
    private TableView<Khs> khsTabel;
    @FXML
    private TableColumn<Khs, Integer> idColumn, sksColumn;
    @FXML
    private TableColumn<Khs, String> nimColumn, kodemkColumn, mkColumn, gradeColumn;
    @FXML
    private ComboBox<String> cmboxMatkul;
    @FXML
    private TextField txtID, txtNim, txtNama, txtKelas, txtKodeMk, txtSks, txtGrade;
    @FXML
    private Button btnTambah, btnUpdate, btnHapus, btnClear;
    
    private boolean isUpdating = false;
    
    private Connection connection = DatabaseConnection.getConnection();
    private ObservableList<Khs> khsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idKhs"));
        nimColumn.setCellValueFactory(new PropertyValueFactory<>("nim"));
        kodemkColumn.setCellValueFactory(new PropertyValueFactory<>("kodeMk"));
        mkColumn.setCellValueFactory(new PropertyValueFactory<>("mataKuliah"));
        sksColumn.setCellValueFactory(new PropertyValueFactory<>("sks"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        loadDataKhs();
        loadMatkul();
        
        txtNim.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isUpdating) {
                filterTable(newValue);
            }
        });

        btnUpdate.setDisable(true);
        btnHapus.setDisable(true);

        khsTabel.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean selected = newSelection != null;
            btnUpdate.setDisable(!selected);
            btnHapus.setDisable(!selected);
            btnTambah.setDisable(true);
            txtNama.setDisable(true);

            if (selected) {
                isUpdating = true;
                isiForm(newSelection);
                isUpdating = false;
            }
        });
    }

    private void loadDataKhs() {
        Khs khsOperation = new Khs();
        khsList = khsOperation.showTable();
        khsTabel.setItems(khsList);
    }

    private void loadMatkul() {
        ObservableList<String> matkulList = FXCollections.observableArrayList();
        String query = "SELECT mata_kuliah FROM matakuliah";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                matkulList.add(rs.getString("mata_kuliah"));
            }
            cmboxMatkul.setItems(matkulList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pilihMatkul() {
        String mataKuliah = cmboxMatkul.getValue();
        if (mataKuliah == null) return;

        String query = "SELECT kode_mk, sks FROM matakuliah WHERE mata_kuliah = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, mataKuliah);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                txtKodeMk.setText(rs.getString("kode_mk"));
                txtSks.setText(rs.getString("sks"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void cariMahasiswa() {
        String nama = txtNama.getText();
        if (nama.isEmpty()) return;

        String query = "SELECT * FROM mahasiswa WHERE nama LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nama);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                txtNim.setText(rs.getString("nim"));
                txtKelas.setText(rs.getString("kelas"));
            } else {
                txtNim.clear();
                txtKelas.clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void tambah() {
        if (validasiInput()) {
            Khs khs = new Khs(
                txtNim.getText(),
                txtKodeMk.getText(),
                txtGrade.getText()
            );
            khs.tambah();
            loadDataKhs();
            filterTable(txtNim.getText());
            txtKodeMk.clear();
            txtSks.clear();
            txtGrade.clear();
            cmboxMatkul.setValue(null);
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data KHS berhasil ditambahkan!");
        }
    }

    @FXML
    private void update() {
        Khs selectedKhs = khsTabel.getSelectionModel().getSelectedItem();
        if (selectedKhs != null && validasiInput()) {
            Khs khs = new Khs(
                txtKodeMk.getText(),
                txtGrade.getText(),
                Integer.parseInt(txtID.getText())
            );
            khs.update();
            loadDataKhs();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data KHS berhasil diperbarui!");
        }
    }

    @FXML
    private void hapus() {
        Khs selectedKhs = khsTabel.getSelectionModel().getSelectedItem();
        if (selectedKhs != null) {
            selectedKhs.hapus();
            loadDataKhs();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data KHS berhasil dihapus!");
        }
    }

    private boolean validasiInput() {
        if (txtNim.getText().isEmpty() || txtKodeMk.getText().isEmpty() || txtGrade.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Semua kolom harus diisi!");
            return false;
        }
        return true;
    }
    
    private void isiForm(Khs khs) {
        txtNim.setText(khs.getNim());
        txtKodeMk.setText(khs.getKodeMk());
        txtGrade.setText(khs.getGrade());
        cmboxMatkul.setValue(khs.getMataKuliah());
        txtID.setText(String.valueOf(khs.getIdKhs()));;
    }

    @FXML
    private void clearFields() {
        txtID.clear();
        txtNama.clear();
        txtKelas.clear();
        txtNim.clear();
        txtKodeMk.clear();
        cmboxMatkul.setValue(null);
        txtSks.clear();
        txtGrade.clear();
        btnUpdate.setDisable(true);
        btnHapus.setDisable(true);
        btnTambah.setDisable(false);
        txtNama.setDisable(false);
    }
    
    private void filterTable(String keyword){
        if (keyword == null || keyword.isEmpty()) {
            khsTabel.setItems(khsList);
            return;
        }
    
    ObservableList<Khs> filteredList = FXCollections.observableArrayList();
    for (Khs k : khsList) {
        if (k.getNim().toLowerCase().contains(keyword.toLowerCase())){
            filteredList.add(k);
            }
        }
        khsTabel.setItems(filteredList);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}