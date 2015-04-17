package qor;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestCompressor {
	
	@Test
	public void testValidCompression() {
		Compressor c = new Compressor();
		
		String expect = "DR.UP.";
		try{
			assertEquals(c.compress("draw{}update{}"), expect);
		} catch (Exception e){}
	}

	@Test
	public void testInvalidCharacter(){
		Compressor c = new Compressor();

		Throwable e = null;

		try {
			c.compress("draw{}update{}`_");
		} catch (Throwable ex) {
			e = ex;
		}

		assertTrue(e instanceof IllegalCharacterException);
	}

}
