import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class PreparerMainController {

    @FXML
    private Label welcomeLabel;

    private String username;

    public void setUsername(String username) {
        this.username = username;
        welcomeLabel.setText("Подготовщик: " + username);
    }

    @FXML
    private void handleViewPreparations() {
        // Логика просмотра заказов на подготовку
        showAlert("Подготовка автомобилей", "Функция подготовки автомобилей");
    }

    @FXML
    private void handleUpdateStatus() {
        // Логика обновления статуса подготовки
        showAlert("Обновление статуса", "Функция обновления статуса");
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