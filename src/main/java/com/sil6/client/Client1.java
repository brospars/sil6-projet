/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sil6.client;


import com.sil6.v1.ressources.Croak;
import com.sil6.v1.ressources.CroakList;
import com.sil6.v1.ressources.Croakos;
import com.sil6.v1.ressources.MultiCroakos;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author ben
 */
public class Client1 {
        
    private static WebResource service = null;
    
    public static void main(String[] args) throws Exception {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        service = client.resource(getBaseURI());
        
        /* Boucle d'excution du client */
        System.out.println("===================================");
        System.out.println("====== Bienvenue sur Croakos ======");
        System.out.println("= A command line Twitter-like app =");
        System.out.println("===================================");
        
        Boolean fin = false;
        Croakos user = null;
        EtatClient etat = EtatClient.CONNEXION ;
        
        do{
            Scanner sc = new Scanner(System.in);
            
            switch(etat){
                case INSCRIPTION:
                    System.out.println("-----------------------------------");
                    System.out.println("---- Inscription ------------------");
                    System.out.println("\nPseudo ?\n");
                    String new_pseudo = sc.nextLine();
                    System.out.println("\nMot de passe ?\n");
                    String new_mdp = sc.nextLine();
                    if(inscription(new Croakos(new_pseudo,new_mdp)).equals("true")){
                        System.out.println("\nInscription réussi ! Bienvenue \n");
                        etat = EtatClient.CONNEXION;
                    }else{System.out.println("\nInscription échouée .. \n");}
                    
                    break;
                case CONNEXION : // L'utilisateur vient d'arriver et doit se connecter
                    System.out.println("\nConnexion :\nRentrer votre pseudo utilisateur :");
                    String pseudo = sc.nextLine();
                    System.out.println("\nMot de passe de "+pseudo+" :");
                    String mdp = sc.nextLine();
                    
                    try { //Connexion
                        if(pseudo != null && mdp != null ){
                            user = connexion(pseudo,mdp);
                        }
                    } catch (Exception ex) {Logger.getLogger(Client1.class.getName()).log(Level.SEVERE, null, ex);}
                    
                    
                    if(user.getNom() != null){
                        System.out.println("\nConnexion réussi ! bienvenue "+user.getNom()+"\n");
                        etat = EtatClient.MENU;
                    }else{
                        System.out.println("\nConnexion échoué ..\n");
                        System.out.println("\nInscription ? (Oui:1/Non:2)\n");
                        int choix = sc.nextInt();
                        if(choix==1){etat = EtatClient.INSCRIPTION;}
                    }
                    
                    break;
                case MENU :
                    afficherHomeMenu();
                    while (!sc.hasNextInt()) { // Tant que l'entree n'est pas un int
                        System.out.println("Vueillez entrer un chiffre svp !");
                        sc.nextLine();
                    }
                    int choix = sc.nextInt();
                    switch(choix){
                        case 1:
                            try { //Refresh la timeline
                                CroakList timeline = getAllCroaks(user.getNom());
                                System.out.println("\n\n-----------------------------------");
                                System.out.println("---- Timeline : -------------------");
                                for(int i=0;i<timeline.croakList.size();i++){
                                    System.out.println(
                                            timeline.croakList.get(i).getDate()+"  || Auteur : "
                                            +timeline.croakList.get(i).getAuteur().getNom()+" *--> "
                                            +timeline.croakList.get(i).getMessage()
                                    );
                                }
                                System.out.println("---- Fin Timeline -----------------\n\n");
                            } catch (Exception ex) {Logger.getLogger(Client1.class.getName()).log(Level.SEVERE, null, ex);}
                            break;
                        case 2: // Poste d'un tweet
                            etat = EtatClient.POST_CROAK;
                            break;
                        case 3: // Abonnement à un utilisateur
                            etat = EtatClient.ABONNEMENT;
                            break;
                        case 4: // Consulter liste d'abonnement
                            etat = EtatClient.ABONNE;
                            break;
                        case 5: // Exit
                            etat = EtatClient.SORTIR;
                            break;
                        default:
                            System.out.println("\nErreur : Choix inexistant\n");
                            break;                           
                    }
                    break;
                    
                case POST_CROAK :
                    Croak croak = new Croak();
                    if(user!= null){
                        croak.setAuteur(user);
                        croak.setDate(new Date());
                    }
                    else{
                        etat = EtatClient.CONNEXION;
                        break;
                    }
                    System.out.println("\nCroak :\nVeuillez rentrez votre Croak :");
                    String message = sc.nextLine();
                    while(message.length()>140 || message.length()<1){
                        System.out.println("\nMessage de taille incorrect ("+message.length()+" caractères)");
                        message = sc.nextLine();
                    }
                    croak.setMessage(sc.nextLine());
                    
                    if(postCroak(croak).equals("true")){
                        System.out.println("\n Votre Croak à bien été enregistré");
                        etat = EtatClient.MENU;
                    }else{
                        System.out.println("\n une erreur est survenue");
                        etat = EtatClient.POST_CROAK;
                    }
                    break;
                    
                case ABONNEMENT :
                    //Fonction d'abonnement
                    if(user != null){
                        System.out.println(user.getNom());
                    } else {
                        System.out.println("YAUNPÉPIN");
                    }
                    if(choixAbonnement(user)){  //
                        etat = EtatClient.MENU;
                        
                    }else{
                        etat = EtatClient.ABONNEMENT;
                    }
                    
                    break;
                    
                case ABONNE :
                    if(user != null){
                        System.out.println(user.getNom());
                    }
                    if(removeAbonnement(user)){  //
                        etat = EtatClient.MENU;
                        
                    }else{
                        etat = EtatClient.ABONNEMENT;
                    }
                    
                    break;
                    
                case SORTIR : // Sortie
                    System.out.println("\nfin -- Appuyer sur une touche pour terminer");
                    System.in.read();
                    fin = true;
                    break;
                    
                default : // Restart menu
                    break;
            }
        }while(!fin);
    }
    
    public static void afficherHomeMenu(){
        
        System.out.println("------------------------------------");
        System.out.println("---- Home Menu ---------------------");
        System.out.println("------------------------------------");
        System.out.println("---- 1. Voir Timeline --------------");
        System.out.println("---- 2. Poster Croak (tweet) -------");
        System.out.println("---- 3. S'abonner à un Croakos -----");
        System.out.println("---- 4. Se desabonner à un Croakos -");        
        System.out.println("---- 5. Exit -----------------------");
        System.out.println("------------------------------------");

        System.out.println("(Veuillez entrer le numero de l'action à effectuer)");
    }
    
    //Tente une connexion, retourne null si échoué
    private static Croakos connexion(String pseudo, String mdp) throws Exception {

        return service.path("connexion/" + pseudo + "/" + mdp).get(Croakos.class);
    }
    
    //Tente une inscription
    private static String inscription(Croakos croakos) throws Exception {

        return service.path("inscription/").put(String.class,croakos);
    }
    
    //Propose une liste des Utilisateurs, et la possibilité d'abonnement 
    //Retourne vrai si réussi
    private static boolean choixAbonnement(Croakos follower) throws Exception {
        follower = service.path("getUser/" + follower.getNom()).get(Croakos.class); // On met à jour l'user actuel
        MultiCroakos allCroakos = getAllCroakos();
        List<Croakos> listCroakos = allCroakos.liste;
        
        int listSize = listCroakos.size();
        String nameFollowing;
        Scanner sc2 = new Scanner(System.in);
        
        System.out.println("-----------------------------------");
        System.out.println("---- Liste des Croakos ------------");
        System.out.println("-----------------------------------");
        System.out.println("---- 0. Retour --------------------"); //Pas implémenté
        System.out.println("-----------------------------------");
        
        for (int i =0; i<listSize;i++){
            String name = listCroakos.get(i).getNom();
            if(!name.equals(follower.getNom())&& !follower.getFollowing().contains(name)){
                System.out.println("---- " + (i+1) + ". " + name);
            }
        }
        System.out.println("-----------------------------------");
        System.out.println("(Veuillez entrer le numero du Croakos auquel vous souhaitez vous abonner)");
        
        while (!sc2.hasNextInt()) { // Tant que le 
            System.out.println("Vueillez entrer un chiffre svp !");
            sc2.nextLine();
        }
        int choice = sc2.nextInt();
        
        if(choice == 0){return true;}// On sort si choix 0
        if(choice>0 && choice<=listSize){   // s'il fait le bon choix on l'abonne
            
            Croakos following = listCroakos.get(choice-1);
            nameFollowing = following.getNom();
            
            //On défini l'objet qui comporte le follower et le followé
            MultiCroakos aboList = new MultiCroakos();
           
            aboList.liste.add(follower);
            aboList.liste.add(following);
            
             if(abonnement(aboList).equals("true")){  // Appel au 2eme Tiers
                 System.out.println("Enregistrement réussi, vous suivez désormais " + nameFollowing);
             }
             return true;
        }else{
            System.out.println("Votre entrée est incorrecte, Veuillez recommencer");
            return false;
        }
    }
    
    //Propose la liste des abonnement, et la possibilité se desabonner 
    //Retourne vrai si réussi
    private static boolean removeAbonnement(Croakos croakos) throws Exception {
         // On met à jour l'user actuel
        croakos = service.path("getUser/" + croakos.getNom()).get(Croakos.class);
        ArrayList<String> abonnement = croakos.getFollowing();
        
        Scanner sc2 = new Scanner(System.in);
        
        System.out.println("-----------------------------------");
        System.out.println("---- Liste des vos abonnements ----");
        System.out.println("-----------------------------------");
        System.out.println("---- 0. Retour --------------------"); //Pas implémenté
        System.out.println("-----------------------------------");
        
        for (int i =0; i<abonnement.size();i++){
            if(!abonnement.get(i).equals(croakos)){
                System.out.println("---- " + (i+1) + ". " + abonnement.get(i));
            }
        }
        System.out.println("-----------------------------------");
        System.out.println("(Veuillez entrer le numero du Croakos auquel vous souhaitez vous abonner)");
        
        while (!sc2.hasNextInt()) { // Tant que le 
            System.out.println("Vueillez entrer un chiffre svp !");
            sc2.nextLine();
        }
        int choice = sc2.nextInt();
        if(choice == 0){return true;}// On sort si choix 0
        if(choice>0 && choice<=abonnement.size()){   // s'il fait le bon choix on l'abonne
            
            String nameToUnfollow = abonnement.get(choice-1);
            
            //On défini l'objet qui comporte le follower et le followé
            MultiCroakos aboList = new MultiCroakos();
           
            aboList.liste.add(croakos);
            aboList.liste.add(service.path("getUser/" + nameToUnfollow).get(Croakos.class));
            
             if(desabonnement(aboList).equals("true")){  // Appel au 2eme Tiers
                 System.out.println("Enregistrement réussi, vous ne suivez plus " + abonnement.get(choice-1));
             }
             return true;
        }else{
            System.out.println("Votre entrée est incorrecte, Veuillez recommencer");
            return false;
        }
    }
    
    //Abonnement
    private static String abonnement(MultiCroakos aboList){
       
        return service.path("abonnement").put(String.class,aboList);
    }
    
    //Desabonnement
    private static String desabonnement(MultiCroakos aboList){
       
        return service.path("desabonnement/").put(String.class,aboList);
    }
    
    //poste un Croak -> retourne vrai si réussi
    private static String postCroak(Croak croak) throws Exception {
        return service.path("postCroak").put(String.class, croak);
    }
    
    //Récupère la liste des Croakos
    private static MultiCroakos getAllCroakos() throws Exception {

        return service.path("users/").get(MultiCroakos.class);
    }
    
    //Récupère la liste des croaks de la timeline de l'utilisateur
    private static CroakList getAllCroaks(String userCourant) throws Exception {

        return service.path("getCroaks/"+userCourant).get(CroakList.class);
    }
    
    /* Type enuméré de l'etat du client */
    public enum EtatClient {
	INSCRIPTION,CONNEXION,MENU,POST_CROAK,ABONNEMENT, ABONNE, SORTIR;
    }
    
    private static URI getBaseURI() {
      return UriBuilder.fromUri("http://localhost:8080/Croaker").build();
    }
}
