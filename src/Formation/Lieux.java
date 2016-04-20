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
public abstract class Lieux {
    protected String id;
    protected String nom;
    protected String codePostal;
    protected double latitude;
    protected double longitude;
    
    public Lieux(String id,String nom, String codepostal, double latitude, double longitude){
        this.id=id;
        this.nom=nom;
        this.codePostal=codepostal;
        this.latitude=latitude;
        this.longitude=longitude;
    }
    
    
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }
    
    public String getCodePostal() {
        return codePostal;
    }

    public double getLongitude() {
        return longitude;
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    
    /**
	 * permettra de comparer si deux lieux sont les mêmes
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CentreFormation)) {
			return false;
		}

		CentreFormation autreCentre = (CentreFormation) obj;
		if (id == null) {
			if (autreCentre.id != null) {
				return false;
			}

		} else if (!id.equals(autreCentre.id)) {
			return false;
		}
		if (Double.compare(latitude, autreCentre.latitude) != 0) {
			return false;
		}
		if (Double.compare(longitude, autreCentre.longitude) != 0) {
			return false;
		}

		if (nom == null) {
			if (autreCentre.nom != null) {
				return false;
			}
		} else if (!nom.equals(autreCentre.nom)) {
			return false;
		}
		if (codePostal == null) {
			if (autreCentre.codePostal != null) {
				return false;
			}
		} else if (!codePostal.equals(autreCentre.codePostal)) {
			return false;
		}
		return true;
	}

    public double distance(Lieux lieu2){
        double lat1=this.latitude;
        double lat2=lieu2.latitude;
        double long1=this.longitude;
        double long2=lieu2.longitude;
                
        double theta = long1 - long2;
        double distance = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) 
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        distance = Math.acos(distance);
        distance = rad2deg(distance);
        distance = distance * 60 * 1.1515;

        System.out.print("La distance entre les deux ville est de " + distance + " km");
        return distance;
    }
    
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
            
}

    
