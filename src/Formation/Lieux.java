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

	protected static final double EARTH_RADIUS = 6371.0;

	public static final int COUT_CENTRE = 2000;
	public static final int COUT_RENTABILITE = 1000;
	public static final int COUT_TOTAL = COUT_CENTRE + COUT_RENTABILITE;
	public static final double COUT_EMP = 0.4;

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

		double dLat = Math.toRadians(lat2-lat1);
		double dLng = Math.toRadians(long2-long1);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
				Math.sin(dLng/2) * Math.sin(dLng/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double dist = EARTH_RADIUS * c;


		System.out.println("La distance entre " + nom+ " et " + lieu2.nom + " est de "+ dist + " km");
		return dist;
	}

}


