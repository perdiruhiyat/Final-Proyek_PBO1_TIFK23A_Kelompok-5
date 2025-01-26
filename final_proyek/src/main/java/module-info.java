module com.mycompany.final_proyek {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.final_proyek to javafx.fxml;
    exports com.mycompany.final_proyek;
}
