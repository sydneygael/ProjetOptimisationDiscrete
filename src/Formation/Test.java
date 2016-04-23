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
        	System.out.println();
        	System.out.println("-----------------------------------------");
        	System.out.println("SOLUTION ALEATOIRE N° 1");
        	System.out.println("-----------------------------------------");
        	System.out.println();
        	Solution s = Utilitaires.genererSolutionAleatoire(null);
        	System.out.println();
        	System.out.println("-----------------------------------------");
        	System.out.println("SOLUTION ALEATOIRE N° 2");
        	System.out.println("-----------------------------------------");
        	System.out.println();
        	
        	List<Solution > l = s.genererVoisinage(2);
        	s.calculerFitness();
        	System.out.println(s.getFitness());
        	double test = s.getFitness()-l.get(0).getFitness();
        	System.out.println(test);
        	for ( Solution sol : l ){
        		
        		System.out.println("fitness voisin : " + sol.getFitness());
        	}
        	
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
