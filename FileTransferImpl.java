import java.io.*;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileTransferImpl extends UnicastRemoteObject implements FileTransferInterface {
    private static final String SERVER_FILE_DIR = "server_files";
    private static final String LOG_FILE = "server_log.txt";
    private PrintWriter logWriter;
    
    public FileTransferImpl() throws RemoteException {
        super();
        // Create the directory if it doesn't exist
        File dir = new File(SERVER_FILE_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        // Initialize the log file
        try {
            logWriter = new PrintWriter(new FileWriter(LOG_FILE, true), true);
            log("Server started");
        } catch (IOException e) {
            System.err.println("Error creating log file: " + e.getMessage());
        }
    }
    
    private void log(String message) {
        if (logWriter != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            logWriter.println(sdf.format(new Date()) + " - " + message);
        }
    }
    
    @Override
    public byte[] downloadFile(String fileName) throws RemoteException {
        long startTime = System.currentTimeMillis();
        log("Download request received for file: " + fileName);
        
        try {
            File file = new File(SERVER_FILE_DIR + File.separator + fileName);
            if (!file.exists()) {
                log("File not found: " + fileName);
                throw new RemoteException("File not found: " + fileName);
            }
            
            FileInputStream fis = new FileInputStream(file);
            byte[] fileData = new byte[(int) file.length()];
            fis.read(fileData);
            fis.close();
            
            long endTime = System.currentTimeMillis();
            log("Sent file: " + fileName + " (" + fileData.length + " bytes) - Time taken: " + (endTime - startTime) + "ms");
            
            return fileData;
        } catch (IOException e) {
            log("Error reading file: " + e.getMessage());
            throw new RemoteException("Error reading file: " + e.getMessage());
        }
    }
    
    @Override
    public List<String> listFiles() throws RemoteException {
        long startTime = System.currentTimeMillis();
        log("List files request received");
        
        File dir = new File(SERVER_FILE_DIR);
        String[] fileNames = dir.list();
        List<String> result = new ArrayList<>();
        
        if (fileNames != null) {
            for (String fileName : fileNames) {
                result.add(fileName);
            }
        }
        
        long endTime = System.currentTimeMillis();
        log("Listed " + (fileNames != null ? fileNames.length : 0) + " files - Time taken: " + (endTime - startTime) + "ms");
        
        return result;
    }
}