/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author IT 4
 */

package com.mycompany.final_proyek.Controller;

import com.mycompany.final_proyek.DatabaseConnection;
import com.mycompany.final_proyek.Mahasiswa;
import java.sql.Connection;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class AdminDataMhsController {

    @FXML
    private TableView<Mahasiswa> DataMahasiswaTabel;
    @FXML
    private TableColumn<Mahasiswa, String> nimColumn, namaColumn, kelasColumn, jenisKelaminColumn, programStudiColumn, sistemKuliahColumn;
    @FXML
    private TableColumn<Mahasiswa, Integer> semesterColumn, angkatanColumn;
    @FXML
    private ComboBox<String> cmboxProdi, cmboxSiskul, cmboxJenisKelamin;
    @FXML
    private TextField txtNim, txtNama, txtKelas, txtSmt, txtAngkatan, searchField;
    @FXML
    private Button btnTambah, btnUpdate, btnHapus, btnClear;

    private Connection connection = DatabaseConnection.getConnection();
    private ObservableList<Mahasiswa> mahasiswaList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nimColumn.setCellValueFactory(new PropertyValueFactory<>("nim"));
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
        kelasColumn.setCellValueFactory(new PropertyValueFactory<>("kelas"));
        jenisKelaminColumn.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        programStudiColumn.setCellValueFactory(new PropertyValueFactory<>("prodi"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        angkatanColumn.setCellValueFactory(new PropertyValueFactory<>("angkatan"));
        sistemKuliahColumn.setCellValueFactory(new PropertyValueFactory<>("sistemKuliah"));

        cmboxProdi.setItems(FXCollections.observableArrayList("Teknik Informatika", "Teknik Industri", "Bisnis Digital", "Desain Komunikasi Visual", "Manajemen Retail"));
        cmboxSiskul.setItems(FXCollections.observableArrayList("Reguler", "Karyawan"));
        cmboxJenisKelamin.setItems(FXCollections.observableArrayList("Pria", "Wanita"));

        loadDataMahasiswa();
        
        btnUpdate.setDisable(true);
        btnHapus.setDisable(true);        
        DataMahasiswaTabel.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean selected = newSelection != null;
            btnUpdate.setDisable(!selected);
            btnHapus.setDisable(!selected);
            btnTambah.setDisable(true);
            txtNim.setDisable(true);

            if (selected) {
                isiForm(newSelection);
            }
        });
        
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });
    }

    private void loadDataMahasiswa() {
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswaList = mahasiswa.showTable();
        DataMahasiswaTabel.setItems(mahasiswaList);
    }

    @FXML
    private void tambah() {
        if (validasiInput()) {
            Mahasiswa mhs = new Mahasiswa(
                txtNim.getText(),
                txtNama.getText(),
                txtKelas.getText(),
                cmboxJenisKelamin.getValue(),
                cmboxProdi.getValue(),
                Integer.parseInt(txtSmt.getText()),
                Integer.parseInt(txtAngkatan.getText()),
                cmboxSiskul.getValue()
            );
            mhs.tambah();
            loadDataMahasiswa();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Mahasiswa berhasil ditambahkan!");
        }
    }

    @FXML
    private void update() {
        Mahasiswa selectedMahasiswa = DataMahasiswaTabel.getSelectionModel().getSelectedItem();
        if (selectedMahasiswa != null && validasiInput()) {
            selectedMahasiswa.setNama(txtNama.getText());
            selectedMahasiswa.setKelas(txtKelas.getText());
            selectedMahasiswa.setJenisKelamin(cmboxJenisKelamin.getValue());
            selectedMahasiswa.setProdi(cmboxProdi.getValue());
            selectedMahasiswa.setSemester(Integer.parseInt(txtSmt.getText()));
            selectedMahasiswa.setAngkatan(Integer.parseInt(txtAngkatan.getText()));
            selectedMahasiswa.setSistemKuliah(cmboxSiskul.getValue());
            selectedMahasiswa.update();
            loadDataMahasiswa();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Mahasiswa berhasil diperbarui!");
        } else {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih mahasiswa yang ingin diperbarui!");
        }
    }

    @FXML
    private void hapus() {
        Mahasiswa selectedMahasiswa = DataMahasiswaTabel.getSelectionModel().getSelectedItem();
        if (selectedMahasiswa != null) {
            selectedMahasiswa.hapus();
            loadDataMahasiswa();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Mahasiswa berhasil dihapus!");
        } else {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih mahasiswa yang ingin dihapus!");
        }
    }
    
    private void isiForm(Mahasiswa mhs) {
        txtNim.setText(mhs.getNim());
        txtNama.setText(mhs.getNama());
        txtKelas.setText(mhs.getKelas());
        cmboxJenisKelamin.setValue(mhs.getJenisKelamin());
        cmboxProdi.setValue(mhs.getProdi());
        txtSmt.setText(String.valueOf(mhs.getSemester()));
        txtAngkatan.setText(String.valueOf(mhs.getAngkatan()));
        cmboxSiskul.setValue(mhs.getSistemKuliah());
    }

    private void filterTable(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            DataMahasiswaTabel.setItems(mahasiswaList);
            return;
        }
        ObservableList<Mahasiswa> filteredList = FXCollections.observableArrayList();
        for (Mahasiswa m : mahasiswaList) {
            if (m.getNama().toLowerCase().contains(keyword.toLowerCase()) ||
                m.getNim().toLowerCase().contains(keyword.toLowerCase()) ||
                m.getProdi().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(m);
            }
        }
        DataMahasiswaTabel.setItems(filteredList);
    }

    @FXML
    private void clearFields() {
        txtNim.clear();
        txtNama.clear();
        txtKelas.clear();
        cmboxJenisKelamin.setValue(null);
        cmboxProdi.setValue(null);
        txtSmt.clear();
        txtAngkatan.clear();
        cmboxSiskul.setValue(null);
        btnUpdate.setDisable(true);
        btnHapus.setDisable(true);
        btnTambah.setDisable(false);
    }

    private boolean validasiInput() {
        if (txtNim.getText().isEmpty() || txtNama.getText().isEmpty() || txtKelas.getText().isEmpty() || cmboxJenisKelamin.getValue() == null ||
            cmboxProdi.getValue() == null || txtSmt.getText().isEmpty() || txtAngkatan.getText().isEmpty() ||
            cmboxSiskul.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Semua kolom harus diisi!");
            return false;
        }
        try {
            Integer.parseInt(txtSmt.getText());
            Integer.parseInt(txtAngkatan.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Semester dan Angkatan harus berupa angka!");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void printReport(){
        try {
                String reportPath = "target/classes/com/mycompany/final_proyek/report/reportDataMahasiswa.jasper";
                HashMap<String, Object> parameters = new HashMap<>();
                JasperPrint print = JasperFillManager.fillReport(reportPath, parameters, connection);
                JasperViewer viewer = new JasperViewer(print, false);
                viewer.setVisible(true);
            } catch (Exception e){
                e.printStackTrace();
            }
    }
}
