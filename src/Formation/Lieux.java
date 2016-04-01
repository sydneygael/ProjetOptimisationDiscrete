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
    private String id;
    private String nom;
    private String codePostal;
    private double latitude;
    private double longitude;
    
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Agence)) {
            return false;
        }
        Agence agence2 = (Agence) obj;
        if (id == null) {
            if (agence2.getId() != null) {
                return false;
            }
        } else if (!id.equals(agence2.getId())) {
            return false;
        }
        if (latitude !=agence2.getLatitude()) {
            return false;
        }
        if (longitude!=agence2.getLongitude()) {
            return false;
        }
        if (nom == null) {
            if (agence2.getNom() != null) {
                return false;
            }
        } else if (!nom.equals(agence2.getNom())) {
            return false;
        }
        if (codePostal == null) {
            if (agence2.getCodePostal() != null) {
                return false;
            }
        } else if (!codePostal.equals(agence2.getCodePostal())) {
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

    
