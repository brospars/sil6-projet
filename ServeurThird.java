import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ServeurThird {
	public static void main(String [] args) throws Exception {
		  Registry registry;
		
		  ThirdServiceImpl thirdService = new ThirdServiceImpl();
		  
		  ThirdService thirdServiceStub = (ThirdService) UnicastRemoteObject.exportObject(thirdService, 0);
		  
		  registry = LocateRegistry.createRegistry(2000);
		
		  registry.bind("ThirdService", thirdService);

		  System.out.println("Server started");
		  System.in.read();
	 }
}
