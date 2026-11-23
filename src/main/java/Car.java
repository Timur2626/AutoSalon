import javafx.beans.property.*;

public class Car {
    private final StringProperty id;
    private final StringProperty brand;
    private final StringProperty model;
    private final IntegerProperty year;
    private final DoubleProperty price;
    private final StringProperty color;
    private final StringProperty fuelType;

    public Car() {
        this.id = new SimpleStringProperty();
        this.brand = new SimpleStringProperty();
        this.model = new SimpleStringProperty();
        this.year = new SimpleIntegerProperty();
        this.price = new SimpleDoubleProperty();
        this.color = new SimpleStringProperty();
        this.fuelType = new SimpleStringProperty();
    }

    public Car(String id, String brand, String model, int year, double price, String color, String fuelType) {
        this();
        setId(id);
        setBrand(brand);
        setModel(model);
        setYear(year);
        setPrice(price);
        setColor(color);
        setFuelType(fuelType);
    }

    // Геттеры и сеттеры для свойств
    public String getId() { return id.get(); }
    public void setId(String id) { this.id.set(id); }
    public StringProperty idProperty() { return id; }

    public String getBrand() { return brand.get(); }
    public void setBrand(String brand) { this.brand.set(brand); }
    public StringProperty brandProperty() { return brand; }

    public String getModel() { return model.get(); }
    public void setModel(String model) { this.model.set(model); }
    public StringProperty modelProperty() { return model; }

    public int getYear() { return year.get(); }
    public void setYear(int year) { this.year.set(year); }
    public IntegerProperty yearProperty() { return year; }

    public double getPrice() { return price.get(); }
    public void setPrice(double price) { this.price.set(price); }
    public DoubleProperty priceProperty() { return price; }

    // Метод getBasePrice() - алиас для getPrice()
    public double getBasePrice() { return getPrice(); }

    public String getColor() { return color.get(); }
    public void setColor(String color) { this.color.set(color); }
    public StringProperty colorProperty() { return color; }

    public String getFuelType() { return fuelType.get(); }
    public void setFuelType(String fuelType) { this.fuelType.set(fuelType); }
    public StringProperty fuelTypeProperty() { return fuelType; }

    // Метод getName() для отображения в интерфейсе
    public String getName() {
        return getBrand() + " " + getModel() + " (" + getYear() + ")";
    }

    @Override
    public String toString() {
        return getName() + " - " + getPrice() + " руб.";
    }
}