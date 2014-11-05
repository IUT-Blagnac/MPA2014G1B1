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
	 * @param s : The String to test
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
	 * @param s : The String to test
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
	 * @param s : The String to test
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
	 * @param s : The String to test
	 * @param numberOfObject : The number of objects in the String
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
	/**
	 * Function to check if to arrays of the String are equals
	 * @param array1 : first array of String
	 * @param array2 : first array of String
	 * @return true if the arrays are equal, false if they aren't
	 */
	public static boolean arraysEqual(String[] array1, String[] array2){

		if(array1 == null || array2 == null || array1.length!= array2.length)
			return false;
		
		for (int i = 0; i < array1.length; i++) {
			if(!array1[i].equals(array2[i])){
				return false;
			}
		}
		return true;
	}
}
