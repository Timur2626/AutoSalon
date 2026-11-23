import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Заполните все поля");
            return;
        }

        User user = new User();
        if (user.authenticate(username, password)) {
            try {
                // Закрываем текущее окно
                Stage currentStage = (Stage) usernameField.getScene().getWindow();
                currentStage.close();

                // Открываем соответствующее окно в зависимости от роли
                openRoleSpecificWindow(user.getRole(), username);

            } catch (IOException e) {
                e.printStackTrace();
                showError("Ошибка загрузки приложения");
            }
        } else {
            showError("Неверный логин или пароль");
        }
    }

    private void openRoleSpecificWindow(String role, String username) throws IOException {
        String fxmlFile;
        String title;

        switch (role) {
            case "admin":
            case "manager":
                fxmlFile = "/manager-main-view.fxml";
                title = "Автосалон - Панель менеджера";
                break;
            case "preparer":
                fxmlFile = "/preparer-main-view.fxml";
                title = "Автосалон - Панель подготовщика";
                break;
            case "client":
            default:
                fxmlFile = "/client-main-view.fxml";
                title = "Автосалон - Главная";
                break;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        // Устанавливаем имя пользователя в контроллер
        switch (role) {
            case "admin":
            case "manager":
                ManagerMainController managerController = loader.getController();
                managerController.setUsername(username);
                break;
            case "preparer":
                PreparerMainController preparerController = loader.getController();
                preparerController.setUsername(username);
                break;
            case "client":
            default:
                ClientMainController clientController = loader.getController();
                clientController.setUsername(username);
                break;
        }

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleCancel() {
        usernameField.clear();
        passwordField.clear();
        errorLabel.setText("");
    }

    @FXML
    private void handleRegister() {
        try {
            // Закрываем текущее окно
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();

            // Открываем окно регистрации
            Parent root = FXMLLoader.load(getClass().getResource("/register-view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Автосалон - Регистрация");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка входа");
        alert.setContentText(message);
        alert.showAndWait();
    }
}