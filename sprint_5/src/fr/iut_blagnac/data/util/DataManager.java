package fr.iut_blagnac.data.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import fr.iut_blagnac.data.etudiant.Etudiant;
import fr.iut_blagnac.data.groupe.Groupe;
import fr.iut_blagnac.data.intervenant.Intervenant;
import fr.iut_blagnac.data.projet.Projet;
import fr.iut_blagnac.data.sujet.Sujet;

/**
 * Data Manager
 * @author groupe 1B1
 * @version 2.0
 */
public class DataManager {
	
	private ArrayList<OptiElement> etudiants = new ArrayList<OptiElement>();
	private ArrayList<OptiElement> intervenants = new ArrayList<OptiElement>();
	private ArrayList<OptiElement> projets = new ArrayList<OptiElement>();
	private ArrayList<OptiElement> sujets = new ArrayList<OptiElement>();
	private ArrayList<OptiElement> groupes = new ArrayList<OptiElement>();
	
	private ArrayList<DataChangeListener> listeners = new ArrayList<DataChangeListener>();

	public ArrayList<OptiElement> getEtudiants(){
		return etudiants;
	}
	public ArrayList<OptiElement> getIntervenants(){
		return intervenants;
	}
	public ArrayList<OptiElement> getProjets(){
		return projets;
	}
	public ArrayList<OptiElement> getSujets(){
		return sujets;
	}
	public ArrayList<OptiElement> getGroupes(){
		return groupes;
	}

	public Groupe getGroupeWhere(String id) {
		for (OptiElement g : groupes){
			if (((Groupe) g).getLibelle().equals(id)) {
				return (Groupe) g;
			}
		}
		return null;
	}
	
	public Projet getProjetWhereIdGroup(String id){
		for (OptiElement p : projets){
			if (((Projet) p).getGroupe().getLibelle().equals(id)){
				return (Projet) p;
			}
		}
		return null;
	}
	
	public Sujet getSujetWhereIdSujet(String id){
		for (OptiElement s : sujets){
			if (((Sujet) s).getId().equals(id)){
				return (Sujet)s;
			}
		}
		return null;
	}
	
	public void refreshGroupes () {

		// Début de la récupération de tout les groupes
		Set<String> groupList = getAllGroups().keySet();
		Set<String> actualGroupList = new HashSet<String>();
		
		for (OptiElement e : this.groupes){
			actualGroupList.add(((Groupe)e).getLibelle());
		}
		
		for (String s : groupList) {
			Groupe g = new Groupe(s);
			if (!this.groupes.contains(g)) {
				this.groupes.add(new Groupe(s));
				actualGroupList.add(s);
			}
		}

		for (String s : actualGroupList) {
			if (!groupList.contains(s)) {
				for (OptiElement e : this.groupes) {
					if (((Groupe)e).getLibelle().equals(s)) {
						this.groupes.remove(e);
						break;
					}
				}
			}
		}
		// Fin de la récupération de tout les groupes

		// Ajout des données aux groupes
		Iterator<OptiElement> groupesIterator = groupes.iterator();
		while(groupesIterator.hasNext()){
			Groupe currentGroupe = ((Groupe)groupesIterator.next());

			// Ajout des étudiants
			for (OptiElement e : this.etudiants) {
				Etudiant etudiant = (Etudiant) e;
				if(etudiant.getGroupe().equals(currentGroupe)){
					currentGroupe.addStudent(etudiant);
				}
			}
			
			// Ajout des projets
			for (OptiElement e : this.projets) {
				Projet projet = (Projet) e;
				if(projet.getGroupe().equals(currentGroupe)){
					currentGroupe.setProjet(projet);
				}
			}
		}
		
	}

	public void extractProjetsFromGroupes(){
		Projet currentProjet;
		for (OptiElement e : this.groupes){
			currentProjet = ((Groupe)e).getProjet();
			if(currentProjet != null && !projets.contains(currentProjet)){
				projets.add(((Groupe)e).getProjet());
			} else {
				
			}
		}
	}
	

	public void refreshProjets () {

		// Ajout des données aux groupes
		Iterator<OptiElement> projetsIterator = projets.iterator();
		while(projetsIterator.hasNext()){
			Projet currentProjet = ((Projet)projetsIterator.next());

			
			// Ajout des projets
			for (OptiElement e : this.sujets) {
				Sujet sujet = (Sujet) e;
				if(currentProjet.getSujet() != null && currentProjet.getSujet().getId().equals(sujet.getId())){
					currentProjet.setSujet(sujet);
				}
			}
		}
		
	}
	
	public void addElement(OptiElement element){
		Class<? extends OptiElement> elementClass = element.getClass();
		if(elementClass.equals(Etudiant.class)){
			etudiants.add((Etudiant) element);
		} else if(elementClass.equals(Intervenant.class)){
			intervenants.add((Intervenant) element);		
		} else if(elementClass.equals(Projet.class)){
			projets.add((Projet) element);		
		} else if(elementClass.equals(Sujet.class)){
			sujets.add((Sujet) element);		
		}
	}
	
	public void removeElement(OptiElement element){
		Class<? extends OptiElement> elementClass = element.getClass();
		if(elementClass.equals(Etudiant.class)){
			etudiants.remove((Etudiant) element);
		} else if(elementClass.equals(Intervenant.class)){
			intervenants.remove((Intervenant) element);		
		} else if(elementClass.equals(Projet.class)){
			projets.remove((Projet) element);		
		} else if(elementClass.equals(Sujet.class)){
			sujets.remove((Sujet) element);		
		}
	}
	
	
	public void notifyDataChange(){
		this.refreshProjets();
		this.refreshGroupes();
		this.extractProjetsFromGroupes();
		for(DataChangeListener listener : listeners ){
			listener.dataChange();
		}
	}
	
	public void addDataChangeListener(DataChangeListener listener){
		listeners.add(listener);
	}
	public boolean removeDataChangeListener(DataChangeListener listener){
		return listeners.remove(listener);
	}
	
	public HashMap<String, Integer> getAllGroups(){
		HashMap<String, Integer> groups = new HashMap<String, Integer>();
		String group = "";
		for(OptiElement etu : etudiants){
			if(etu.isValid()){
				group = ((Etudiant) etu).getGroupe().getLibelle();
				if(!groups.containsKey(group)){
					groups.put(group, 0);
				} else {
					groups.put(group, groups.get(group)+1);
				}
			}
		}		
		return groups;
	}
	
	public List<OptiElement> getEtudiantsWhereGroupeContain(String idGroup){
		List<OptiElement> result = new ArrayList<OptiElement>();
		for(OptiElement etu : this.etudiants){
			if(((Etudiant)etu).getGroupe().getLibelle().contains(idGroup)){
				result.add(etu);
			}
		}
		return result;
	}
	
	public List<OptiElement> getSujetsWhereIdContains(String idGroup){
		List<OptiElement> result = new ArrayList<OptiElement>();
		for(OptiElement etu : this.sujets){
			if(((Sujet)etu).getId().contains(idGroup)){
				result.add(etu);
			}
		}
		return result;
	}
	
}
