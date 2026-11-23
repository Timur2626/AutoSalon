import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientOrdersController {

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, String> orderIdColumn;

    @FXML
    private TableColumn<Order, String> carIdColumn;

    @FXML
    private TableColumn<Order, LocalDate> dateColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

    @FXML
    private TableColumn<Order, Double> priceColumn;

    @FXML
    private Label ordersCountLabel;

    private ObservableList<Order> orders;
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    public void initialize() {
        // Инициализация колонок таблицы
        orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        carIdColumn.setCellValueFactory(cellData -> cellData.getValue().carIdProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().orderDateProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        orders = FXCollections.observableArrayList();
        ordersTable.setItems(orders);
    }

    public void loadOrders() {
        // Здесь должна быть логика загрузки заказов из файла или базы данных
        // Для примера создаем тестовые данные
        orders.clear();

        List<Order> userOrders = new ArrayList<>();
        userOrders.add(new Order("ORD001", "Toyota Camry 2022", username,
                LocalDate.now().minusDays(5), "В обработке", 2500000));
        userOrders.add(new Order("ORD002", "Honda CR-V 2023", username,
                LocalDate.now().minusDays(2), "Подтвержден", 2800000));

        orders.addAll(userOrders);
        updateOrdersCount();
    }

    @FXML
    private void handleRefresh() {
        loadOrders();
        showAlert(Alert.AlertType.INFORMATION, "Обновлено", "Список заказов обновлен");
    }

    @FXML
    private void handleCancelOrder() {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) {
            showAlert(Alert.AlertType.WARNING, "Предупреждение", "Выберите заказ для отмены");
            return;
        }

        if ("Подтвержден".equals(selectedOrder.getStatus())) {
            showAlert(Alert.AlertType.WARNING, "Предупреждение",
                    "Нельзя отменить подтвержденный заказ");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Отмена заказа");
        alert.setHeaderText("Подтверждение отмены");
        alert.setContentText("Вы уверены, что хотите отменить заказ " + selectedOrder.getId() + "?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // Здесь должна быть логика отмены заказа
            orders.remove(selectedOrder);
            updateOrdersCount();
            showAlert(Alert.AlertType.INFORMATION, "Успешно", "Заказ отменен");
        }
    }

    private void updateOrdersCount() {
        ordersCountLabel.setText("Всего заказов: " + orders.size());
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}