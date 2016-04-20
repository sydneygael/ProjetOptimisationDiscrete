/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Utils.Utilitaires;

/**
 *
 * @author Corentin
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            /*carte1.getAgencesFromFile("Ressources/ListeAgences_100.txt");
            
            carte1.getCentreFormationFromFile("Ressources/LieuxPossibles.txt");*/
        	Utilitaires.premiereSolution();
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
