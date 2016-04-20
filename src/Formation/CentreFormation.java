/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formation;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Corentin
 */
public class CentreFormation extends Lieux{

	private List<Employe> listeEmployes;
	private int nbEmp;

	public CentreFormation(String id,String nom, String codepostal, double latitude, double longitude){
		super(id,nom,codepostal,latitude,longitude);
		listeEmployes=new ArrayList<Employe>();
	}


}
