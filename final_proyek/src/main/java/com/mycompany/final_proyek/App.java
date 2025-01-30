package com.mycompany.final_proyek;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    
    private static Stage stg;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            stg = primaryStage;
            primaryStage.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/final_proyek/fxml/LoginForm.fxml"));
            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}