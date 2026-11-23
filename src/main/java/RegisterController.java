import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Заполните все поля");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Пароли не совпадают");
            return;
        }

        if (password.length() < 4) {
            showError("Пароль должен содержать минимум 4 символа");
            return;
        }

        User user = new User();
        if (user.register(username, password, "client")) {
            showSuccess();
            handleBackToLogin();
        } else {
            showError("Пользователь с таким логином уже существует");
        }
    }

    @FXML
    private void handleCancel() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        errorLabel.setText("");
    }

    @FXML
    private void handleBackToLogin() {
        try {
            // Закрываем текущее окно
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();

            // Открываем окно входа
            Parent root = FXMLLoader.load(getClass().getResource("/login-view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Автосалон - Вход");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }

    private void showSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешно");
        alert.setHeaderText("Регистрация завершена");
        alert.setContentText("Пользователь успешно зарегистрирован!");
        alert.showAndWait();
    }
}