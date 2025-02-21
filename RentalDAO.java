import java.sql.*;
import java.util.Scanner;

public class RentalDAO {
    private Connection conn;

    public RentalDAO(Connection conn) {
        this.conn = conn;
    }

    // Add a new rental record.
    public void addRental(Scanner scanner) {
        try {
            // Get the customer driver license number and car registration number
            System.out.print("Enter customer driver license number: ");
            String license = scanner.nextLine();
            System.out.print("Enter car registration number: ");
            String regNum = scanner.nextLine();

            // Query the Car table to retrieve the current odometer reading
            int startOdometer = 0;
            String sqlCar = "SELECT odometer FROM Car WHERE registration_number = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlCar)) {
                pstmt.setString(1, regNum);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Set the starting odometer value from the Car record
                        startOdometer = rs.getInt("odometer");
                    } else {
                        System.out.println("Car not found for registration number: " + regNum);
                        return; // Exit if the car doesn't exist
                    }
                }
            }

            // Get rental start and end times, and max km from the user
            System.out.print("Enter rental start (YYYY-MM-DD HH:MM:SS): ");
            String start = scanner.nextLine();
            System.out.print("Enter rental end (YYYY-MM-DD HH:MM:SS): ");
            String end = scanner.nextLine();
            System.out.print("Enter max km: ");
            int maxKm = Integer.parseInt(scanner.nextLine());

            // Insert the new rental record using the fetched starting odometer value
            String sql = "INSERT INTO Rental (customer_license, registration_number, rental_start, rental_end, max_km, start_odometer) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, license);
                pstmt.setString(2, regNum);
                pstmt.setTimestamp(3, Timestamp.valueOf(start));
                pstmt.setTimestamp(4, Timestamp.valueOf(end));
                pstmt.setInt(5, maxKm);
                pstmt.setInt(6, startOdometer);
                pstmt.executeUpdate();
                System.out.println("Rental added successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding rental: " + e.getMessage());
        }
    }


    // Update an existing rental record
    public void updateRental(Scanner scanner) {
        try {
            System.out.print("Enter rental ID to update: ");
            int rentalId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter new max km: ");
            int maxKm = Integer.parseInt(scanner.nextLine());
            String sql = "UPDATE Rental SET max_km = ? WHERE rental_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, maxKm);
                pstmt.setInt(2, rentalId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0)
                    System.out.println("Rental updated successfully.");
                else
                    System.out.println("Rental not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating rental: " + e.getMessage());
        }
    }

    // delete a rental record
    public void deleteRental(Scanner scanner) {
        try {
            System.out.print("Enter rental ID to delete: ");
            int rentalId = Integer.parseInt(scanner.nextLine());
            String sql = "DELETE FROM Rental WHERE rental_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, rentalId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0)
                    System.out.println("Rental deleted successfully.");
                else
                    System.out.println("Rental not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting rental: " + e.getMessage());
        }
    }

    // List all rental records
    public void listRentals() {
        String sql = "SELECT r.rental_id, r.rental_start, r.rental_end, r.max_km, r.start_odometer, " +
                "c.name, car.brand, car.model " +
                "FROM Rental r " +
                "JOIN Customer c ON r.customer_license = c.driver_license_number " +
                "JOIN Car car ON r.registration_number = car.registration_number";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Rentals ---");
            while (rs.next()) {
                System.out.println("Rental ID: " + rs.getInt("rental_id") +
                        ", Customer: " + rs.getString("name") +
                        ", Car: " + rs.getString("brand") + " " + rs.getString("model") +
                        ", Start: " + rs.getTimestamp("rental_start") +
                        ", End: " + rs.getTimestamp("rental_end"));
            }
        } catch (SQLException e) {
            System.out.println("Error listing rentals: " + e.getMessage());
        }
    }
}
