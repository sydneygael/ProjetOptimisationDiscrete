package Algorithmes;


import java.util.List;
import java.util.Random;

public class Metaheuristique {
	protected int nbIterations;
	protected double temperature;
	protected double mu;
	protected double delta;
	protected boolean accepter;
	protected SolutionSimple s ;
	protected SolutionSimple voisinAleatoire;
	protected double meilleurCout;
	protected SolutionSimple meilleurSolutionConnue;
	protected double nbIterationsSansAmelioration;
	protected double epsilon;
	protected int nbVoisins;
	protected Random r;
	protected List<SolutionSimple> solutionsVoisines ;

}
