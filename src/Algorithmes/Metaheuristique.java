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
	public double getDelta() {
		return delta;
	}
	public void setDelta(double delta) {
		this.delta = delta;
	}
	public Solution getVoisinAleatoire() {
		return voisinAleatoire;
	}
	public void setVoisinAleatoire(Solution voisinAleatoire) {
		this.voisinAleatoire = voisinAleatoire;
	}
	public double getMeilleurCout() {
		return meilleurCout;
	}
	public void setMeilleurCout(double meilleurCout) {
		this.meilleurCout = meilleurCout;
	}
	public Solution getMeilleurSolutionConnue() {
		return meilleurSolutionConnue;
	}
	public void setMeilleurSolutionConnue(Solution meilleurSolutionConnue) {
		this.meilleurSolutionConnue = meilleurSolutionConnue;
	}
	public double getNbIterationsSansAmelioration() {
		return nbIterationsSansAmelioration;
	}
	public void setNbIterationsSansAmelioration(double nbIterationsSansAmelioration) {
		this.nbIterationsSansAmelioration = nbIterationsSansAmelioration;
	}
	public double getEpsilon() {
		return epsilon;
	}
	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}
	public List<Solution> getSolutionsVoisines() {
		return solutionsVoisines;
	}
	public void setSolutionsVoisines(List<Solution> solutionsVoisines) {
		this.solutionsVoisines = solutionsVoisines;
	}

}
