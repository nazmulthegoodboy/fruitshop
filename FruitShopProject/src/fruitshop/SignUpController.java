package fruitshop;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class SignUpController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleCombo;
    @FXML private Button signupBtn;

    @FXML
    private void initialize() {
        roleCombo.setItems(FXCollections.observableArrayList("Customer", "Admin"));
    }

    @FXML
    private void onSignUp(ActionEvent event) {
        String u = usernameField.getText().trim();
        String p = passwordField.getText().trim();
        String r = roleCombo.getValue();
        if (u.isEmpty() || p.isEmpty() || r==null) {
            showAlert("Validation", "Please fill all fields.");
            return;
        }
        User existing = UserDAO.findByUsername(u);
        if (existing != null) {
            showAlert("Error", "Username already exists.");
            return;
        }
        boolean ok = UserDAO.create(new User(u, p, r));
        if (ok) {
            showAlert("Success", "Account created. Please login.");
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Failed to create account.");
        }
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}