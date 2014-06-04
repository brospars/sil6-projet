/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sil6.v1.ressources;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author boris
 */

@XmlRootElement(name="AllCroakos")
public class MultiCroakos {
   @XmlElement
   public List<Croakos> liste = new ArrayList<Croakos>();

    public MultiCroakos() {
       
    }
   
   
}
