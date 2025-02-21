import java.sql.*;
import java.util.Scanner;

// This class handles all database operations related to the "Car" entity.
public class CarDAO {
    private Connection conn;

    public CarDAO(Connection conn) {
        this.conn = conn;
    }

    // Adds a new car record.
    public void addCar(Scanner scanner) {
        try {
            System.out.print("Enter registration number: ");
            String regNum = scanner.nextLine();
            System.out.print("Enter brand: ");
            String brand = scanner.nextLine();
            System.out.print("Enter model: ");
            String model = scanner.nextLine();
            System.out.print("Enter fuel type: ");
            String fuel = scanner.nextLine();
            System.out.print("Enter registration year: ");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter registration month: ");
            int month = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter odometer reading: ");
            int odometer = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter category (Luxury, Family, Sport): ");
            String category = scanner.nextLine();

            String sql = "INSERT INTO Car (registration_number, brand, model, fuel_type, registration_year, registration_month, odometer, category) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, regNum);
                pstmt.setString(2, brand);
                pstmt.setString(3, model);
                pstmt.setString(4, fuel);
                pstmt.setInt(5, year);
                pstmt.setInt(6, month);
                pstmt.setInt(7, odometer);
                pstmt.setString(8, category);
                pstmt.executeUpdate();
                System.out.println("Car added successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding car: " + e.getMessage());
        }
    }

    // Updates an existing car record (e.g., updating the odometer reading).
    public void updateCar(Scanner scanner) {
        try {
            System.out.print("Enter registration number of car to update: ");
            String regNum = scanner.nextLine();
            System.out.print("Enter new odometer reading: ");
            int odometer = Integer.parseInt(scanner.nextLine());
            String sql = "UPDATE Car SET odometer = ? WHERE registration_number = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, odometer);
                pstmt.setString(2, regNum);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0)
                    System.out.println("Car updated successfully.");
                else
                    System.out.println("Car not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating car: " + e.getMessage());
        }
    }

    // Deletes a car record.
    public void deleteCar(Scanner scanner) {
        try {
            System.out.print("Enter registration number of car to delete: ");
            String regNum = scanner.nextLine();
            String sql = "DELETE FROM Car WHERE registration_number = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, regNum);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0)
                    System.out.println("Car deleted successfully.");
                else
                    System.out.println("Car not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting car: " + e.getMessage());
        }
    }

    // Lists all car records.
    public void listCars() {
        String sql = "SELECT * FROM Car";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Cars ---");
            while (rs.next()) {
                System.out.println("Registration#: " + rs.getString("registration_number") +
                        ", Brand: " + rs.getString("brand") +
                        ", Model: " + rs.getString("model") +
                        ", Category: " + rs.getString("category") +
                        ", Odometer: " + rs.getInt("odometer"));
            }
        } catch (SQLException e) {
            System.out.println("Error listing cars: " + e.getMessage());
        }
    }
}
