package fruitshop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

public class CustomerDashboardController {
    @FXML private TableView<Fruit> fruitTable;
    @FXML private TableColumn<Fruit, String> nameCol;
    @FXML private TableColumn<Fruit, Double> priceCol;
    @FXML private TableColumn<Fruit, Integer> stockCol;

    @FXML private TextField quantityField;
    @FXML private Button addToCartBtn;
    @FXML private Button logoutBtn;

    @FXML private Label cartSummary;

    private User currentUser;
    private ObservableList<Fruit> list = FXCollections.observableArrayList();
    private List<Fruit> cart = new ArrayList<>();

    public void setCurrentUser(User u) {
        this.currentUser = u;
    }

    @FXML
    private void initialize() {
        nameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
        priceCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getPrice()).asObject());
        stockCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getStock()).asObject());
        refreshTable();
        updateCartLabel();
    }

    private void refreshTable() {
        list.setAll(FruitDAO.getAll());
        fruitTable.setItems(list);
    }

    @FXML
    private void onAddToCart(ActionEvent event) {
        Fruit sel = fruitTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("Select", "Select a fruit to add."); return; }
        int q = 1;
        try {
            q = Integer.parseInt(quantityField.getText().trim());
        } catch (Exception e) { /* ignore, default 1 */ }
        if (q <= 0) { showAlert("Validation", "Quantity must be positive."); return; }
        if (q > sel.getStock()) { showAlert("Stock", "Not enough stock available."); return; }
        // simple in-memory cart: add a copy of the fruit with requested quantity encoded in stock field
        Fruit copy = new Fruit(sel.getId(), sel.getName(), sel.getPrice(), q);
        cart.add(copy);
        updateCartLabel();
        showAlert("Added", "Added to cart.");
    }

    @FXML
    private void onLogout(ActionEvent event) {
        try {
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("Login.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) logoutBtn.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void updateCartLabel() {
        int totalItems = cart.stream().mapToInt(Fruit::getStock).sum();
        double totalPrice = cart.stream().mapToDouble(f -> f.getPrice() * f.getStock()).sum();
        cartSummary.setText("Cart: " + totalItems + " items | Total: $" + String.format("%.2f", totalPrice));
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}