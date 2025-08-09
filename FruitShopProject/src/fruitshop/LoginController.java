package fruitshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginBtn;
    @FXML private Button signupBtn;

    @FXML
    private void initialize() {
    }

    @FXML
    private void onLogin(ActionEvent event) {
        String u = usernameField.getText().trim();
        String p = passwordField.getText().trim();
        if (u.isEmpty() || p.isEmpty()) {
            showAlert("Validation", "Please enter both username and password.");
            return;
        }
        User user = UserDAO.findByUsername(u);
        if (user == null) {
            showAlert("Login Failed", "User not found.");
            return;
        }
        if (!user.getPassword().equals(p)) {
            showAlert("Login Failed", "Incorrect password.");
            return;
        }

        try {
            String fxml = user.getRole().equalsIgnoreCase("Admin") ? "AdminDashboard.fxml" : "CustomerDashboard.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            // pass user to controller if needed
            if (fxml.equals("CustomerDashboard.fxml")) {
                CustomerDashboardController c = loader.getController();
                c.setCurrentUser(user);
            }

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load dashboard.");
        }
    }

    @FXML
    private void onSignUp(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load signup.");
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