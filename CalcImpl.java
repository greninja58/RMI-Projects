
// Next, we implement the interface
// CalcImpl.java
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalcImpl extends UnicastRemoteObject implements CalcInterface {
    public CalcImpl() throws RemoteException {
        super();
    }
    
    @Override
    public double add(double a, double b) throws RemoteException {
        return a + b;
    }
    
    @Override
    public double subtract(double a, double b) throws RemoteException {
        return a - b;
    }
    
    @Override
    public double multiply(double a, double b) throws RemoteException {
        return a * b;
    }
    
    @Override
    public double divide(double a, double b) throws RemoteException {
        if (b == 0) {
            throw new RemoteException("Division by zero is not allowed");
        }
        return a / b;
    }
}
