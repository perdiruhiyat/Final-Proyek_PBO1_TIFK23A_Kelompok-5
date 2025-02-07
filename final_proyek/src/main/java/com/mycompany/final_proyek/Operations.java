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

public abstract class Operations<T> {
    protected Connection connection;

    public Operations() {
        this.connection = DatabaseConnection.getConnection();
    }

    public abstract void tambah(T data);
    public abstract void update(T data);
    public abstract void hapus(T data);
    public abstract ObservableList<T> showTable();
}