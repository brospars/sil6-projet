/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sil6.client;


import com.sil6.v1.ressources.Croak;
import com.sil6.v1.ressources.CroakList;
import com.sil6.v1.ressources.Croakos;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.net.URI;
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
                        user = connexion(pseudo,mdp);
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
                    int choix = sc.nextInt();
                    switch(choix){
                        case 1:
                            try { //Refresh la timeline
                                CroakList timeline = getAllCroaks(user.getNom());
                                System.out.println("\n\n-----------------------------------");
                                System.out.println("---- Timeline : -------------------");
                                for(int i=0;i<timeline.croakList.size();i++){
                                    System.out.println(
                                            timeline.croakList.get(i).getDate()+" "
                                            +timeline.croakList.get(i).getAuteur()+" : "
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
                        case 4: // Exit
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
                    croak.setMessage(sc.nextLine());
                    
                    if(postCroak(croak)){
                        System.out.println("\n Votre Croak à bien été enregistré");
                        etat = EtatClient.MENU;
                    }else{
                        System.out.println("\n une erreur est survenue");
                        etat = EtatClient.POST_CROAK;
                    }
                    break;
                    
                case ABONNEMENT :
                    //La majeure partie est faite dans la fonction, elle s'occupe de tout
                    if(abonnement(user.getNom())){  //
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
                    
                default : // Sortie
                    System.out.println("\nfin -- Appuyer sur une touche pour terminer");
                    System.in.read();
                    fin = true;
                    break;
            }
        }while(!fin);
    }
    
    public static void afficherHomeMenu(){
        System.out.println("-----------------------------------");
        System.out.println("---- Home Menu --------------------");
        System.out.println("-----------------------------------");
        System.out.println("---- 1. Voir Timeline -------------");
        System.out.println("---- 2. Poster Croak (tweet) ------");
        System.out.println("---- 3. S'abonner à un Croakos ----");        
        System.out.println("---- 4. Exit ----------------------");
        System.out.println("-----------------------------------");
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
    private static boolean abonnement(String userName) throws Exception {
        List<Croakos> listCroakos = getAllCroakos();
        int listSize = listCroakos.size();
        String nameFollowing;
        Scanner sc2 = new Scanner(System.in);
        
        System.out.println("-----------------------------------");
        System.out.println("---- Liste des Croakos ------------");
        System.out.println("-----------------------------------");
        System.out.println("---- 0. Retour --------------------"); //Pas implémenté
        
        for (int i =0; i<listSize;i++){
            String name = listCroakos.get(i).getNom();
            System.out.println("----" + i+1 + ". " + name);
        }
        System.out.println("-----------------------------------");
        System.out.println("(Veuillez entrer le numero du Croakos auquel vous souhaitez vous abonner)");
        
        int choice = sc2.nextInt();
        
        if(choice<listSize){
            nameFollowing = listCroakos.get(choice).getNom();
             if(service.path("abonnement/" + userName + "/" + nameFollowing).get(boolean.class)){  // Appel au 2eme Tiers
                 System.out.println("Enregistrement réussi, vous suivez désormais" + nameFollowing);
             }
             return true;
        }else{
            System.out.println("Votre entrée est incorrecte, Veuillez recommencer");
            return false;
        }
    }
    
    //poste un Croak -> retourne vrai si réussi
    private static boolean postCroak(Croak croak) throws Exception {

        return service.path("postCroak/" + croak).get(boolean.class);
    }
    //Récupère la liste des Croakos
    private static List<Croakos> getAllCroakos() throws Exception {

        return service.path("users/").get(List.class);
    }
    
    //Récupère la liste des croaks de la timeline de l'utilisateur
    private static CroakList getAllCroaks(String userCourant) throws Exception {

        return service.path("getCroaks/"+userCourant).get(CroakList.class);
    }
    
    /* Type enuméré de l'etat du client */
    public enum EtatClient {
	INSCRIPTION,CONNEXION,MENU,POST_CROAK,ABONNEMENT, SORTIR;
    }
    
    private static URI getBaseURI() {
      return UriBuilder.fromUri("http://localhost:8080/Croaker").build();
    }
}
