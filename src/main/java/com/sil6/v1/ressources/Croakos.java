/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sil6.v1.ressources;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement
public class Croakos implements Serializable{

	private static final long serialVersionUID = 8040518096487622839L;
	
	private String nom;
	private String mdp;
	private ArrayList<String> followers = new ArrayList<String>();
	private ArrayList<String> following = new ArrayList<String>();

        public Croakos() {
        }
	
	public Croakos(String nom, String mdp) {
		super();
		this.nom = nom;
		this.mdp = mdp;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

        public ArrayList<String> getFollowers() {
            return followers;
        }

        public void setFollowers(ArrayList<String> followers) {
            this.followers = followers;
        }

        public ArrayList<String> getFollowing() {
            return following;
        }

        public void setFollowing(ArrayList<String> following) {
            this.following = following;
        }
            
	
	@Override
	public String toString() {
		return "Croakos [nom=" + nom + ", mdp=" + mdp + "]";
	}
}

