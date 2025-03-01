// Now, we create the server
// CalcServer.java
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class CalcServer {
    public static void main(String[] args) {
        try {
            // Create a registry on port 1099 (default port for RMI)
            LocateRegistry.createRegistry(1099);
            
            // Create an instance of the remote object
            CalcImpl calc = new CalcImpl();
            
            // Bind the remote object to the registry
            Naming.rebind("CalculatorService", calc);
            
            System.out.println("Calculator Server is running...");
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
