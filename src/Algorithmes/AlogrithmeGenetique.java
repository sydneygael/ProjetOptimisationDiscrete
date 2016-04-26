package Algorithmes;

import java.io.IOException;

import Utils.Utilitaires;

public class AlogrithmeGenetique extends Metaheuristique {
	private Population p;
	
	public AlogrithmeGenetique(int taille) throws IOException {
		p = new Population(taille);
	}
	
	public void run ( ) throws IOException {
		
		//meilleurSolutionConnue = Utilitaires.genererSolutionAleatoire();
		p.reproduire(p.getTaille());
        p.croiser();
        p.mutation(p.getTaille());
      //  meilleurSolutionConnue = p.getMeilleurSolution();
        System.out.println(meilleurSolutionConnue.getFitness());
	}
	

}
