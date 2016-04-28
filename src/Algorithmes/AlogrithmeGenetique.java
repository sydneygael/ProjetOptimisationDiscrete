package Algorithmes;

import java.io.IOException;
import java.util.Collections;

import Utils.Utilitaires;

public class AlogrithmeGenetique extends Metaheuristique {
	private Population p;

	public AlogrithmeGenetique(int taille,int nbIterations) throws IOException {
		p = new Population(taille);
		this.nbIterations=nbIterations;
		meilleurCout = Double.MAX_VALUE;
	}

	public void run () throws IOException {

		int generation = 0;

		while (generation<nbIterations) {
			
			generation++;
			p.reproduire(p.getTaille());
			p.croiser();
			p.mutation(10);

			Solution min = Collections.min(p.getSolutions());
			
			if(min.getFitness() < meilleurCout) {
				meilleurSolutionConnue = p.meilleurSolution=min;
				meilleurCout=meilleurSolutionConnue.getFitness();
			}
			System.out.println("generation : "+generation+ " meilleurCout de generation: " + min.getFitness());
		}
		System.out.println();
		System.out.println();
		System.out.println("meilleur Cout Algo génétique :" + meilleurCout);
		
		Utilitaires.afiicherSolution(meilleurSolutionConnue);
	}


}
