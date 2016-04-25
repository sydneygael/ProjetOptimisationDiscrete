package Algorithmes;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import Utils.Utilitaires;

public class RecuitSimule {
	// les constantes
	public static final int NB_ITER_SANS_CHANGEMENT=200;
	public static final int NB_ITER_MAX=10000;
	//les variables
	private int nbIterations;
	private double temperature;
	private double mu;
	private double delta;
	private boolean accepter;
	private Solution s ;
	private Solution voisinAleatoire;
	private double meilleurCout;
	private Solution meilleurSolutionConnue;
	private double nbIterationsSansAmelioration;
	private double epsilon;
	private int nbVoisins;
	private Random r;
	private List<Solution> solutionsVoisines ;
	
	
	public RecuitSimule(int nbIterations,double temperature,double mu,double epsilon,int nbVoisins) {
		
		this.nbIterations = nbIterations;
		this.temperature=temperature;
		this.mu = mu;
		s= new Solution();
		this.delta=0.;
		this.epsilon=epsilon;
		this.nbVoisins = nbVoisins;
		this.r = new Random();
	}
	
	public void run() throws IOException {
		
		//Construire aléatoirement une solution initiale s
		s = Utilitaires.genererSolutionAleatoire();
		
		meilleurCout = s.getFitness();
		meilleurSolutionConnue=s;
		nbIterationsSansAmelioration=0;
		int n =0;
		
		do {
			n++;
			//Tirer au sort une solution s’ dans V(s)
			solutionsVoisines = s.genererVoisinage(nbVoisins) ;
			voisinAleatoire= solutionsVoisines.get(r.nextInt(nbVoisins));
			voisinAleatoire.calculerFitness();
			
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
		
		while(temperature <= epsilon || n< nbIterations 
				|| nbIterationsSansAmelioration<NB_ITER_SANS_CHANGEMENT);
	}

}
