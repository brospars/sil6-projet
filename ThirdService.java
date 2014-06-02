import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;



public interface ThirdService extends Remote {
	public void saveUser(Croakos user) throws RemoteException ;
	public Croakos getUser(String name) throws RemoteException ;
	public ArrayList<Croakos> getAllUsers() throws RemoteException ;
	public void saveAllCroaks(ArrayList<Croak> listeCroak) throws RemoteException ;
	public ArrayList<Croak> getAllCroaks() throws RemoteException ;
}
