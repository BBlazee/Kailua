import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class CarRentalApp {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kailua";
    private static final String USER = "demo_user";
    private static final String PASS = "demo_password";

    public void run() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Scanner scanner = new Scanner(System.in)) {

            // Instantiate DAO objects
            CarDAO carDAO = new CarDAO(conn);
            CustomerDAO customerDAO = new CustomerDAO(conn);
            RentalDAO rentalDAO = new RentalDAO(conn);

            // menu
            boolean running = true;
            while (running) {
                System.out.println("\n--- Kailua CarRental Management System ---");
                System.out.println("1. Add Car");
                System.out.println("2. Update Car");
                System.out.println("3. Delete Car");
                System.out.println("4. List Cars");
                System.out.println("5. Add Customer");
                System.out.println("6. Update Customer");
                System.out.println("7. Delete Customer");
                System.out.println("8. List Customers");
                System.out.println("9. Add Rental");
                System.out.println("10. Update Rental");
                System.out.println("11. Delete Rental");
                System.out.println("12. List Rentals");
                System.out.println("0. Exit");
                System.out.print("Select an option: ");

                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: carDAO.addCar(scanner); break;
                    case 2: carDAO.updateCar(scanner); break;
                    case 3: carDAO.deleteCar(scanner); break;
                    case 4: carDAO.listCars(); break;
                    case 5: customerDAO.addCustomer(scanner); break;
                    case 6: customerDAO.updateCustomer(scanner); break;
                    case 7: customerDAO.deleteCustomer(scanner); break;
                    case 8: customerDAO.listCustomers(); break;
                    case 9: rentalDAO.addRental(scanner); break;
                    case 10: rentalDAO.updateRental(scanner); break;
                    case 11: rentalDAO.deleteRental(scanner); break;
                    case 12: rentalDAO.listRentals(); break;
                    case 0: running = false; break;
                    default: System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
