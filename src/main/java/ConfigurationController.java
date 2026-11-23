import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import java.io.IOException;

public class ConfigurationController {

    @FXML
    private Label carModelLabel;

    @FXML
    private Label basePriceLabel;

    @FXML
    private ComboBox<String> colorComboBox;

    @FXML
    private ComboBox<String> interiorComboBox;

    @FXML
    private CheckBox leatherSeatsCheckBox;

    @FXML
    private CheckBox sunroofCheckBox;

    @FXML
    private CheckBox navigationCheckBox;

    @FXML
    private CheckBox premiumAudioCheckBox;

    @FXML
    private Label totalPriceLabel;

    private Car selectedCar;
    private User currentUser;
    private double basePrice;
    private double totalPrice;

    public void setCar(Car car) {
        this.selectedCar = car;
        if (car != null) {
            carModelLabel.setText(car.getName());
            basePrice = car.getPrice();
            basePriceLabel.setText(String.format("%.2f руб.", basePrice));
            calculateTotalPrice();
        }
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    public void initialize() {
        // Инициализация ComboBox
        colorComboBox.getItems().addAll("Черный", "Белый", "Серый", "Синий", "Красный", "Зеленый");
        interiorComboBox.getItems().addAll("Ткань", "Кожа", "Алькантара", "Комбинированный");

        // Установка значений по умолчанию
        colorComboBox.setValue("Черный");
        interiorComboBox.setValue("Ткань");

        // Добавление слушателей изменений
        colorComboBox.valueProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice());
        interiorComboBox.valueProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice());

        leatherSeatsCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice());
        sunroofCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice());
        navigationCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice());
        premiumAudioCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice());
    }

    private void calculateTotalPrice() {
        totalPrice = basePrice;

        // Доплаты за цвет
        String color = colorComboBox.getValue();
        if ("Металлик".equals(color)) {
            totalPrice += 50000;
        } else if ("Перламутр".equals(color)) {
            totalPrice += 80000;
        }

        // Доплаты за интерьер
        String interior = interiorComboBox.getValue();
        if ("Кожа".equals(interior)) {
            totalPrice += 100000;
        } else if ("Алькантара".equals(interior)) {
            totalPrice += 70000;
        } else if ("Комбинированный".equals(interior)) {
            totalPrice += 120000;
        }

        // Доплаты за опции
        if (leatherSeatsCheckBox.isSelected()) {
            totalPrice += 50000;
        }
        if (sunroofCheckBox.isSelected()) {
            totalPrice += 80000;
        }
        if (navigationCheckBox.isSelected()) {
            totalPrice += 40000;
        }
        if (premiumAudioCheckBox.isSelected()) {
            totalPrice += 30000;
        }

        totalPriceLabel.setText(String.format("%.2f руб.", totalPrice));
    }

    @FXML
    private void handleConfirmConfiguration() {
        if (selectedCar == null || currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не выбран автомобиль или пользователь");
            return;
        }

        // Создаем конфигурацию
        String configuration = buildConfigurationString();

        // Сохраняем заказ с конфигурацией
        saveConfiguredOrder(configuration);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setHeaderText("Конфигурация сохранена");
        alert.setContentText("Ваш автомобиль сконфигурирован и заказ оформлен!");
        alert.showAndWait();

        // Закрываем окно
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private String buildConfigurationString() {
        StringBuilder config = new StringBuilder();
        config.append("Автомобиль: ").append(selectedCar.getName()).append("\n");
        config.append("Цвет: ").append(colorComboBox.getValue()).append("\n");
        config.append("Интерьер: ").append(interiorComboBox.getValue()).append("\n");
        config.append("Опции: ");

        boolean hasOptions = false;
        if (leatherSeatsCheckBox.isSelected()) {
            config.append("Кожаные сиденья, ");
            hasOptions = true;
        }
        if (sunroofCheckBox.isSelected()) {
            config.append("Люк, ");
            hasOptions = true;
        }
        if (navigationCheckBox.isSelected()) {
            config.append("Навигация, ");
            hasOptions = true;
        }
        if (premiumAudioCheckBox.isSelected()) {
            config.append("Премиум-аудио, ");
            hasOptions = true;
        }

        if (hasOptions) {
            config.setLength(config.length() - 2); // Удаляем последнюю запятую и пробел
        } else {
            config.append("нет");
        }

        config.append("\nИтоговая цена: ").append(String.format("%.2f руб.", totalPrice));

        return config.toString();
    }

    private void saveConfiguredOrder(String configuration) {
        // Здесь должна быть логика сохранения заказа с конфигурацией
        // Для примера выводим в консоль
        System.out.println("Конфигурированный заказ для пользователя: " + currentUser.getUsername());
        System.out.println(configuration);
        System.out.println("---");

        // Сохраняем в файл
        try {
            java.io.BufferedWriter writer = new java.io.BufferedWriter(
                    new java.io.FileWriter("configured_orders.txt", true));
            writer.write("Пользователь: " + currentUser.getUsername());
            writer.newLine();
            writer.write(configuration);
            writer.newLine();
            writer.write("---");
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setConfiguration(String configuration) {
        // Метод для установки существующей конфигурации (если редактируем)
        // В данной реализации просто игнорируем, так как создаем новую конфигурацию
    }

    public void setTotalPrice(double price) {
        this.totalPrice = price;
        totalPriceLabel.setText(String.format("%.2f руб.", price));
    }

    private void closeWindow() {
        Stage stage = (Stage) carModelLabel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}