package fr.iut_blagnac.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import fr.iut_blagnac.io.CSVManager;

public class MakeOptiWeb {

	private static String version = "0.1";

	public static void main(String[] args) {
		MakeOptiWeb.writeAll();
	}

	public static void writeAll(){
		PrintWriter writer;
		//*
		try {
			writer = new PrintWriter("OPTIweb.html", "UTF-8");
			writer.write(htmlHead());
			writer.write(pageAccueil());
			writer.write(pageAPropos());
			writer.write(pageProjets());
			writer.write(pageSujets());
			writer.write(pageEtudiants());
			writer.write(pageIntervenants());
			writer.write(finDePage());

			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Bad encoding");
		}
		//*/
	}
	
	public static String pageAccueil() throws UnsupportedEncodingException {

		// Attributes
		String htmlCode;
		htmlCode = "<!-- DEBUT page accueil -->\n"
				+ "<div data-role=\"page\" id=\"accueil\" data-title=\"OPTIweb - V" + version + "\">\n"
				+ "<div data-role=\"header\" data-add-back-btn=\"true\">\n"
				+ "<h1>P<span class=\"landscape\">rojets </span>tut<span class=\"landscape\">orés</span> 2014-2015<br/>Département INFO<span class=\"landscape\">RMATIQUE</span><br/>IUT de Blagnac</h1>\n"
				+ "<a href=\"#credits\" data-theme=\"b\" class=\"ui-btn-right\">Crédits</a>\n"
				+ "</div>\n"
				+ "<div data-role=\"content\">\n"
				+ "<ul data-role=\"listview\" data-inset=\"true\" id=\"listeSources\">\n"
				+ "  <li><a href=\"#projets\"><i class=\"fa fa-tasks\"></i> Projets</a></li>\n"
				+ "  <li><a href=\"#sujets\"><i class=\"fa fa-copy\"></i> Sujets</a></li>\n"
				+ "  <li><a href=\"#etudiants\"><i class=\"fa fa-group\"></i> Etudiants</a></li>\n"
				+ "  <li><a href=\"#intervenants\"><i class=\"fa fa-group\"></i> Intervenants</a></li>\n"
				+ "\n"
				+ "</ul>\n"
				+ "</div>\n"
				+ "<div data-role=\"footer\"> \n"
				+ " <h4>OPTIweb V<span class=\"landscape\">ersion </span>" + version + " <i class=\"fa fa- fa-2x\"></i></h4> \n"
				+ "</div>\n"
				+ "</div>\n"
				+ "<!-- FIN page accueil -->\n"
				+ "\n";

		// Returning the full html page code
		return new String(htmlCode.getBytes(), "UTF-8");
	}

	public static String pageAPropos() throws UnsupportedEncodingException {
		// Attributes
		String htmlCode;

		htmlCode = "<!-- DEBUT page credits -->\n"
				+ "<div data-role=\"page\" id=\"credits\" data-title=\"OPTIweb - V" + version + " - Crédits\">\n"
				+ "<div data-role=\"header\" data-add-back-btn=\"true\">\n"
				+ "<h1>Crédits</h1>\n"
				+ "</div>\n"
				+ "<div data-role=\"content\">\n"
				+ "    <p>Cette application a été réalisée dans le cadre du module M3301/MPA du DUT Informatique à l'IUT de Blagnac.</p>\n"
				+ "<ul data-role=\"listview\" data-inset=\"true\" id=\"contacts\" data-theme=\"a\" data-divider-theme=\"b\">\n"
				+ "    <li data-role=\"list-divider\">Product Owner</li>\n"
				+ "    <li>André PÉNINOU</li>\n"
				+ "    <li>Université Toulouse 2 - IUT de Blagnac\n"
				+ "    <br/>Département INFORMATIQUE</li>\n"
				+ "</ul>\n"
				+ "<ul data-role=\"listview\" data-inset=\"true\" id=\"listecredits\" data-theme=\"a\" data-divider-theme=\"b\">\n"
				+ "    <li data-role=\"list-divider\">Membres de l'équipe enseignante</li>\n"
				+ "<li>Jean-Michel BRUEL</li><li>Jean-Michel INGLEBERT</li><li>André PÉNINOU</li><li>Olivier ROQUES</li>\n"
				+ "</ul>\n"
				+ "<ul data-role=\"listview\" data-inset=\"true\" id=\"listepowered\" data-theme=\"a\" data-divider-theme=\"b\">\n"
				+ "    <li data-role=\"list-divider\">Propulsé par</li>\n"
				+ "    <li><a href=\"http://jquerymobile.com/\" target=\"autrePage\">http://jquerymobile.com/</a></li>\n"
				+ "    <li><a href=\"http://fortawesome.github.io/Font-Awesome/\" target=\"autrePage\">http://fortawesome.github.io/Font-Awesome/</a></li>\n"
				+ "</ul>\n"
				+ "</div>\n"
				+ "<div data-role=\"footer\"> \n"
				+ " <h4>OPTIweb V<span class=\"landscape\">ersion </span>" + version + " <i class=\"fa fa- fa-2x\"></i></h4> \n"
				+ "</div>\n"
				+ "</div>\n"
				+ "<!-- FIN page credits -->\n"
				+ "\n";

		// Returning the full html page code
		return new String(htmlCode.getBytes(), "UTF-8");
	}

	public static String htmlHead() throws UnsupportedEncodingException {
		// Attributes
		String htmlCode;

		htmlCode = "<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
				+ "<meta name=\"generator\" content=\"OPTIweb VOPTIweb\" />\n"
				+ "<title>" + version + " - V" + version + "</title>\n"
				+ "<link rel=\"stylesheet\" href=\"http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css\" />\n"
				+ "<link rel=\"stylesheet\" href=\"http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.css\" />\n"
				+ "<script src=\"http://code.jquery.com/jquery-1.9.1.min.js\"></script>\n"
				+ "<script src=\"http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js\"></script>\n"
				+ "<style type='text/css'>    \n"
				+ "@media all and (orientation:portrait) { .landscape {display: none;} }\n"
				+ "@media all and (orientation:landscape) { .landscape {display: inline;} }\n"
				+ "</style>\n"
				+ "</head><body>\n"
				+ "\n";

		// Returning the full html page code
		return new String(htmlCode.getBytes());
	}

	public static String pageEtudiants() throws UnsupportedEncodingException {
		String fileName = "./etudiants2014_2015.csv";

		String chaineFinale = "<!-- DEBUT page etudiants -->\n"
				+ "<div data-role=\"page\" id=\"etudiants\" data-title=\"OPTIweb - V"
				+ version
				+ "\">\n"
				+ "<div data-role=\"header\" data-add-back-btn=\"true\">\n"
				+ "<h1>Etudiants 2014-2015</h1>\n"
				+ "\n"
				+ "</div>\n"
				+ "<div data-role=\"content\">\n"
				+ "  \n"
				+ "  <form class=\"ui-filterable\">"
				+ "<input id=\"autocomplete-input-etudiant\" name=\"etudiant\" data-type=\"search\" placeholder=\"Etudiant ou Groupe X\">"
				+ "</form>"
				+ "<ol id=\"listeetudiants\" data-role=\"listview\" data-inset=\"true\" data-filter=\"true\" data-filter-reveal=\"false\" data-input=\"#autocomplete-input-etudiant\" data-divider-theme=\"b\">"
				+ "<li data-role=\"list-divider\">"
				+ "Etudiant<span class=\"ui-li-count\" title=\"Groupe\" style=\"right: 40px !important;\">Groupe</span>"
				+ "</li>";

		try {
			String donneesEtudiants[][] = CSVManager.openCSV(
					new File(fileName), ';');
			String[][] donneesEtudiantSansHeader = new String[donneesEtudiants.length-1][donneesEtudiants[0].length];
			System.arraycopy(donneesEtudiants, 1, donneesEtudiantSansHeader, 0, donneesEtudiantSansHeader.length);
			donneesEtudiants = donneesEtudiantSansHeader;
			trier(donneesEtudiants, 2);
			

			for (String etudiant[] : donneesEtudiants) {
				chaineFinale += "<li data-find=\""
						+ etudiant[3]
						+ " "
						+ etudiant[2]
						+ "\">"
						+ "<a href=\"#projets\">"
						+ etudiant[2]
						+ " "
						+ etudiant[3]
						+ "<span class=\"ui-li-count\" title=\"Groupe\">Groupe "
						+ etudiant[0] + "</span>" + "</a>" + "</li>";
			}
		} catch (FileNotFoundException e) {
			System.out
					.println("ERREUR : Impossible de charger ou de lire le fichier "
							+ fileName + " !");
		}

		chaineFinale += "</ol>\n"
				+ "</div>\n"
				+ "<div data-role=\"footer\"> \n"
				+ " <h4>OPTIweb V<span class=\"landscape\">ersion </span>" + version + " <i class=\"fa fa-group fa-2x\"></i></h4> \n"
				+ "</div>\n"
				+ "</div>\n"
				+ "<!-- FIN page etudiants -->\n"
				+ "\n";

		return new String(chaineFinale.getBytes());
	}

	public static String pageSujets() throws UnsupportedEncodingException {

		String fileNameSujet = "./sujets2014_2015.csv", fileNameProjet = "./projets2014_2015.csv";

		String chaineFinale = "<!-- DEBUT page sujets -->\n"
				+ "<div data-role=\"page\" id=\"sujets\" data-title=\"OPTIweb - V" + version + "\">\n"
				+ "<div data-role=\"header\" data-add-back-btn=\"true\">\n"
				+ "<h1>Sujets 2014-2015</h1>\n"
				+ "\n"
				+ "</div>\n"
				+ "<div data-role=\"content\">\n"
				+ "  \n"
				+ "  <form class=\"ui-filterable\">"
				+ "<input id=\"autocomplete-input-sujet\" name=\"sujet\" data-type=\"search\" placeholder=\"Vous cherchez ?\">"
				+ "</form>"
				+ "<ol id=\"listesujets\" data-role=\"listview\" data-inset=\"true\" data-filter=\"true\" data-filter-reveal=\"false\" data-input=\"#autocomplete-input-sujet\" data-divider-theme=\"b\" data-count-theme=\"a\">"
				+ "<li data-role=\"list-divider\">"
				+ "Sujet"
				+ "<span class=\"ui-li-count\" title=\"Groupe\" style=\"right: 40px !important;\">Groupe</span>"
				+ "</li>";


		try {
			String donneesSujet[][] = CSVManager.openCSV(
					new File(fileNameSujet), ';');
			
			String[][] donneesSujetSansHeader = new String[donneesSujet.length-1][donneesSujet[0].length];
			System.arraycopy(donneesSujet, 1, donneesSujetSansHeader, 0, donneesSujetSansHeader.length);
			donneesSujet = donneesSujetSansHeader;
			
			String donneesProjets[][] = CSVManager.openCSV(
					new File(fileNameProjet), ';');
			
			String[][] donneesProjetSansHeader = new String[donneesProjets.length-1][donneesProjets[0].length];
			System.arraycopy(donneesProjets, 1, donneesProjetSansHeader, 0, donneesProjetSansHeader.length);
			donneesProjets = donneesProjetSansHeader;
			
			trier(donneesSujet,1);

			List<String> groupesTemp;
			
			for (String sujet[] : donneesSujet) {
				groupesTemp = new ArrayList<String>(2);
				
				for (String[] projet : donneesProjets)
					if (projet[2].equals(sujet[0]))
						groupesTemp.add(projet[1]);
				
				chaineFinale += "<li data-find=\"["
						+ sujet[1]
						+ "]"
						+ "\">"
						+ "<a href=\"#projets\">["
						+ sujet[1]
						+ "] <br/>"
						+ "<div style=\"white-space:normal;\">"
						+ "<span><b>"
						+ sujet[2]
						+ "</b></span>"
						+ "<span class=\"ui-li-count\">";
				
				for (int i = 0; i < groupesTemp.size(); i++)
					chaineFinale += ((i == 0) ? groupesTemp.get(i) : " " + groupesTemp.get(i));
						
				chaineFinale += "</span>"
						+ "</div>"
						+ "</a>"
						+ "</li>";
			}
		} catch (FileNotFoundException e) {
			System.out
					.println("ERREUR : Impossible de charger ou de lire un des fichiers suivants :  "
							+ fileNameSujet + ", " + fileNameProjet + " !");
		}
		
		chaineFinale += "</ol>\n"
						+ "</div>\n"
						+ "<div data-role=\"footer\"> \n"
						+ " <h4>OPTIweb V"
						+ "<span class=\"landscape\">ersion </span>" + version
						+ " <i class=\"fa fa-copy fa-2x\">"
						+ "</i>"
						+ "</h4> \n"
						+ "</div>\n"
						+ "</div>\n"
						+ "<!-- FIN page sujets -->\n"
						+ "\n";		
		return new String(chaineFinale.getBytes());
	}
	
	public static String pageIntervenants () throws UnsupportedEncodingException {
		String fileNameInterv = "./intervenants2014_2015.csv";
		String fileNameProject = "./projets2014_2015.csv";

		String chaineFinale = "<!-- DEBUT page intervenants -->\n"
				+ "<div data-role=\"page\" id=\"intervenants\" data-title=\"OPTIweb - V" + version + "\">\n"
				+ "<div data-role=\"header\" data-add-back-btn=\"true\">\n"
				+ "<h1>Intervenants 2014-2015</h1>\n"
				+ "\n"
				+ "</div>\n"
				+ "<div data-role=\"content\">\n"
				+ "  \n"
				+ "  <form class=\"ui-filterable\">"
				+ "<input id=\"autocomplete-input-intervenant\" name=\"intervenant\" data-type=\"search\" placeholder=\"Intervenant\">"
				+ "</form>"
				+ "<ul id=\"listeintervenants\" data-role=\"listview\" data-inset=\"true\" data-filter=\"true\" data-filter-reveal=\"false\" data-input=\"#autocomplete-input-intervenant\" data-divider-theme=\"b\">"
				+ "<li data-role=\"list-divider\">"
				+ "Intervenant<span class=\"ui-li-count\" style=\"right: 110px !important;\" title=\"Client\">Client</span><span class=\"ui-li-count\" title=\"Superviseur\">Superviseur</span>"
				+ "</li>";
		
		try {
			String donneesIntervenants[][] = CSVManager.openCSV(new File(fileNameInterv), ';');
			String donneesProjets[][] = CSVManager.openCSV(new File(fileNameProject), ';');
			
			String[][] donneesIntervenantsSansHeader = new String[donneesIntervenants.length-1][donneesIntervenants[0].length];
			System.arraycopy(donneesIntervenants, 1, donneesIntervenantsSansHeader, 0, donneesIntervenantsSansHeader.length);
			donneesIntervenants = donneesIntervenantsSansHeader;
			
			String[][] donneesProjetsSansHeader = new String[donneesProjets.length-1][donneesProjets[0].length];
			System.arraycopy(donneesProjets, 1, donneesProjetsSansHeader, 0, donneesProjetsSansHeader.length);
			donneesProjets = donneesProjetsSansHeader;
			
			trier(donneesIntervenants, 2);
			
			int nbClient ;
			int nbSuperv ;
			

			for (String intervenant[] : donneesIntervenants) {
				
				nbClient = 0 ;
				nbSuperv = 0 ;
				
				for (String[] cursorProject : donneesProjets ) {
					if (cursorProject[3].equals(intervenant[0])) {
						nbClient ++ ;
					}
					if (cursorProject[4].equals(intervenant[0])) {
						nbSuperv ++ ;
					}
				}
				
				chaineFinale += "<li data-find=\"" + intervenant[2] + " " + intervenant[1] + "\">"
						+ "<a href=\"#projets\">"
						+ intervenant[2] + " " + intervenant[1] + "<span class=\"ui-li-count\" style=\"right: 120px !important;\" title=\"Client\">" + nbClient + "</span><span class=\"ui-li-count\" title=\"Superviseur\">" + nbSuperv + "</span>"
						+ "</a>"
						+ "</li>";
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("ERREUR : Impossible de charger ou de lire un des deux fichiers suivants : " + fileNameInterv + " " + fileNameProject);
		}
		
		chaineFinale += "</ul>\n"
				+ "</div>\n"
				+ "<div data-role=\"footer\"> \n" 
				+ " <h4>OPTIweb V<span class=\"landscape\">ersion </span>" + version + " <i class=\"fa fa-group fa-2x\"></i></h4> \n"
				+ "</div>\n"
				+ "</div>\n"
				+ "<!-- FIN page intervenants -->\n"
				+ "\n";
		
		return new String(chaineFinale.getBytes());
	}
	
	public static String pageProjets () throws UnsupportedEncodingException {
		String fileNameEtu = "./etudiants2014_2015.csv";
		String fileNamePro = "./projets2014_2015.csv";
		String fileNameInt = "./intervenants2014_2015.csv";
		String fileNameSuj = "./sujets2014_2015.csv";
		
		String chaineFinale = "<!-- DEBUT page projets -->\n"
		+ "<div data-role=\"page\" id=\"projets\" data-title=\"OPTIweb - V" + version + "\">\n"
		+ "<div data-role=\"header\" data-add-back-btn=\"true\">\n"
		+ "<h1>Projets 2014-2015</h1>\n"
		+ "\n"
		+ "</div>\n"
		+ "<div data-role=\"content\">\n"
		+ "  \n"
	    + "  <form class=\"ui-filterable\">"
		+ "<input id=\"autocomplete-input-projet\" name=\"projet\" data-type=\"search\" placeholder=\"Vous cherchez ?...\">"
		+ "</form>"
		+ "<ol id=\"listeprojets\" data-role=\"listview\" data-inset=\"true\" data-filter=\"true\" data-filter-reveal=\"false\" data-input=\"#autocomplete-input-projet\">";

		try {
			String donneesProjets[][] = CSVManager.openCSV(new File(fileNamePro), ';');
			String[][] donneesProjetSansHeader = new String[donneesProjets.length-1][donneesProjets[0].length];
			System.arraycopy(donneesProjets, 1, donneesProjetSansHeader, 0, donneesProjetSansHeader.length);
			donneesProjets = donneesProjetSansHeader;
			trier(donneesProjets, 0);
			
			String donneesSujets[][] = CSVManager.openCSV(new File(fileNameSuj), ';');
			String[][] donneesSujetSansHeader = new String[donneesSujets.length-1][donneesSujets[0].length];
			System.arraycopy(donneesSujets, 1, donneesSujetSansHeader, 0, donneesSujetSansHeader.length);
			donneesSujets = donneesSujetSansHeader;
			trier(donneesSujets, 0);
			
			String donneesIntervenants[][] = CSVManager.openCSV(new File(fileNameInt), ';');
			String[][] donneesIntervenantSansHeader = new String[donneesIntervenants.length-1][donneesIntervenants[0].length];
			System.arraycopy(donneesIntervenants, 1, donneesIntervenantSansHeader, 0, donneesIntervenantSansHeader.length);
			donneesIntervenants = donneesIntervenantSansHeader;
			trier(donneesIntervenants, 2);
			
			String donneesEtudiants[][] = CSVManager.openCSV(new File(fileNameEtu), ';');
			String[][] donneesEtudiantSansHeader = new String[donneesEtudiants.length-1][donneesEtudiants[0].length];
			System.arraycopy(donneesEtudiants, 1, donneesEtudiantSansHeader, 0, donneesEtudiantSansHeader.length);
			donneesEtudiants = donneesEtudiantSansHeader;
			trier(donneesEtudiants, 2);
			
			String sujetTemp[] = {"-1", "Inconnu", "Inconnu"}, intervenantsTemp[][] = {{"-1", "Inconnu", "Inconnu"}, {"-1", "Inconnu", "Inconnu"}};
			List<String[]> etudiantsTemp; 
			
			for (String projet[] : donneesProjets) {
				etudiantsTemp = new ArrayList<String[]>(5);
				
				for (String sujet[] : donneesSujets) {
					if (sujet[0].equals(projet[2])) {
						sujetTemp = sujet;
						break;
					}
				}
				
				for (String intervenant[] : donneesIntervenants) {
					if (intervenant[0].equals(projet[3]))
						intervenantsTemp[0] = intervenant;
					if (intervenant[0].equals(projet[4]))
						intervenantsTemp[1] = intervenant;
				}
				
				for (String etudiant[] : donneesEtudiants)
					if (etudiant[0].equals(projet[1]))
						etudiantsTemp.add(etudiant);
				
				chaineFinale += "<li><p><b>[" + sujetTemp[1] + "]</b> " + sujetTemp[2] + "</p>"
				+ "<p><b>Client :</b> " + intervenantsTemp[0][2] + " " + intervenantsTemp[0][1] + "</p>"
				+ "<p><b>Superviseur :</b> " + intervenantsTemp[1][2] + " " + intervenantsTemp[1][1] + "</p>"
				+ "<p><b>Groupe " + projet[1] + " :</b> ";
				
				for (String etudiant[] : etudiantsTemp)
					chaineFinale += etudiant[3] + " " + etudiant[2] + " - ";
				
				chaineFinale += "</p></li>";

			}
		}
		catch (FileNotFoundException e) {
			System.out.println("ERREUR : Impossible de charger ou de lire le fichier !");
		}

		chaineFinale += "</ol>\n"	
			+ "</div>\n"
			+ "<div data-role=\"footer\"> \n"
			+ " <h4>OPTIweb V<span class=\"landscape\">ersion </span>" + version + " <i class=\"fa fa-tasks fa-2x\"></i></h4> \n"
			+ "</div>\n"
			+ "</div>\n"
			+ "<!-- FIN page projets -->\n"
			+ "\n";

		return new String(chaineFinale.getBytes());
	}
	
	public static String finDePage () throws UnsupportedEncodingException {
		return new String(("<script>\n"
				+ " // li click handler which fills the projects search bar \n"
				+ " // with the value of the current data-find attribute\n"
				+ " $( 'li[data-find]' ).on( 'click',function(event){\n"
				+ "  $(\"#autocomplete-input-projet\").val($(this).attr('data-find')).trigger('change');\n"
				+ " });\n"
				+ "</script>\n"
				+ "</body>\n"
				+ "</html>\n").getBytes());
	}

	private static void trier(String donnees[][], int indexATrier) {
		String temp[];
		boolean termine;
	
		try {
			do {
				termine = true;
	
				for (int i = 0; i < donnees.length - 1; i++) {
					if (Integer.parseInt(donnees[i][indexATrier]) > Integer.parseInt(donnees[i + 1][indexATrier])) {
						termine = false;
						temp = donnees[i];
						donnees[i] = donnees[i + 1];
						donnees[i + 1] = temp;
					}
				}
			} while (!termine);
		}
		catch (Exception e) {
			do {
				termine = true;
	
				for (int i = 0; i < donnees.length - 1; i++) {
					if (donnees[i][indexATrier].toLowerCase().compareTo(donnees[i + 1][indexATrier].toLowerCase()) > 0) {
						termine = false;
						temp = donnees[i];
						donnees[i] = donnees[i + 1];
						donnees[i + 1] = temp;
					}
				}
			} while (!termine);
		}
	}
}
