import java.rmi.Naming;
import java.rmi.Remote;
import java.util.ArrayList;

public class SecondServiceImpl extends Thread implements SecondService, Remote{
	
	private ArrayList<Croakos> liste_croakos;
	
	public SecondServiceImpl(){
		liste_croakos = new ArrayList<Croakos>();
	}
	
	public static void main(String args[]) throws Exception {
		ThirdService thirdService;
		// Ecoute le port 2000 pour communiquer avec le 3em Tiers
		thirdService = (ThirdService) Naming.lookup("rmi://localhost:2000/ThirdService");
		
		SecondServiceImpl secondService = new SecondServiceImpl();
		
		//Creer des utilisateurs test et le sauvegarde
		Croakos croakos = new Croakos("Benoit","123456");
		thirdService.saveUser(croakos);
		croakos = new Croakos("Boris","123456");
		thirdService.saveUser(croakos);
		
		//Recupération des utilisateurs après en avoir créé
		secondService.setListeCroakos(thirdService.getAllUsers());
		secondService.printUsers();
	}

	public ArrayList<Croakos> getListeCroakos() {
		return liste_croakos;
	}

	public void setListeCroakos(ArrayList<Croakos> liste_croakos) {
		this.liste_croakos = liste_croakos;
	}
	
	public void printUsers(){
		for(Croakos i : this.liste_croakos){
			System.out.println(i.getNom());
		}
	}
}
