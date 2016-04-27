package Algorithmes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import Formation.Agence;
import Formation.Carte;
import Formation.CentreFormation;
import Formation.Lieux;

public class SolutionSimple extends Solution {

	private Map <Agence,CentreFormation> dispositionSimple;
	private List<Agence> agencesDansLaSolution ;
	public List<CentreFormation> centresDansLaSolution;

	int nbCentres ;
	private Carte carte;

	public SolutionSimple (){
		agencesDansLaSolution = new ArrayList<Agence>();
		dispositionSimple = new LinkedHashMap <Agence,CentreFormation>();
		centresDansLaSolution = new ArrayList<CentreFormation>();
	}
	
	public SolutionSimple (Map <Agence,CentreFormation> dispositionSimple,
			List<Agence> agencesDansLaSolution,List<CentreFormation> centresDansLaSolution){
		this.agencesDansLaSolution = agencesDansLaSolution;
		this.dispositionSimple = dispositionSimple;
		this.centresDansLaSolution = centresDansLaSolution;
	}
	
	public SolutionSimple (Map <Agence,CentreFormation> dispositionSimple,
			List<Agence> agencesDansLaSolution,List<CentreFormation> centresDansLaSolution,
			Carte carte){
		this.agencesDansLaSolution = agencesDansLaSolution;
		this.dispositionSimple = dispositionSimple;
		this.centresDansLaSolution = centresDansLaSolution;
	}
	
	public SolutionSimple (Carte carte) {
		agencesDansLaSolution = new ArrayList<Agence>();
		dispositionSimple = new LinkedHashMap <Agence,CentreFormation>();
		centresDansLaSolution = new ArrayList<CentreFormation>();
		this.carte=carte;
	}

	public SolutionSimple construireVoisinAleatoire () {

		SolutionSimple aleatoire = new SolutionSimple();
		aleatoire.setDispositionSimple(dispositionSimple);
		aleatoire.centresDansLaSolution = new ArrayList<>(centresDansLaSolution);
		Random random = new Random();
		int tailleAgences = dispositionSimple.size();

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
		CentreFormation c1 = dispositionSimple.get(a1);
		CentreFormation c2 = dispositionSimple.get(a2);
		
		aleatoire.getDispositionSimple().put(a1, c2);
		aleatoire.getDispositionSimple().put(a2, c1);

		//on met a jour
		c1.getAgencesAssociees().remove(a1);
		c1.getAgencesAssociees().add(a2);
		c2.getAgencesAssociees().remove(a2);
		c2.getAgencesAssociees().add(a1);

		return aleatoire;

	}

	/**
	 * permet de générer un voisinage
	 * @param nombreDeVoisins
	 * @return
	 */
	public List<SolutionSimple> genererVoisinageS(int nombreDeVoisins) {
		List<SolutionSimple> voisinage = new ArrayList<SolutionSimple>();

		for ( int i=0 ; i < nombreDeVoisins ; i++) {
			SolutionSimple s = construireVoisinAleatoire();

			while (!s.solutionPosiible()) {
				s = construireVoisinAleatoire();
			}
			
			s.calculerFitness();
			voisinage.add(s);
		}
		return voisinage;
	}

	@Override
	public void calculerFitness() {

		double coutFitness = centresDansLaSolution.size() * Lieux.COUT_TOTAL;

		for (Entry<Agence,CentreFormation> entry : dispositionSimple.entrySet()) {
			CentreFormation centre = entry.getValue();
			Agence agence = entry.getKey();
			coutFitness+=Lieux.COUT_EMP*2*agence.distance(centre)*agence.getNbEmploye();
		}
		
		this.fitness = coutFitness;
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
		return this.dispositionSimple;
	}

	public void setDispositionSimple(Map<Agence, CentreFormation> disposition) {
		this.dispositionSimple = disposition;
	}

	public void ajouterDispositon(Agence a, CentreFormation centreAleatoire) {
		dispositionSimple.put(a, centreAleatoire);
	}
	
	public Agence getAgenceFromMap(int indexCentre) {
		agencesDansLaSolution = new ArrayList<Agence>(dispositionSimple.keySet());
		return agencesDansLaSolution.get(indexCentre);
	}

	@Override
	public boolean equals(Object obj ) {

		if (!(obj instanceof SolutionSimple)) {
			return false;
		}
		SolutionSimple other = (SolutionSimple) obj;
		return (other.getFitness()-getFitness() != 0.);
	}

	@Override
	protected SolutionSimple clone() {
		Map <Agence,CentreFormation> dispositionSimpleClone = new LinkedHashMap<Agence,CentreFormation>(dispositionSimple);
		List<Agence> agencesDansLaSolutionClone=new ArrayList<Agence>(agencesDansLaSolution) ;
		List<CentreFormation> centresDansLaSolutionClone=new ArrayList<CentreFormation>(centresDansLaSolution);
		return new SolutionSimple(dispositionSimpleClone, agencesDansLaSolutionClone, centresDansLaSolutionClone);
	}

}
