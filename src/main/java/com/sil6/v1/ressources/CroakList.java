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
 * @author ben
 */
@XmlRootElement
public class CroakList {
    @XmlElement
    public List<Croak> croakList = new ArrayList<Croak>();

    public CroakList() {
    }

    public List<Croak> getCroakList() {
        return croakList;
    }

    public void setCroakList(ArrayList<Croak> croakList) {
        this.croakList = croakList;
    }
    
    
}
