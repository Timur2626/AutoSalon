import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class OrderDialogController {

    @FXML
    private Label carInfoLabel;

    @FXML
    private TextField clientNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextArea commentsArea;

    private Car selectedCar;
    private boolean confirmed = false;

    @FXML
    public void initialize() {
        // Инициализация контроллера
    }

    public void setCar(Car car) {
        this.selectedCar = car;
        if (car != null) {
            carInfoLabel.setText(car.getName() + " - " + car.getPrice() + " руб.");
        }
    }

    @FXML
    private void handleConfirm() {
        if (clientNameField.getText().isEmpty() || phoneField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Заполните обязательные поля: ФИО и телефон");
            return;
        }

        confirmed = true;
        closeDialog();
    }

    @FXML
    private void handleCancel() {
        confirmed = false;
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) carInfoLabel.getScene().getWindow();
        stage.close();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getClientName() {
        return clientNameField.getText();
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public String getComments() {
        return commentsArea.getText();
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}