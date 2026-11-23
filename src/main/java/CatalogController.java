import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class CatalogController {

    @FXML
    private TableView<Car> carsTable;

    @FXML
    private TableColumn<Car, String> brandColumn;

    @FXML
    private TableColumn<Car, String> modelColumn;

    @FXML
    private TableColumn<Car, Integer> yearColumn;

    @FXML
    private TableColumn<Car, Double> priceColumn;

    @FXML
    private TableColumn<Car, String> colorColumn;

    @FXML
    private TableColumn<Car, String> fuelColumn;

    @FXML
    private TextField brandFilter;

    @FXML
    private TextField modelFilter;

    @FXML
    private TextField yearFilter;

    @FXML
    private TextField maxPriceFilter;

    @FXML
    private Label selectedCarLabel;

    private ObservableList<Car> allCars;
    private ObservableList<Car> filteredCars;
    private String username;
    private Car selectedCar;

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    public void initialize() {
        // Инициализация колонок таблицы
        brandColumn.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        colorColumn.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        fuelColumn.setCellValueFactory(cellData -> cellData.getValue().fuelTypeProperty());

        // Загрузка данных
        loadCars();

        // Обработчик выбора автомобиля
        carsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCarDetails(newValue));
    }

    private void loadCars() {
        // Создаем тестовые данные
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("1", "Toyota", "Camry", 2022, 2500000, "Черный", "Бензин"));
        cars.add(new Car("2", "Honda", "CR-V", 2023, 2800000, "Белый", "Бензин"));
        cars.add(new Car("3", "BMW", "X5", 2022, 5500000, "Синий", "Дизель"));
        cars.add(new Car("4", "Mercedes", "E-Class", 2023, 4800000, "Серый", "Бензин"));
        cars.add(new Car("5", "Audi", "A4", 2022, 3200000, "Красный", "Бензин"));
        cars.add(new Car("6", "Hyundai", "Solaris", 2023, 1200000, "Белый", "Бензин"));
        cars.add(new Car("7", "Kia", "Rio", 2022, 1100000, "Серый", "Бензин"));
        cars.add(new Car("8", "Toyota", "RAV4", 2023, 3000000, "Черный", "Гибрид"));

        allCars = FXCollections.observableArrayList(cars);
        filteredCars = FXCollections.observableArrayList(cars);
        carsTable.setItems(filteredCars);
    }

    @FXML
    private void handleFilter() {
        filteredCars.clear();

        for (Car car : allCars) {
            boolean matches = true;

            // Фильтр по марке
            if (!brandFilter.getText().isEmpty() &&
                    !car.getBrand().toLowerCase().contains(brandFilter.getText().toLowerCase())) {
                matches = false;
            }

            // Фильтр по модели
            if (!modelFilter.getText().isEmpty() &&
                    !car.getModel().toLowerCase().contains(modelFilter.getText().toLowerCase())) {
                matches = false;
            }

            // Фильтр по году
            if (!yearFilter.getText().isEmpty()) {
                try {
                    int year = Integer.parseInt(yearFilter.getText());
                    if (car.getYear() != year) {
                        matches = false;
                    }
                } catch (NumberFormatException e) {
                    // Игнорируем некорректный ввод
                }
            }

            // Фильтр по цене
            if (!maxPriceFilter.getText().isEmpty()) {
                try {
                    double maxPrice = Double.parseDouble(maxPriceFilter.getText());
                    if (car.getPrice() > maxPrice) {
                        matches = false;
                    }
                } catch (NumberFormatException e) {
                    // Игнорируем некорректный ввод
                }
            }

            if (matches) {
                filteredCars.add(car);
            }
        }
    }

    @FXML
    private void handleClearFilters() {
        brandFilter.clear();
        modelFilter.clear();
        yearFilter.clear();
        maxPriceFilter.clear();
        filteredCars.setAll(allCars);
    }

    @FXML
    private void handleOrder() {
        if (selectedCar == null) {
            showAlert(Alert.AlertType.WARNING, "Предупреждение", "Выберите автомобиль для заказа");
            return;
        }

        try {
            // Открываем диалог оформления заказа
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/order-dialog.fxml"));
            Parent root = loader.load();

            OrderDialogController dialogController = loader.getController();
            dialogController.setCar(selectedCar);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Оформление заказа");
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            if (dialogController.isConfirmed()) {
                // Сохраняем заказ
                saveOrder(selectedCar, dialogController.getClientName(),
                        dialogController.getPhone(), dialogController.getComments());

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Успешно");
                successAlert.setHeaderText("Заказ оформлен");
                successAlert.setContentText("Ваш заказ успешно оформлен! Ожидайте подтверждения.");
                successAlert.showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось открыть диалог заказа");
        }
    }

    private void showCarDetails(Car car) {
        if (car != null) {
            selectedCar = car;
            selectedCarLabel.setText("Выбран: " + car.getBrand() + " " + car.getModel() +
                    " (" + car.getYear() + ") - " + car.getPrice() + " руб.");
        } else {
            selectedCarLabel.setText("Выберите автомобиль");
        }
    }

    private void saveOrder(Car car, String clientName, String phone, String comments) {
        // Здесь должна быть логика сохранения заказа в файл или базу данных
        // Для примера просто выводим в консоль
        System.out.println("Новый заказ: " + username + " заказал " +
                car.getBrand() + " " + car.getModel() +
                " для " + clientName + " (" + phone + ")");
        if (!comments.isEmpty()) {
            System.out.println("Комментарий: " + comments);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}