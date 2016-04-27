package Algorithmes;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import Formation.CentreFormation;
import Utils.Utilitaires;

public class RecuitSimule extends Metaheuristique {


	// les constantes
	public static final int NB_ITER_SANS_CHANGEMENT=200;
	public static final int NB_ITER_MAX=10000;
	//les variables



	public RecuitSimule(int nbIterations,double temperature,double mu,double epsilon,int nbVoisins) {

		this.nbIterations = nbIterations;
		this.temperature=temperature;
		this.mu = mu;
		s= new SolutionSimple();
		this.delta=0.;
		this.epsilon=epsilon;
		this.nbVoisins = nbVoisins;
		this.r = new Random();
	}

	public void run() throws IOException {

		//Construire aléatoirement une solution initiale s
		s = Utilitaires.genererUneSolutionSimple();
		
		System.out.println("debut première solution: " + s.getFitness());
		System.out.println(" nb centres : " + s.centresDansLaSolution.size());
		meilleurCout = s.getFitness();
		meilleurSolutionConnue=s;
		nbIterationsSansAmelioration=0;
		int n =0;

		do {
			n++;
			//Tirer au sort une solution s’ dans V(s)
			solutionsVoisines = s.genererVoisinageS(nbVoisins);
			voisinAleatoire= solutionsVoisines.get(r.nextInt(nbVoisins));
			
			System.out.println();
			System.out.println("------------------voisin aleatoire--------------------");
			System.out.println("fitness v(s) = " + voisinAleatoire.getFitness());
			System.out.println("------------------voisin aleatoire--------------------");
			System.out.println();
			//Calculer la variation de coût Δf
			delta = voisinAleatoire.getFitness() - s.getFitness();

			if ( delta <0. ) {
				accepter=true;
			}

			else {
				double p = r.nextDouble(); // tirage aléatoire d'une probabilité
				accepter = p <= Math.exp(- delta / this.temperature);
			}

			if (accepter) {
				s = voisinAleatoire;

				if (s.getFitness() < meilleurCout) {
					meilleurCout = s.getFitness();
					meilleurSolutionConnue = s;
				}
				//Si Δf = 0 alors NGel := NGel + 1 sinon NGel := 0 FS
				if ( delta ==0) nbIterationsSansAmelioration++;
				else nbIterationsSansAmelioration=0;
			}

			temperature=mu*temperature;
		}
		while (n<100);
		/*while(temperature <= epsilon && n < nbIterations 
				&& nbIterationsSansAmelioration<NB_ITER_SANS_CHANGEMENT);*/

	}

	public int getNbIterations() {
		return nbIterations;
	}

	public void setNbIterations(int nbIterations) {
		this.nbIterations = nbIterations;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getMu() {
		return mu;
	}

	public void setMu(double mu) {
		this.mu = mu;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public Solution getVoisinAleatoire() {
		return voisinAleatoire;
	}

	public void setVoisinAleatoire(SolutionSimple voisinAleatoire) {
		this.voisinAleatoire = voisinAleatoire;
	}

	public double getMeilleurCout() {
		return meilleurCout;
	}

	public void setMeilleurCout(double meilleurCout) {
		this.meilleurCout = meilleurCout;
	}

	public SolutionSimple getMeilleurSolutionConnue() {
		return meilleurSolutionConnue;
	}

	public void setMeilleurSolutionConnue(SolutionSimple meilleurSolutionConnue) {
		this.meilleurSolutionConnue = meilleurSolutionConnue;
	}

	public double getNbIterationsSansAmelioration() {
		return nbIterationsSansAmelioration;
	}

	public void setNbIterationsSansAmelioration(double nbIterationsSansAmelioration) {
		this.nbIterationsSansAmelioration = nbIterationsSansAmelioration;
	}

	public double min(double val1 , double val2) {
		if(val1 < val2) return val1;
		else return val2;

	}

}
