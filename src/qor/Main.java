package qor;
import java.io.IOException;


public class Main {
	
	public static void main(String[] args) throws IOException {
		String pong = Statics.readFile("games/breakout");
		
		Compressor c = new Compressor();
		
		pong = c.compress(pong);
		
		//pong = pong.replaceAll("#thick", "4");
		
		System.out.println(pong);
		System.out.println(pong.length());
	}
	
	
}
