package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.execption.ExceptionCodePostal;
import org.apache.commons.lang3.math.NumberUtils;

import static org.apache.commons.lang3.math.NumberUtils.isDigits;
import static java.lang.Integer.parseInt;

/**
 * Recherche et affichage de toutes les villes d'un département dont la
 * population est comprise entre une valeur min et une valeur max renseignées
 * par l'utilisateur.
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationBorneService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner)  throws ExceptionCodePostal {

		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine();

		System.out.println("Choississez une population minimum (en milliers d'habitants): ");
		String saisieMin = scanner.nextLine();
		
		System.out.println("Choississez une population maximum (en milliers d'habitants): ");
		String saisieMax = scanner.nextLine();
		if ((!isDigits(saisieMin) ||!isDigits(saisieMax)) && !saisieMax.startsWith("-") && !saisieMin.startsWith("-")){
			throw new ExceptionCodePostal("veuillez ne rentrer que des chiffres pour le nombre d'habitant s'il vous plait");
		};

		if (parseInt(saisieMax) < 0 | parseInt(saisieMin)<0||parseInt(saisieMax)<parseInt(saisieMin)) {
			throw new ExceptionCodePostal("veuillez rentrer des nombres cohérents");

		}
		boolean choixExiste=false;
		for (Ville ville : rec.getVilles() ) {
			if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
				choixExiste=true;
			}
		}
		if (!choixExiste) {
			throw new ExceptionCodePostal("veuillez choisir un code postal connu");
		}


		int min = parseInt(saisieMin) * 1000;
		int max = parseInt(saisieMax) * 1000;
		
		List<Ville> villes = rec.getVilles();
		for (Ville ville : villes) {
			if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
				if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
					System.out.println(ville);
				}
			}
		}
	}

}
