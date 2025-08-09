package fruitshop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class AdminDashboardController {
    @FXML private TableView<Fruit> fruitTable;
    @FXML private TableColumn<Fruit, String> nameCol;
    @FXML private TableColumn<Fruit, Double> priceCol;
    @FXML private TableColumn<Fruit, Integer> stockCol;

    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField stockField;
    @FXML private Button addBtn;
    @FXML private Button updateBtn;
    @FXML private Button deleteBtn;
    @FXML private Button logoutBtn;

    private ObservableList<Fruit> list = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        nameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
        priceCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getPrice()).asObject());
        stockCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getStock()).asObject());
        refreshTable();

        fruitTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                nameField.setText(newSel.getName());
                priceField.setText(String.valueOf(newSel.getPrice()));
                stockField.setText(String.valueOf(newSel.getStock()));
            }
        });
    }

    private void refreshTable() {
        list.setAll(FruitDAO.getAll());
        fruitTable.setItems(list);
    }

    @FXML
    private void onAdd(ActionEvent event) {
        try {
            String n = nameField.getText().trim();
            double p = Double.parseDouble(priceField.getText().trim());
            int s = Integer.parseInt(stockField.getText().trim());
            Fruit f = new Fruit(n, p, s);
            if (FruitDAO.create(f)) {
                refreshTable();
                clearInputs();
            } else showAlert("Error", "Could not add fruit.");
        } catch (NumberFormatException e) {
            showAlert("Validation", "Price and stock must be numeric.");
        }
    }

    @FXML
    private void onUpdate(ActionEvent event) {
        Fruit sel = fruitTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("Select", "Select a fruit to update."); return; }
        try {
            sel.setName(nameField.getText().trim());
            sel.setPrice(Double.parseDouble(priceField.getText().trim()));
            sel.setStock(Integer.parseInt(stockField.getText().trim()));
            if (FruitDAO.update(sel)) {
                refreshTable();
                clearInputs();
            } else showAlert("Error", "Update failed.");
        } catch (NumberFormatException e) {
            showAlert("Validation", "Price and stock must be numeric.");
        }
    }

    @FXML
    private void onDelete(ActionEvent event) {
        Fruit sel = fruitTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("Select", "Select a fruit to delete."); return; }
        if (FruitDAO.delete(sel.getId())) {
            refreshTable();
            clearInputs();
        } else showAlert("Error", "Delete failed.");
    }

    @FXML
    private void onLogout(ActionEvent event) {
        try {
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("Login.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) logoutBtn.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void clearInputs() {
        nameField.clear(); priceField.clear(); stockField.clear();
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}