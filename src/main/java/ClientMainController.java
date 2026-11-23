import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class ClientMainController {

    @FXML
    private Label welcomeLabel;

    private String username;

    public void setUsername(String username) {
        this.username = username;
        welcomeLabel.setText("Добро пожаловать, " + username + "!");
    }

    @FXML
    private void handleOpenCatalog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/catalog-view.fxml"));
            Parent root = loader.load();

            CatalogController controller = loader.getController();
            controller.setUsername(username);

            Stage stage = new Stage();
            stage.setTitle("Каталог автомобилей");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleOpenOrders() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client-orders-view.fxml"));
            Parent root = loader.load();

            ClientOrdersController controller = loader.getController();
            controller.setUsername(username);
            controller.loadOrders();

            Stage stage = new Stage();
            stage.setTitle("Мои заказы");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
}