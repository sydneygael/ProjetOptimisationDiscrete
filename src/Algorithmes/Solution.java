package Algorithmes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Map.Entry;

import Formation.Agence;
import Formation.Carte;
import Formation.CentreFormation;
import Formation.Lieux;
import Utils.Utilitaires;

public class Solution implements Comparable <Solution> {

	private Map <Agence,CentreFormation> disposition;
	private List<Agence> agencesDansLaSolution ;
	public List<CentreFormation> centresDansLaSolution;
	public List<CentreFormation> centresDansLaCarte;

	int nbCentres ;
	private Carte carte;
	private double fitness;

	public Solution (){
		agencesDansLaSolution = new ArrayList<Agence>();
		disposition = new LinkedHashMap <Agence,CentreFormation>();
		centresDansLaSolution = new ArrayList<CentreFormation>();
	}

	public Solution (Map <Agence,CentreFormation> dispositionSimple,
			List<Agence> agencesDansLaSolution,List<CentreFormation> centresDansLaSolution){
		this.agencesDansLaSolution = agencesDansLaSolution;
		this.disposition = dispositionSimple;
		this.centresDansLaSolution = centresDansLaSolution;
	}

	public Solution (Map <Agence,CentreFormation> dispositionSimple,
			List<Agence> agencesDansLaSolution,List<CentreFormation> centresDansLaSolution,
			Carte carte){
		this.agencesDansLaSolution = agencesDansLaSolution;
		this.disposition = dispositionSimple;
		this.centresDansLaSolution = centresDansLaSolution;
		this.carte = carte;
		centresDansLaCarte = carte.getListCentreFormation();
	}

	public Solution (Carte carte) {
		agencesDansLaSolution = new ArrayList<Agence>();
		disposition = new LinkedHashMap <Agence,CentreFormation>();
		centresDansLaSolution = new ArrayList<CentreFormation>();
		this.carte=carte;
		centresDansLaCarte = carte.getListCentreFormation();
	}

	public Solution construireVoisinAleatoire () {

		Solution aleatoire = new Solution();
		aleatoire.setDispositionSimple(disposition);
		aleatoire.centresDansLaSolution = new ArrayList<>(centresDansLaSolution);
		Random random = new Random();
		int tailleAgences = disposition.size();

		//on choisit une agence au hasard 
		int indexVille1 = random.nextInt(tailleAgences);
		int indexVille2 = random.nextInt(tailleAgences);

		Agence a1 = getAgenceFromMap(indexVille1);
		Agence a2 = getAgenceFromMap(indexVille2);

		// tant que ce ne sont pas des agences différentes on regénérère
		while ( indexVille1 == indexVille2) {
			indexVille1= random.nextInt(tailleAgences);
			indexVille2= random.nextInt(tailleAgences);
		}

		//on réalise un échange de centre
		CentreFormation c1 = disposition.get(a1);
		CentreFormation c2 = disposition.get(a2);

		aleatoire.getDispositionSimple().put(a1, c2);
		aleatoire.getDispositionSimple().put(a2, c1);

		//on met a jour
		c1.getAgencesAssociees().remove(a1);
		c1.getAgencesAssociees().add(a2);
		c2.getAgencesAssociees().remove(a2);
		c2.getAgencesAssociees().add(a1);

		return aleatoire;

	}

	public Solution voisinAleatoire() {

		Solution aleatoire = new Solution();
		aleatoire.setDispositionSimple(disposition);
		aleatoire.centresDansLaSolution = new ArrayList<>(centresDansLaSolution);
		aleatoire.centresDansLaCarte = new ArrayList<>(centresDansLaCarte);
		Random r = new Random();

		int indexCentreSolution = r.nextInt(centresDansLaSolution.size());
		int indexCentreCarte = r.nextInt(centresDansLaCarte.size());

		CentreFormation cSolution = centresDansLaSolution.remove(indexCentreSolution);
		CentreFormation cCarte = centresDansLaCarte.remove(indexCentreCarte);

		centresDansLaSolution.add(indexCentreSolution, cCarte);
		centresDansLaCarte.add(cSolution);
		agencesDansLaSolution = new ArrayList<Agence>(disposition.keySet());
		aleatoire.setDispositionSimple(new LinkedHashMap<Agence,CentreFormation>());
		for (CentreFormation c : centresDansLaSolution) {

			List<Agence> agencesProches = Utilitaires.chercherAgencesLesPlusProches(c,agencesDansLaSolution);

			for (Agence a : agencesProches) {
				aleatoire.getDispositionSimple().put(a,c);
			}
		}

		aleatoire.calculerFitness();

		return aleatoire;
	}

	public void croisement (Solution s2) {

		agencesDansLaSolution = new ArrayList<>(disposition.keySet());
		int index= new Random().nextInt(agencesDansLaSolution.size());
		List <Agence> l1 = agencesDansLaSolution.subList(0, index);
		List <Agence> l2 = agencesDansLaSolution.subList(index, agencesDansLaSolution.size());


		Map<Agence, CentreFormation> partie1 = l1.stream()
				.filter(disposition::containsKey)
				.collect(Collectors.toMap(Function.identity(), disposition::get));

		Map< Agence, CentreFormation> partie2 =  l2.stream()
				.filter(disposition::containsKey)
				.collect(Collectors.toMap(Function.identity(), disposition::get));

		partie1.putAll(partie2);
		disposition = new LinkedHashMap<>(partie1);
		calculerFitness();

	}

	/**
	 * permet de générer un voisinage
	 * @param nombreDeVoisins
	 * @return
	 */
	public List<Solution> genererVoisinage(int nombreDeVoisins) {
		List<Solution> voisinage = new ArrayList<Solution>();

		for ( int i=0 ; i < nombreDeVoisins ; i++) {
			Solution s = voisinAleatoire();

			while (!s.solutionPosiible()) {
				s = voisinAleatoire();
			}

			s.calculerFitness();
			voisinage.add(s);
		}
		return voisinage;
	}


	public void calculerFitness() {

		double coutFitness = centresDansLaSolution.size() * Lieux.COUT_TOTAL;

		for (Entry<Agence,CentreFormation> entry : disposition.entrySet()) {
			CentreFormation centre = entry.getValue();
			Agence agence = entry.getKey();
			coutFitness+=Lieux.COUT_EMP*2*agence.distance(centre)*agence.getNbEmploye();
		}

		this.setFitness(coutFitness);
	}

	public boolean solutionPosiible() {

		for ( CentreFormation c : centresDansLaSolution)
		{
			if (c.verifierCentre()==false) return false;
		}
		return true;
	}

	public void setNbCentres(int nbCentres) {
		this.nbCentres=nbCentres;
	}

	public Map<Agence, CentreFormation> getDispositionSimple() {
		return this.disposition;
	}

	public void setDispositionSimple(Map<Agence, CentreFormation> disposition) {
		this.disposition = disposition;
	}

	public void ajouterDispositon(Agence a, CentreFormation centreAleatoire) {
		disposition.put(a, centreAleatoire);
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public Agence getAgenceFromMap(int indexCentre) {
		agencesDansLaSolution = new ArrayList<Agence>(disposition.keySet());
		return agencesDansLaSolution.get(indexCentre);
	}

	@Override
	public boolean equals(Object obj ) {

		if (!(obj instanceof Solution)) {
			return false;
		}
		Solution other = (Solution) obj;
		return (other.getFitness()- getFitness() != 0.);
	}

	@Override
	protected Solution clone() {
		Map <Agence,CentreFormation> dispositionSimpleClone = new LinkedHashMap<Agence,CentreFormation>(disposition);
		List<Agence> agencesDansLaSolutionClone=new ArrayList<Agence>(agencesDansLaSolution) ;
		List<CentreFormation> centresDansLaSolutionClone=new ArrayList<CentreFormation>(centresDansLaSolution);

		return new Solution(dispositionSimpleClone, agencesDansLaSolutionClone, centresDansLaSolutionClone,carte);
	}

	@Override
	public int compareTo(Solution o) {
		return (int) (getFitness() - o.getFitness());
	}

}
