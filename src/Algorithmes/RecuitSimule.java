package Algorithmes;

public class RecuitSimule {
	/**
	 * 
	 * 	Construire aléatoirement une solution initiale s
		z* := f(s) {meilleur coût obtenu}
		s* := s {meilleure solution connue}
		Initialiser T et ε
		Initialiser MaxIter et MaxGel
		NIter := 0 {nombre d’itérations}
		NGel := 0 {itérations sans améliorations}
		Répéter
		NIter := NIter + 1
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
		Jusqu’à (T ≤ ε) ou (NIter = MaxIter) ou (NGel = MaxGel).
	 */
	
	public RecuitSimule(int nbIterations,int temperature) {
		
	}

}
