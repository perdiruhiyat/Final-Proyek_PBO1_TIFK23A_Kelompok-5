/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.final_proyek.Controller;

import com.mycompany.final_proyek.DatabaseConnection;
import com.mycompany.final_proyek.Khs;
import com.mycompany.final_proyek.Mahasiswa;
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
import java.util.HashMap;
import javafx.scene.control.Button;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import javafx.scene.text.Text;

/**
 *
 * @author IT 4
 */
public class MahasiswaNilaiController {
    private String nim;

    @FXML
    private TableView<Khs> khsTabel;

    @FXML
    private TableColumn<Khs, Integer> idColumn, sksColumn;

    @FXML
    private TableColumn<Khs, String> kodemkColumn, mkColumn, gradeColumn;

    @FXML
    private TableColumn<Khs, Double> bobotColumn, nilaimutuColumn;
    
    @FXML
    private Text lbAngkatan, lbJenisKelamin, lbKelas, lbNama;

    @FXML
    private Text lbNim, lbProdi, lbSiskul, lbSmt, lbSks, lbNilaiMutu, lbIpk;
    
    @FXML
    private Button btnPrint;

    private ObservableList<Khs> khsList = FXCollections.observableArrayList();
    private Connection connection = DatabaseConnection.getConnection();
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idKhs"));
        kodemkColumn.setCellValueFactory(new PropertyValueFactory<>("kodeMk"));
        mkColumn.setCellValueFactory(new PropertyValueFactory<>("mataKuliah"));
        sksColumn.setCellValueFactory(new PropertyValueFactory<>("sks"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        bobotColumn.setCellValueFactory(new PropertyValueFactory<>("bobot"));
        nilaimutuColumn.setCellValueFactory(new PropertyValueFactory<>("nilaiMutu"));        
    }

    public void setNim(String nim) {
        this.nim = nim;
        loadDataKhs();
        loadDataMhs();
        hitungIpk();        
    }

    private void loadDataMhs(){
        
        if (nim == null || nim.isEmpty()) return;
        
        String query = "SELECT * FROM mahasiswa where nim = ?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nim);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Mahasiswa mhs = new Mahasiswa(
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("kelas"),
                    rs.getString("jeniskelamin"),
                    rs.getString("prodi"),
                    rs.getInt("semester"),
                    rs.getInt("angkatan"),
                    rs.getString("sistem_kuliah")
                );
                lbNim.setText(mhs.getNim());
                lbNama.setText(mhs.getNama());
                lbKelas.setText(mhs.getKelas());
                lbJenisKelamin.setText(mhs.getJenisKelamin());
                lbProdi.setText(mhs.getProdi());
                lbSmt.setText(String.valueOf(mhs.getSemester()));
                lbAngkatan.setText(String.valueOf(mhs.getAngkatan()));
                lbSiskul.setText(mhs.getSistemKuliah());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadDataKhs() {
        if (nim == null || nim.isEmpty()) return;

        khsList.clear();

        String query = "SELECT k.id_khs, k.kode_mk, m.mata_kuliah, m.sks, k.grade " +
                       "FROM khs k " +
                       "JOIN matakuliah m ON k.kode_mk = m.kode_mk " +
                       "WHERE k.nim = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nim);
            ResultSet rs = stmt.executeQuery();

            khsList.clear();

            while (rs.next()) {
                Khs khs = new Khs(
                    rs.getInt("id_khs"),
                    rs.getString("kode_mk"),
                    rs.getString("mata_kuliah"),
                    rs.getInt("sks"),
                    rs.getString("grade")
                );
                khsList.add(khs);
            }

            khsTabel.setItems(khsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void hitungIpk(){
        int totalSks = 0;
        double totalNilaiMutu = 0.00;
        
        for(Khs khs : khsTabel.getItems()){
            totalSks += khs.getSks();
            totalNilaiMutu += khs.getNilaiMutu();
        }
        lbSks.setText(String.valueOf(totalSks));
        lbNilaiMutu.setText(String.format("%.2f", totalNilaiMutu));
        
        double ipk = (totalSks == 0) ? 0.00 : (totalNilaiMutu / totalSks);
        lbIpk.setText(String.format("%.2f", ipk));
    }
    
    @FXML
    private void printReport(){
        try {
                String reportPath = "target/classes/com/mycompany/final_proyek/report/reportKHS.jasper";
                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("nim",nim);
                JasperPrint print = JasperFillManager.fillReport(reportPath, parameters, connection);
                JasperViewer viewer = new JasperViewer(print, false);
                viewer.setVisible(true);
            } catch (Exception e){
                e.printStackTrace();
            }
    }
}
