import javafx.beans.property.*;
import java.time.LocalDate;

public class Order {
    private final StringProperty id;
    private final StringProperty carId;
    private final StringProperty username;
    private final ObjectProperty<LocalDate> orderDate;
    private final StringProperty status;
    private final DoubleProperty totalPrice;

    public Order() {
        this.id = new SimpleStringProperty();
        this.carId = new SimpleStringProperty();
        this.username = new SimpleStringProperty();
        this.orderDate = new SimpleObjectProperty<>();
        this.status = new SimpleStringProperty();
        this.totalPrice = new SimpleDoubleProperty();
    }

    public Order(String id, String carId, String username, LocalDate orderDate, String status, double totalPrice) {
        this();
        setId(id);
        setCarId(carId);
        setUsername(username);
        setOrderDate(orderDate);
        setStatus(status);
        setTotalPrice(totalPrice);
    }

    // Геттеры и сеттеры для свойств
    public String getId() { return id.get(); }
    public void setId(String id) { this.id.set(id); }
    public StringProperty idProperty() { return id; }

    public String getCarId() { return carId.get(); }
    public void setCarId(String carId) { this.carId.set(carId); }
    public StringProperty carIdProperty() { return carId; }

    public String getUsername() { return username.get(); }
    public void setUsername(String username) { this.username.set(username); }
    public StringProperty usernameProperty() { return username; }

    public LocalDate getOrderDate() { return orderDate.get(); }
    public void setOrderDate(LocalDate orderDate) { this.orderDate.set(orderDate); }
    public ObjectProperty<LocalDate> orderDateProperty() { return orderDate; }

    public String getStatus() { return status.get(); }
    public void setStatus(String status) { this.status.set(status); }
    public StringProperty statusProperty() { return status; }

    public double getTotalPrice() { return totalPrice.get(); }
    public void setTotalPrice(double totalPrice) { this.totalPrice.set(totalPrice); }
    public DoubleProperty totalPriceProperty() { return totalPrice; }
}