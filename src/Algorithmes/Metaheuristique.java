package Algorithmes;


import java.util.List;
import java.util.Random;

public class Metaheuristique {
	protected int nbIterations;
	protected double temperature;
	protected double mu;
	protected double delta;
	protected boolean accepter;
	protected Solution s ;
	protected Solution voisinAleatoire;
	protected double meilleurCout;
	protected Solution meilleurSolutionConnue;
	protected double nbIterationsSansAmelioration;
	protected double epsilon;
	protected int nbVoisins;
	protected Random r;
	protected List<Solution> solutionsVoisines ;

}
