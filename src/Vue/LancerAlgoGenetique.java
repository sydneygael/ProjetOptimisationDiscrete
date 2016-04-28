package Vue;

import java.io.IOException;

import Algorithmes.AlogrithmeGenetique;

public class LancerAlgoGenetique {

	public static void main(String args[]) {

		try {
			AlogrithmeGenetique g = new AlogrithmeGenetique(200, 10000);
			g.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
