import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class ManagerMainController {

    @FXML
    private Label welcomeLabel;

    private String username;

    public void setUsername(String username) {
        this.username = username;
        welcomeLabel.setText("Менеджер: " + username);
    }

    @FXML
    private void handleViewOrders() {
        // Логика просмотра заказов для менеджера
        showAlert("Просмотр заказов", "Функция просмотра заказов");
    }

    @FXML
    private void handleProcessOrder() {
        // Логика обработки заказов
        showAlert("Обработка заказов", "Функция обработки заказов");
    }

    @FXML
    private void handleReports() {
        // Логика отчетов
        showAlert("Отчеты", "Функция отчетов");
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.close();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/login-view.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Автосалон - Вход");
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}