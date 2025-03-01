// Create the client
// FileTransferClient.java
import java.io.File;
import java.io.FileOutputStream;
import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileTransferClient {
    private static final String CLIENT_FILE_DIR = "client_files";
    private static final String LOG_FILE = "client_log.txt";
    private static PrintWriter logWriter;
    
    private static void log(String message) {
        if (logWriter != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            logWriter.println(sdf.format(new Date()) + " - " + message);
        }
    }
    
    public static void main(String[] args) {
        try {
            // Initialize the log file
            try {
                logWriter = new PrintWriter(new FileWriter(LOG_FILE, true), true);
                log("Client started");
            } catch (IOException e) {
                System.err.println("Error creating log file: " + e.getMessage());
            }
            
            // Get the server address from command line or use localhost
            String serverAddress = args.length > 0 ? args[0] : "localhost";
            log("Connecting to server: " + serverAddress);
            
            long startLookup = System.currentTimeMillis();
            // Look up the remote object
            String url = "rmi://" + serverAddress + "/FileTransferService";
            FileTransferInterface fileTransfer = (FileTransferInterface) Naming.lookup(url);
            long endLookup = System.currentTimeMillis();
            log("Connected to server - Time taken: " + (endLookup - startLookup) + "ms");
            
            // Create client directory if it doesn't exist
            File dir = new File(CLIENT_FILE_DIR);
            if (!dir.exists()) {
                dir.mkdir();
            }
            
            Scanner scanner = new Scanner(System.in);
            
            while (true) {
                System.out.println("\nFile Transfer Menu:");
                System.out.println("1. List files available on server");
                System.out.println("2. Download a file");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        log("Requesting file list");
                        long startList = System.currentTimeMillis();
                        List<String> files = fileTransfer.listFiles();
                        long endList = System.currentTimeMillis();
                        log("Received file list - Time taken: " + (endList - startList) + "ms");
                        
                        System.out.println("\nFiles available on server:");
                        if (files.isEmpty()) {
                            System.out.println("No files found.");
                        } else {
                            for (String file : files) {
                                System.out.println(" - " + file);
                            }
                        }
                        break;
                        
                    case 2:
                        System.out.print("Enter the name of the file to download: ");
                        String fileName = scanner.nextLine();
                        log("Requesting download of file: " + fileName);
                        
                        try {
                            long startDownload = System.currentTimeMillis();
                            byte[] fileData = fileTransfer.downloadFile(fileName);
                            long endDownload = System.currentTimeMillis();
                            log("Received file data (" + fileData.length + " bytes) - Time taken: " + (endDownload - startDownload) + "ms");
                            
                            // Save the file
                            long startSave = System.currentTimeMillis();
                            String filePath = CLIENT_FILE_DIR + File.separator + fileName;
                            FileOutputStream fos = new FileOutputStream(filePath);
                            fos.write(fileData);
                            fos.close();
                            long endSave = System.currentTimeMillis();
                            log("Saved file to disk - Time taken: " + (endSave - startSave) + "ms");
                            
                            System.out.println("File downloaded successfully to: " + filePath);
                            System.out.println("Size: " + fileData.length + " bytes");
                            System.out.println("Total time: " + (endSave - startDownload) + "ms");
                        } catch (Exception e) {
                            log("Error in download: " + e.getMessage());
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                        
                    case 3:
                        log("Client shutting down");
                        System.out.println("Exiting...");
                        if (logWriter != null) {
                            logWriter.close();
                        }
                        scanner.close();
                        return;
                        
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) {
            log("Client error: " + e.getMessage());
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
            if (logWriter != null) {
                logWriter.close();
            }
        }
    }
}