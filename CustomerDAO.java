import java.sql.*;
import java.util.Scanner;


public class CustomerDAO {
    private Connection conn;

    public CustomerDAO(Connection conn) {
        this.conn = conn;
    }

    // Add a new customer
    public void addCustomer(Scanner scanner) {
        try {
            System.out.print("Enter driver license number: ");
            String license = scanner.nextLine();
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter address: ");
            String address = scanner.nextLine();
            System.out.print("Enter zip: ");
            String zip = scanner.nextLine();
            System.out.print("Enter city: ");
            String city = scanner.nextLine();
            System.out.print("Enter mobile phone: ");
            String mobile = scanner.nextLine();
            System.out.print("Enter phone: ");
            String phone = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter driver since (YYYY-MM-DD): ");
            String driverSince = scanner.nextLine();

            String sql = "INSERT INTO Customer (driver_license_number, name, address, zip, city, mobile_phone, phone, email, driver_since) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, license);
                pstmt.setString(2, name);
                pstmt.setString(3, address);
                pstmt.setString(4, zip);
                pstmt.setString(5, city);
                pstmt.setString(6, mobile);
                pstmt.setString(7, phone);
                pstmt.setString(8, email);
                pstmt.setDate(9, Date.valueOf(driverSince));
                pstmt.executeUpdate();
                System.out.println("Customer added successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    // Update an existing customers email
    public void updateCustomer(Scanner scanner) {
        try {
            System.out.print("Enter driver license number of customer to update: ");
            String license = scanner.nextLine();
            System.out.print("Enter new email: ");
            String email = scanner.nextLine();
            String sql = "UPDATE Customer SET email = ? WHERE driver_license_number = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, license);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0)
                    System.out.println("Customer updated successfully.");
                else
                    System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }
    }

    // Deletes a customer record.
    public void deleteCustomer(Scanner scanner) {
        try {
            System.out.print("Enter driver license number of customer to delete: ");
            String license = scanner.nextLine();
            String sql = "DELETE FROM Customer WHERE driver_license_number = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, license);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0)
                    System.out.println("Customer deleted successfully.");
                else
                    System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        }
    }

    // Lists all customer records.
    public void listCustomers() {
        String sql = "SELECT * FROM Customer";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Customers ---");
            while (rs.next()) {
                System.out.println("License: " + rs.getString("driver_license_number") +
                        ", Name: " + rs.getString("name") +
                        ", City: " + rs.getString("city"));
            }
        } catch (SQLException e) {
            System.out.println("Error listing customers: " + e.getMessage());
        }
    }
}
