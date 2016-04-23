package Algorithmes;

import java.io.IOException;

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
	private double meilleurCout;
	private Solution meilleurSolutionConnue;
	private double nbIterationsSansChangementCout;
	private double epsilon;
	private int nbVoisins;
	
	/**
	 * 
	 * 	
		
	
		Répéter
		Tirer au sort une solution s’ dans V(s)
		Calculer la variation de coût Δf
		Si Δf < 0
		Alors Accept := Vrai
		Sinon
		Tirer au sort p dans [0,1]
		Accept := p ≤ exp(-Δf/T)
		FS
		Si Accept alors
		s := s’
		Si f(s) < z* alors
		z* := f(s)
		s* := s
		FS
		Si Δf = 0 alors NGel := NGel + 1 sinon NGel := 0 FS
		FS
		T := k.T
		
	 */
	
	public RecuitSimule(int nbIterations,double temperature,double mu,double epsilon,int nbVoisins) {
		
		this.nbIterations = nbIterations;
		this.temperature=temperature;
		this.mu = mu;
		s= new Solution();
		this.delta=0.;
		this.epsilon=epsilon;
		this.nbVoisins = nbVoisins;
		
	}
	
	public void run() throws IOException {
		
		//Construire aléatoirement une solution initiale s
		s = Utilitaires.genererSolutionAleatoire(null);
		
		//Solution pour contuire voisinage
		Solution v = Utilitaires.genererSolutionAleatoire(null);
		
		meilleurCout = 0.;
		meilleurSolutionConnue=s;
		nbIterationsSansChangementCout=0;
		int n =0;
		
		do {
			n++;
		}
		
		while(temperature <= epsilon || n==nbIterations 
				|| nbIterationsSansChangementCout==NB_ITER_SANS_CHANGEMENT);
	}

}
