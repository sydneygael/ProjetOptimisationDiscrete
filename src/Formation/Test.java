/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formation;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Algorithmes.Solution;
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
        	Solution s = Utilitaires.genererSolutionAleatoire(null);
        	List<Solution > l = s.genererVoisinage(4);
        	
        	for ( Solution sol : l ){
        		
        		System.out.println("fitness voisin : " + sol.getFitness());
        	}
        	
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
