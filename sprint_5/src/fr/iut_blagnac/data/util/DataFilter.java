package fr.iut_blagnac.data.util;



@SuppressWarnings("rawtypes")
public class DataFilter {
	
	private Class elementClass;
	private String champ;
	private String contenu;
	private boolean equals;

	public DataFilter(Class elementClass, String champ, String contenu, boolean equals){
		this.elementClass = elementClass;
		this.champ = champ;
		this.contenu = contenu;
		this.equals = equals;
	}
	
	public boolean matchFilter(OptiElement element){
		if(!element.getClass().equals(elementClass)){
			return false;
		}
		String value = element.getValueFor(champ);
		if( equals && contenu.length() != 0 && !value.equals(contenu)){
			return false;
		}
		if(!equals && contenu.length()!= 0 && !value.contains(contenu)){
			return false;
		}
		return true;
	}
}
