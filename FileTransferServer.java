// Create the server
// FileTransferServer.java
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class FileTransferServer {
    public static void main(String[] args) {
        try {
            // Create registry on port 1099 (or use existing)
            try {
                LocateRegistry.createRegistry(1099);
                System.out.println("Created new registry on port 1099");
            } catch (Exception e) {
                System.out.println("Registry already exists, will reuse it");
            }
            
            // Create and bind the remote object
            FileTransferImpl fileTransfer = new FileTransferImpl();
            Naming.rebind("FileTransferService", fileTransfer);
            
            System.out.println("File Transfer Server is running...");
            System.out.println("Files are located in the 'server_files' directory");
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
