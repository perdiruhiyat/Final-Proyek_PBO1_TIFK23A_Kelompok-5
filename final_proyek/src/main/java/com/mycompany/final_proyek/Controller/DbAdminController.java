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


public class DbAdminController implements Initializable {

  @FXML
    private TextField txtnama;

    @FXML
    private NimField txtnim;
    
     @FXML
    private KelasField txtkls;

 @FXML
    private ProdiField txtprodi;
 
 @FXML
    private SemesterField txtsmt;
 
  @FXML
    private AngkatanField txtaktn;
  
   @FXML
    private SistemField txtsys;



    @FXML
    private Button btnaddmhsw;
    
    @FXML
    private Button btnupdatemhsw;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       btnaddmhsw.setOnAction(this: : handleAddmhsw);
    }
    @FXML
    void handleAddmhsw(ActionEvent event){
            String no = txtnim.getText()
            String kelas = txtkls.getText()
            String program = txtprodi.getText()
            String semester = txtsmt.getText()
            String angkatan = txtaktn.getText()
            String sistem = txtsys.getText()
            String name = txtnama.getText()
        if (no.isEmpty() || kelas.isEmpty()|| program.isEmpty()|| semester.isEmpty()|| angkatan.isEmpty()|| sistem.isEmpty()|| name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "semua data harus diisi");
            return;
        }
            
         try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO mahasiswa (nim,nama,kelas,prodi,semester,angkatan,sistem_kuliah) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, no());
            stmt.setString(2,name());
            stmt.setString(3, kelas());
            stmt.setString(4, prodi());
            stmt.setString(5,semester());
            stmt.setString(6, angkatan());
            stmt.setString(7, sistem());            
            stmt.executeUpdate();
            System.out.println("To-Do added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        // TODO

    private void showAlert(Alert.AlertType alertType, String peringatan, String username_dan_Password_harus_diisi) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
