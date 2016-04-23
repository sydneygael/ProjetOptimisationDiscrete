/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;

import Utils.AjoutException;

/**
 *
 * @author Corentin
 */
public class CentreFormation extends Lieux{

	private List<Agence> agencesAssosciees;
	private int nbEmp;

	public CentreFormation(String id,String nom, String codepostal, double latitude, double longitude){
		super(id,nom,codepostal,latitude,longitude);
		agencesAssosciees=new ArrayList<Agence>();
		nbEmp=0;
	}

	public void setNbEmp(int nbEmp) {
		this.nbEmp = nbEmp;
	}

	public void addNbEmployes (int nb) throws AjoutException {
		int test = nbEmp+nb;
		if (test <= 60) nbEmp+=nb;
		else throw new AjoutException();
	}

	public int getNbEmployes() {
		return nbEmp;
	}
	
	public List<Agence> getAgencesAssociees() {
        return agencesAssosciees;
    }
	
	public List<Agence> setAgencesAssociees(List<Agence> toSet) {
        return agencesAssosciees=toSet;
    }
}
