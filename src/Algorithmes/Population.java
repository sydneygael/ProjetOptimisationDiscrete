package Algorithmes;

import java.io.IOException;
import java.util.ArrayList;

/**
 * une population représente un ensemble de solution
 * @author Sydney
 *
 */

import java.util.Collections;
import java.util.List;
import java.util.Random;
import Formation.Carte;
import Formation.CentreFormation;
import Utils.Utilitaires;

/**
 * 
 * @author Sydney
 *
 */

public class Population {

	private List<Solution> populationDeSolutions;
	private Solution meilleurSolution;
	private int taille;
	private Random alea;
	private Carte carte ;

	public Population(int taille) throws IOException {
		this.taille = taille;
		alea = new Random();
		carte = new Carte();
		carte.getAgencesFromFile(Utilitaires.RESSOURCES_LISTE_AGENCES);
		carte.getCentreFormationFromFile(Utilitaires.RESSOURCES_LIEUX_POSSIBLES);

		populationDeSolutions = new ArrayList<Solution>();

		for (int i=0; i<taille; i++) {
			populationDeSolutions.add(Utilitaires.genererSolutionAleatoire());
		}

		calculerMeilleurSolution();
	}

	public Solution getMeilleurSolution() {
		return meilleurSolution;
	}

	public void reproduire(int k) {
		populationDeSolutions = new Roulette(this, k).getNouvelleGeneration();
	}

	public void croiser() {
		int n = populationDeSolutions.size();
		for (int i=0; i+2<=n; i += 2) {
			populationDeSolutions.get(i)
			.croisement(populationDeSolutions.get(i + 1));
		}
	}

	public void mutation(int taille) {

		for (int i=0; i<taille; i++) {
			int index = alea.nextInt(populationDeSolutions.size());
			CentreFormation c = carte.getListCentreFormation().remove(0);
			CentreFormation sortant = populationDeSolutions.get(index).mutation(c);
			carte.getListCentreFormation().add(sortant);
		}
	}

	private void calculerMeilleurSolution() {
		meilleurSolution = Collections.min(populationDeSolutions);
	}

	public List<Solution> getSolutions() {
		return populationDeSolutions;
	}

	public int getTaille() {
		return taille;
	}
}