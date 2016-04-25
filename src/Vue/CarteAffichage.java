/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Formation.Agence;
import Formation.CentreFormation;
import Utils.Utilitaires;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Corentin
 */
public class CarteAffichage  extends javax.swing.JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ImageIcon image;
    	private Map<CentreFormation, List<Agence>> disposition;
    	
    	public void initCarte(Map<CentreFormation, List<Agence>> listeLieux){
            disposition=listeLieux;
            image=new ImageIcon(getClass().getResource("/CarteFrance.PNG"));

            JFrame frame = new JFrame("Carte des Agences et Lieux de Formation");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.add(this);  
                    frame.pack();
                    frame.setBounds(0, 0, 673, 734); //Taille de l'image
                    frame.setVisible(true);
        }

    
    public void setDisposition(Map<CentreFormation, List<Agence>> listeLieux){
        disposition=listeLieux;
    }
    
    
    public Point ajouterLieu(double latitude, double longitude){
        //Coordonnées des latitudes/longitudes max et min de la carte
        double minLat = 41.368564;  //Plus au Sud sur la carte
        double minLong = -5.2294;
        double maxLat = 51.720223;
        double maxLong = 9.184570;

        // Taille de l'image (en pixels)
        Double mapHeight = 688.0;
        Double mapWidth = 657.0;

        // Position du point à représenter en pixels
        double x,y;        
        x = mapWidth * ((minLong-longitude)/(minLong-maxLong));
        y = (mapHeight * ((maxLat-latitude)/(maxLat-minLat)));
        
        Point p=new Point(); 
        p.setLocation(x,y);
        
        return p;
    }
    
    
    @Override
    public void paintComponent(Graphics g){
    	
        super.paintComponent(g);        
        g.drawImage(image.getImage(), 0, 0,  this);
        
        List<Color> colors=new ArrayList<Color>();
        colors.add(Color.red);
        colors.add(Color.BLUE);
        colors.add(Color.BLACK);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        
        
        
        Point p, pCentre;
        
            
            for (Map.Entry<CentreFormation, List<Agence>> entry : disposition.entrySet()) {       
                g.setColor(colors.get((int) (Math.random()*colors.size())));  //Choix d'une couleur aléatoire*/
                CentreFormation centre = entry.getKey();
                pCentre=ajouterLieu(centre.getLatitude(),centre.getLongitude());
                for(Agence agence : entry.getValue()){ 
                    
                    
                    p=ajouterLieu(agence.getLatitude(),agence.getLongitude());
                    System.out.println("Ville "+agence.getNom()+" de longitude "+agence.getLatitude()+" et de latitude "+agence.getLongitude());
                    
                    //g.setColor(Color.BLUE);
                    g.drawRect(pCentre.x, pCentre.y, 10, 10);
                    g.drawOval(p.x, p.y, 5,5);
                    g.drawLine(p.x, p.y, pCentre.x, pCentre.y);
                }
        }
            g.dispose();
    }
    
    
    
        public static void main(String args[]) {
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                JFrame frame = new JFrame("Carte des Agences et Lieux de Formation");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                final CarteAffichage carte=new CarteAffichage();
                frame.add(carte);
                carte.setDisposition(Utilitaires.genererSolutionAleatoire().getDisposition());
                
                frame.pack();
                frame.setBounds(0, 0, 673, 734);    //Taille de l'image
                frame.setVisible(true);

                }catch(Exception e){}
            }
        });
    }
}


