package qor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class Compressor {
	
	public String compress(String code){
		code = removeComments(code);
		
		ArrayList<String> quotes = getQuotes(code);
		code = removeQuotes(code);
		
		code = replaceVariables(code);
		code = replaceSpecialCharacters(code);
		code = replaceConstants(code);
		code = preProcess(code);
		
		code = replaceQuotes(code, quotes);
		
		return code;
	}
	
	private String replaceQuotes(String code, ArrayList<String> quotes) {
		for(int i = 0; i < quotes.size(); i++){
			String quote = quotes.get(i);
			quote = quote.replaceAll("'", "QS");
			code = code.replace("?" + (i+1), quote);
		}
		
		return code;
	}

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
	
	private String removeComments(String code){
		return code.replaceAll(Statics.commentRegex, "");
	}
	
	private ArrayList<String> getQuotes(String code){
		ArrayList<String> quotes = new ArrayList<String>();
		
		Matcher matcher = Pattern.compile("'(.*?)'").matcher(code);
	    while(matcher.find()) quotes.add(matcher.group());
	    
		return quotes;
	}
	
	private String removeQuotes(String code){
	    Matcher matcher = Pattern.compile("'(.*?)'").matcher(code);
	    int i = 1;
	    while(matcher.find()){
	    	code = code.replace(matcher.group(), "?" + i);
	    	i++;
	    }
	    
		return code;
	}
	
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

	private String replaceSpecialCharacters(String code){
		for(String[] pair: Statics.matches){
			code = code.replaceAll(Pattern.quote(pair[0]),pair[1]);
		}
		
		return code;
	}
	
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
