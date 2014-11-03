package fr.iut_blagnac.util;

/**
 * Class used to different check about String (digit, letter, id, etc...)
 * @author Groupe 1B1
 * @version 2.0
 */
public class Str {

	/**
	 * This method return true if the given string is only composed of letters or char like  ' ' , '-' , ''' , ',' , '(' , ')' , '"', ':'
	 * False, if not
	 * @param s
	 * @return boolean
	 */
	public static boolean isLetter(String s){
		boolean valid = true;
		int i=0;
		
		while(i<s.length() && valid == true ){
			if (! Character.isLetter(s.charAt(i)) && ! (s.charAt(i) == ' ') && ! (s.charAt(i) == '-') && ! (s.charAt(i) == ('\'')) && ! (s.charAt(i)==',') && ! (s.charAt(i)=='(') && ! (s.charAt(i)==')') && ! (s.charAt(i)=='\"') && ! (s.charAt(i)==':')){
				valid = false;
			}
			i++;
		}
		return valid;		
	}
	
	/**
	 * This method return true if the given string is only composed of digit
	 * False if not
	 * @param s
	 * @return boolean
	 */
	public static boolean isDigit(String s){
		boolean valid = true;
		int i=0;
		
		while(i<s.length() && valid == true ){
			if (! Character.isDigit(s.charAt(i))){
				valid = false;
			}
			i++;
		}
		return valid;		
	}
	
	/**
	 * True if the given string is only composed of upperCase letter
	 * False if not
	 * Never used
	 * @param s
	 * @return boolean
	 */
	public static boolean isGroupValid(char s){
		boolean valid = true;
		if (!Character.isLetter(s) || !Character.isUpperCase(s)){
			valid = false;
		}
		return valid;		
	}
	
	/**
	 * Used to check the order input
	 * Return true if the given string is composed of x (x=numberOfObject) values and each oh them must be separated by ',' 
	 * and each of number is unique and not over x (x=numberOfObject)
	 * False if some values are missing or not separated by ',' or there's extra character 
	 * @param s
	 * @param numberOfObject
	 * @return boolean
	 */
	public static boolean isOrderValid (String s, int numberOfObject) {
		boolean valid = true;
		int i=0;
		
		while(i<s.length()){
			if (! Character.isDigit(s.charAt(i)) && ! (s.charAt(i) == ',')){
				return false;
			}
			if (s.length() > i){
				if (s.charAt(i)==',' && s.charAt(i+1)==',') {
					return false;
				}
			}
			i++;
		}
		
		String[] st = s.split(",");
		int[] storedValue = new int[st.length];
			
		if (st.length!=numberOfObject){
			return false;
		}
		
		for (int j=0 ; j<st.length; j++) {
			int val = Integer.parseInt(st[j]);
			
			if(val>numberOfObject) {
				return false;
			}

			for (int k=0 ; k<storedValue.length ; k++){
				if (val == storedValue[k]){
					return false;
				}
			}
			storedValue[j] = val;
		}
		return valid;	
	}
	
	public static void main (String[] args){
		/*String s ="1,2,3";
		String s1 ="1,2,3,4,5,6,7";
		String s2 = "1,2,2";
		String s3 = "1,2,3,5";
		
		//System.out.println(Str.isOrderValid(s,3));
		//System.out.println(Str.isOrderValid(s1, 7));
		//System.out.println(Str.isOrderValid(s2,3));
		System.out.println(Str.isOrderValid(s3,4));
		*/
	}
}
