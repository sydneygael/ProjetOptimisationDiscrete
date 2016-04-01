/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formation;

import java.util.ArrayList;

/**
 *
 * @author Corentin
 */
public class Agence extends Lieux{
    
    private int nbEmploye;
    
    /**
     * construit une agence
     * @param id
     * @param nom
     * @param codepostal
     * @param latitude
     * @param longitude
     * @param nbEmploye
     */
    public Agence(String id,String nom, String codepostal, double latitude, double longitude, int nbEmploye){
        super(id,nom,codepostal,latitude,longitude);
        this.nbEmploye=nbEmploye;
    }
    
    public int getNbEmploye() {
        return nbEmploye;
    }
    
    
}
