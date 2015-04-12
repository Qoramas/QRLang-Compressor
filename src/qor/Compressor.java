package qor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


/**
 * Compressor for the QRLang project. This takes QRLang code and compresses it ready for use
 * in a decompressor or to be encoded into a QR code.
 * 
 * @author Samuel Haycock
 *
 */
public class Compressor {
	
	/**
	 * Compresses the given code to QRLang compressed code.
	 * @param code the code to be compressed.
	 * @return the compressed code
	 * @throws IllegalCharacterException if there is an illegal character in the code
	 */
	public String compress(String code) throws IllegalCharacterException{
		code = removeComments(code);
		
		ArrayList<String> quotes = getQuotes(code);
		code = removeQuotes(code);
		
		code = replaceVariables(code);
		code = replaceSpecialCharacters(code);
		code = replaceConstants(code);
		code = preProcess(code);
		
		code = replaceQuotes(code, quotes);
		
		for(int i = 0; i < code.length(); i++){
			if(!Statics.characters.contains(code.substring(i,i+1))){
				throw new IllegalCharacterException(code.substring(i,i+1));
			}
		}
		
		return code;
	}
	
	//Replaces all of the removed qoutes back into the code
	private String replaceQuotes(String code, ArrayList<String> quotes) {
		for(int i = 0; i < quotes.size(); i++){
			String quote = quotes.get(i);
			quote = quote.replaceAll("'", "QS");
			code = code.replace("?" + (i+1), quote);
		}
		
		return code;
	}

	//Does precalculations to constants in the code to minimise space
	//eg. if 244+55 was found in the code it would be replaced with 299
	private String preProcess(String code) {
		ScriptEngineManager mgr = new ScriptEngineManager();
	    ScriptEngine engine = mgr.getEngineByName("JavaScript");
	    
		Matcher matcher = Pattern.compile("\\d+[+-]\\d+").matcher(code);
		
	    while(matcher.find()){
	    	String match = matcher.group();
	    	try {
	    		String result = engine.eval(match).toString();
	    		result = result.substring(0, result.length()-2);
				code = code.replace(match, result);
			} catch (ScriptException e) {}
	    	matcher = Pattern.compile("\\d+[+-]\\d+").matcher(code);
	    }
		
		return code;
	}
	
	//Removes all comments in the code with regex
	private String removeComments(String code){
		return code.replaceAll(Statics.commentRegex, "");
	}
	
	//Retreive an arraylist of quotes in the project
	private ArrayList<String> getQuotes(String code){
		ArrayList<String> quotes = new ArrayList<String>();
		
		Matcher matcher = Pattern.compile("'(.*?)'").matcher(code);
	    while(matcher.find()) quotes.add(matcher.group());
	    
		return quotes;
	}
	
	//Removes all quotes within the code
	private String removeQuotes(String code){
	    Matcher matcher = Pattern.compile("'(.*?)'").matcher(code);
	    int i = 1;
	    while(matcher.find()){
	    	code = code.replace(matcher.group(), "?" + i);
	    	i++;
	    }
	    
		return code;
	}
	
	//Replaces all variables in the code with a minified version ($)
	private String replaceVariables(String code){
		ArrayList<String> matches = new ArrayList<String>();
		Matcher matcher = Pattern.compile("\\$(\\w*)").matcher(code);

	    while(matcher.find()) matches.add(matcher.group());
	    
	    //Removes all duplicates
	    LinkedHashSet hs = new LinkedHashSet();
	    hs.addAll(matches);
	    matches.clear();
	    matches.addAll(hs);
	    
	    for(int i = 0; i < matches.size(); i++){
	    	code = code.replaceAll(Pattern.quote(matches.get(i)), "\\$" + Statics.cList.charAt(i));
	    }
	    
		return code;
	}

	//Replaces all special characters and sequences within the code
	private String replaceSpecialCharacters(String code){
		for(String[] pair: Statics.matches){
			code = code.replaceAll(Pattern.quote(pair[0]),pair[1]);
		}
		
		return code;
	}
	
	//Replaces all constants within the document to their set values
	private String replaceConstants(String code){
		code = code.replaceAll("#w", "160");
		code = code.replaceAll("#h", "90");
		Matcher matcher = Pattern.compile("#\\w*%\\d+").matcher(code);
		
		while(matcher.find()){
			String match = matcher.group();
			code = code.replace(match, "");
			String constant = match.substring(0, match.indexOf("%"));
			String number = match.substring(match.indexOf("%")+1);
			code = code.replaceAll(constant, number);
		}
		
		return code;
	}
}
