// Finally, we create the client
// CalcClient.java
import java.rmi.Naming;
import java.util.Scanner;

public class CalcClient {
    public static void main(String[] args) {
        try {
            // Get the server address from command line or use localhost
            String serverAddress = args.length > 0 ? args[0] : "localhost";
            
            // Look up the remote object
            String url = "rmi://" + serverAddress + "/CalculatorService";
            CalcInterface calc = (CalcInterface) Naming.lookup(url);
            
            Scanner scanner = new Scanner(System.in);
            
            while (true) {
                System.out.println("\nCalculator Menu:");
                System.out.println("1. Addition");
                System.out.println("2. Subtraction");
                System.out.println("3. Multiplication");
                System.out.println("4. Division");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                
                int choice = scanner.nextInt();
                
                if (choice == 5) {
                    System.out.println("Exiting...");
                    break;
                }
                
                System.out.print("Enter first number: ");
                double a = scanner.nextDouble();
                System.out.print("Enter second number: ");
                double b = scanner.nextDouble();
                
                double result = 0;
                
                switch (choice) {
                    case 1:
                        result = calc.add(a, b);
                        System.out.println("Result: " + result);
                        break;
                    case 2:
                        result = calc.subtract(a, b);
                        System.out.println("Result: " + result);
                        break;
                    case 3:
                        result = calc.multiply(a, b);
                        System.out.println("Result: " + result);
                        break;
                    case 4:
                        try {
                            result = calc.divide(a, b);
                            System.out.println("Result: " + result);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
            
            scanner.close();
            
        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}