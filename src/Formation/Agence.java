/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formation;

/**
 *
 * @author Corentin
 */
public class Agence extends Lieux implements Comparable<Agence> {
    
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
    public Agence(String id,String nom, String codepostal, double longitude, double latitude, int nbEmploye){
        super(id,nom,codepostal,longitude,latitude);
        this.nbEmploye=nbEmploye;
    }
    
    public int getNbEmploye() {
        return nbEmploye;
    }

	@Override
	public int compareTo(Agence o) {
		return this.id.compareTo(o.getId());
	}
    
    
}
