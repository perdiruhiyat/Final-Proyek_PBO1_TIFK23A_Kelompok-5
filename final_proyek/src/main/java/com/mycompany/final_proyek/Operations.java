/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.final_proyek;

/**
 *
 * @author Perdi Ruhiyat
 */
import javafx.collections.ObservableList;
import java.sql.Connection;

public abstract class Operations {
    protected Connection connection;

    public Operations() {
        this.connection = DatabaseConnection.getConnection();
    }

    public abstract void tambah();
    public abstract void update();
    public abstract void hapus();
    public abstract ObservableList showTable();
}