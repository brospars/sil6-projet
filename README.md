sil6-projet
===========

Projet de SIL 6 Twitter-like Java RMI

#Description Fonctionnel

Service permettant de s’inscrire, de s’abonner à des utilisateurs et d’envoyer des tweets (de la même manière que le service Tweeter).

##Premier tiers (client)
    - Le client peut s’inscrire sur le service
    - Le client s’identifie et se connecte au service
    - Le client peut s’abonner à d’autres clients
    - Le client reçoit les messages de tous ses abonnements
##Second tiers (controller)
    - Le service authentifie le clients
    - Le service permet à un nouveau client de s’inscrire et le notifie au 3ème tiers
    - Le service reçoit un tweet d’un client et le distribue aux différents abonnés du client
    - A la déconnexion du client, transmet ses tweets de session au 3ème tiers
##Troisième tiers (modèle)
Fonction du tiers : Persistance des données (CRUD)
    Tout les objets seront sérialisé et reconnaissable par un identifiant unique.
    - Le serveur enregistre les utilisateurs
    - Le serveur enregistre les tweets
    - Le serveur fourni les utilisateurs ainsi que les tweets au service
    
#Documentation
##Troisième Tiers (Gestion BDD)
```java
	public void saveUser(Croakos user) throws RemoteException ;
	public Croakos getUser(String name) throws RemoteException ;
	public ArrayList<Croakos> getAllUsers() throws RemoteException ;
	public void saveAllCroaks(ArrayList<Croak> listeCroak) throws RemoteException ;
	public ArrayList<Croak> getAllCroaks() throws RemoteException ;
```
##Troisième Tiers (Controller)
```java
	public SecondServiceImpl() ;
	public ArrayList<Croakos> getListeCroakos() ;
	public ArrayList<Croakos> getAllUsers() throws RemoteException ;
	public void setListeCroakos(ArrayList<Croakos> liste_croakos) ;
	public void printUsers() ;
```
