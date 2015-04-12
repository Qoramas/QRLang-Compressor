package qor;

public class IllegalCharacterException extends Exception {
	String c;
	
	public IllegalCharacterException(String c) {
		this.c = c;
	}
	
	public String getIllegalCharacter(){
		return c;
	}
}
