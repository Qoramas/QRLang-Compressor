package qor;
import java.io.IOException;


public class Main {
	
	/**
	 * Sample program for the compressor
	 */
	public static void main(String[] args) throws IOException {
		String pong = Statics.readFile("games/breakout");
		
		Compressor c = new Compressor();
		
		try {
			pong = c.compress(pong);
		} catch (IllegalCharacterException e) {
			System.out.println("Illegal Character : " + e.getIllegalCharacter());
			e.printStackTrace();
		}
		
		//pong = pong.replaceAll("#thick", "4");
		
		System.out.println(pong);
		System.out.println(pong.length());
	}
	
	
}
