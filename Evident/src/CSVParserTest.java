import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class CSVParserTest {

	private final CSVParser csvParser = new CSVParser();
	@Before
	public void setUp(){
		
	}
	
	@Test(expected=NullPointerException.class)
	public void testParseFileNullParam() {
		csvParser.parseFile(null);
		fail("Exception was not thrown");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testParseFileInvalidFileExtension() {
		csvParser.parseFile("invalid.txt");
		fail("Exception was not thrown");
	}
	


}
