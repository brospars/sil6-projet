import java.rmi.Naming;
import java.rmi.Remote;

public class SecondeServiceImpl implements SecondService, Remote{
	
	public static void main(String args[]) throws Exception {
		ThirdService thirdService;
		
		// Ecoute le port 2000 pour communiquer avec le 3em Tiers
		thirdService = (ThirdService) Naming.lookup("rmi://localhost:2000/ThirdService");
		
		//Creer un utilisateur test et le sauvegarde
		Croakos croakos = new Croakos("Benoit","123456");
		
		thirdService.saveUser(croakos);
	}
	
}
