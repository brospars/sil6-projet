sil6-projet
===========

Projet de SIL 6 Twitter-like Java RMI

#Description Fonctionnel

Service permettant de s’inscrire, de s’abonner à des utilisateurs et d’envoyer des tweets (de la même manière que le service Tweeter).

###Premier tiers (client)
    - Le client peut s’inscrire sur le service
    - Le client s’identifie et se connecte au service
    - Le client peut s’abonner à d’autres clients
    - Le client reçoit les messages de tous ses abonnements
###Second tiers (controller)
    - Le service authentifie le clients
    - Le service permet à un nouveau client de s’inscrire et le notifie au 3ème tiers
    - Le service reçoit un tweet d’un client et le distribue aux différents abonnés du client
    - A la déconnexion du client, transmet ses tweets de session au 3ème tiers
###Troisième tiers (modèle)
	Fonction du tiers : Persistance des données (CRUD)
    Tout les objets seront sérialisé et reconnaissable par un identifiant unique.
    - Le serveur enregistre les utilisateurs
    - Le serveur enregistre les tweets
    - Le serveur fourni les utilisateurs ainsi que les tweets au service
    
#Utilisation
    1. Installer Netbeans (avec tous les plugins inclus)
    2. Créer un nouveau projet Maven > Web Application
    3. Importer l’ensemble de l’archive jointe dans le dossier du projet précédemment créé
    4. Lancer le fichier ThirdServiceImpl (clic-droit sur le fichier > Run File)
    5. Lancer le projet (clic-droit sur le projet > Run)
    6. Lancer le client (clic-droit sur le fichier > Run)













