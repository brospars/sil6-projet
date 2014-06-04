/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sil6.client;


import com.sil6.v1.ressources.Croakos;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.net.URI;
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
    
    public static void main(String[] args) {
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
                case CONNEXION : // L'utilisateur vient d'arriver et doit se connecter
                    System.out.println("\nConnexion :\nRentrer votre pseudo utilisateur :");
                    String pseudo = sc.nextLine();
                    System.out.println("\nmor de passe de "+pseudo+" :");
                    String mdp = sc.nextLine();
                    
                    try { //Connexion
                        user = connexion(pseudo,mdp);
                    } catch (Exception ex) {Logger.getLogger(Client1.class.getName()).log(Level.SEVERE, null, ex);}
                    
                    if(user != null){
                        System.out.println("\nConnexion réussi ! bienvenue "+user.getNom()+"\n");
                        etat = EtatClient.MENU;
                    }else{System.out.println("\nConnexion échoué ..\n");}
                    
                    break;
                case MENU :
                    afficherHomeMenu();
                    int choix = sc.nextInt();
                    switch(choix){
                        case 1:
                            try { //Refresh la timeline
                                //refreshTimeLine(user.getNom());
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
                    
                    break;
                    
                case ABONNEMENT :
                    
                    break;
                default : // Sortie
                    System.out.println("\nfin -- Appuyer sur une touche pour terminer");
                    sc.next();
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
    
    
    private static Croakos connexion(String pseudo, String mdp) throws Exception {
        
        return service.path("getUser/" + pseudo).get(Croakos.class);
    }
    
    /* Type enuméré de l'etat du client */
    public enum EtatClient {
	CONNEXION,MENU,POST_CROAK,ABONNEMENT, SORTIR;
    }
    
    private static URI getBaseURI() {
      return UriBuilder.fromUri("http://localhost:8080/Croaker").build();
    }
}
