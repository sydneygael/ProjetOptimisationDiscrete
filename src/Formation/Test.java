/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Corentin
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CentreFormation centre1=new CentreFormation("peronnas","peronnas","01960",5.2,46.1833);
        CentreFormation centre2=new CentreFormation("Oyonnax","Oyonnax","01100",5.66667,46.25);

        System.out.println(centre1.distance(centre2));;
    
        Carte carte1=new Carte();
        
        try {
            carte1.getAgencesFromFile("Ressources/ListeAgences_100.txt");
            
            carte1.getCentreFormationFromFile("Ressources/LieuxPossibles.txt");
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
