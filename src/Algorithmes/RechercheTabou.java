package Algorithmes;

import java.io.IOException;
import java.util.List;

import Utils.Utilitaires;

/**
 * 
 * @author Sydney
 * Construire une solution initiale s {solution initiale}
* Calculer son coût z = f(s)
* z* := z {meilleur coût obtenu}
* s* := s {meilleure solution connue}
* Initialiser MaxIter {nombre max d’itérations}
* T := ∅ {vide la liste tabou T}
* NIter := 0 {nombre d’itérations} 
*Répéter {boucle principale}
*NIter := NIter + 1 {compte une itération}
* z" := +∞ {initialise coût de s"}
* Pour toute solution s’ de V(s) {exploration de V(s)}
* Si s’ ∉ T et f(s’) < z" alors {si s’ n’est pas tabou}
* s" := s’ {mise à jour du meilleur voisin s"}
* z" := f(s’) {et de son coût}
* FS
* FP
* Si z" < +∞ alors {si s" a été trouvé}
* Enlever solution en tête de T {mise à jour de la liste tabou}
* Ajouter s en fin de T
* s := s" {changement de solution}
* z := z"
* Si z < z* alors {mise à jour meilleure solution s*}
* s* := s
* z* := z
* FS
* FS
* Jusqu’à (NIter = MaxIter) ou (z" = +∞). 
*
*/
public class RechercheTabou extends Metaheuristique {
	
	private List<Solution> listeTabou;
	private int tailleListeTabou ;
	
	public RechercheTabou (int nbIterations,int tailleListeTabou,int nbVoisins) {
		this.nbIterations= nbIterations;
		this.tailleListeTabou=tailleListeTabou;
		this.nbVoisins = nbVoisins;
	}
	
	public void run () throws IOException {
		
		//s = Utilitaires.genererSolutionAleatoire();
		meilleurCout=s.getFitness();
	}

}
