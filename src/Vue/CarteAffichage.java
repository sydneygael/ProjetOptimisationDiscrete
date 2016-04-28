/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Formation.Agence;
import Formation.CentreFormation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Corentin
 * Affiche l'image d'une carte de France et les lieux qui sont dessus (agences et centres de formation)
 */
public class CarteAffichage  extends javax.swing.JPanel{

	private static final long serialVersionUID = 1L;
	
	private ImageIcon image;
        protected Map<Agence,CentreFormation> disposition;
    	
        
    public void initCarte(Map<Agence,CentreFormation> listeLieux, JFrame frame){
        disposition=listeLieux;
        image=new ImageIcon("Ressources/CarteFrance.PNG");
    }
    
    public void setDisposition(Map<Agence,CentreFormation> listeLieux){
        disposition=listeLieux;
    }
    
    //Donne la position d'un point en pixels sur l'image (depuis les coordonnées GPS)
    public Point ajouterLieu(double latitude, double longitude){
        //Coordonnes des latitudes/longitudes max et min de la carte
        double minLat = 41.368564;  //Plus au Sud sur la carte
        double minLong = -5.2294;
        double maxLat = 51.720223;
        double maxLong = 9.184570;

        // Taille de l'image (en pixels)
        Double mapHeight = 688.0;
        Double mapWidth = 657.0;

        // Position du point Ã  reprÃ©senter en pixels
        double x,y;        
        x = mapWidth * ((minLong-longitude)/(minLong-maxLong));
        y = (mapHeight * ((maxLat-latitude)/(maxLat-minLat)));
        
        Point p=new Point(); 
        p.setLocation(x,y);
        
        return p;
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        g.drawImage(image.getImage(), 0, 0,  this);

        Point p, pCentre;
        Agence agence; CentreFormation centre;
            //On dessine un trait entre chaque agence et centre de formation
            for (Map.Entry<Agence,CentreFormation> entry : disposition.entrySet()) {
                agence = entry.getKey();
                centre = entry.getValue();
               //Chaque trait peut-être noir/bleu/rouge selon le centre de formation. C'est plus lisible qu'avec une couleur uniforme
                if(centre.getCodePostal().startsWith("0")||centre.getCodePostal().startsWith("1")||centre.getCodePostal().startsWith("2")){
                                    g.setColor(Color.red);  
                }
                else if(centre.getCodePostal().startsWith("4")||centre.getCodePostal().startsWith("5")||centre.getCodePostal().startsWith("6")){
                                    g.setColor(Color.blue);
                }
                  else{
                    g.setColor(Color.black);
                }
                pCentre=ajouterLieu(centre.getLatitude(),centre.getLongitude());    //Cette fonction donne la position d'un lieu en pixels
                p=ajouterLieu(agence.getLatitude(),agence.getLongitude());
                    
                g.drawRect(pCentre.x, pCentre.y, 10, 10);
                g.drawOval(p.x, p.y, 5,5);
                g.drawLine(p.x, p.y, pCentre.x, pCentre.y);
            }
    }
    }


