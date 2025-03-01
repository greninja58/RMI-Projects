// First, define the remote interface
// FileTransferInterface.java
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FileTransferInterface extends Remote {
    byte[] downloadFile(String fileName) throws RemoteException;
    List<String> listFiles() throws RemoteException;
}