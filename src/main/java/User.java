import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private String role;
    private String fullName;
    private String email;
    private String phone;

    public User() {}

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, String role, String fullName, String email, String phone) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    // Геттеры и сеттеры
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    // Проверка логина и пароля
    public boolean authenticate(String inputUsername, String inputPassword) {
        try {
            File file = new File("users.txt");
            if (!file.exists()) {
                // Создаем тестовых пользователей если файла нет
                createDefaultUsers();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(":");
                if (userData.length >= 3 &&
                        userData[0].equals(inputUsername) &&
                        userData[1].equals(inputPassword)) {
                    this.username = userData[0];
                    this.password = userData[1];
                    this.role = userData[2];

                    // Дополнительные поля, если есть
                    if (userData.length > 3) this.fullName = userData[3];
                    if (userData.length > 4) this.email = userData[4];
                    if (userData.length > 5) this.phone = userData[5];

                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Регистрация нового пользователя
    public boolean register(String username, String password, String role, String fullName, String email, String phone) {
        // Проверяем, существует ли пользователь
        if (userExists(username)) {
            return false;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));
            writer.write(username + ":" + password + ":" + role + ":" + fullName + ":" + email + ":" + phone);
            writer.newLine();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Перегруженный метод для обратной совместимости
    public boolean register(String username, String password, String role) {
        return register(username, password, role, "", "", "");
    }

    // Проверка существования пользователя
    private boolean userExists(String username) {
        try {
            File file = new File("users.txt");
            if (!file.exists()) return false;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(":");
                if (userData.length > 0 && userData[0].equals(username)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Создание тестовых пользователей
    private void createDefaultUsers() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"));
            writer.write("admin:admin:admin:Администратор Системы:admin@autosalon.ru:+79990000000");
            writer.newLine();
            writer.write("manager:manager:manager:Менеджер Иванов:manager@autosalon.ru:+79990000001");
            writer.newLine();
            writer.write("preparer:preparer:preparer:Подготовщик Петров:preparer@autosalon.ru:+79990000002");
            writer.newLine();
            writer.write("user:user:client:Пользователь Сидоров:user@mail.ru:+79990000003");
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return username + " (" + fullName + ") - " + role;
    }
}