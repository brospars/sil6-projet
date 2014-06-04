/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sil6.model;

import com.sil6.v1.ressources.Croak;
import com.sil6.v1.ressources.Croakos;
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

