import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AbstractFileMonitorTest {

	private AbstractFileMonitor fileMonitor;
	static final String FILE_PATH = System.getProperty("user.dir")+File.pathSeparator+"Abstract_File_Monitor_Test_File";

	@BeforeClass
	public static void beforeClassFunction(){
	}

	@Before
	public void beforeFunction() throws Exception{
		fileMonitor = new AbstractFileMonitor(FILE_PATH) {

			@Override
			public void setFilePath(String path) {}

			@Override
			public String getFilePath() throws IllegalStateException {
				return FILE_PATH;
			}	
		};
	}

	@After
	public void afterFunction(){
		File file = new File(FILE_PATH); 
		if(file.exists()) {
			file.delete();
		}
	}

	@AfterClass
	public static void afterClassFunction(){
		File file = new File(FILE_PATH); 
		if(file.exists()) {
			file.delete();
		}
	}

	@Test
	public void updateAndChangeTest() throws Exception{
		fileMonitor.update();
		
		Files.write(Paths.get(FILE_PATH), (randomString(10)+"\n").getBytes());
		
		assertFalse(fileMonitor.hasChanged());
		
		fileMonitor.update();
		
		assertTrue(fileMonitor.hasChanged());
		
		fileMonitor.update();
		
		assertFalse(fileMonitor.hasChanged());
		
		Files.write(Paths.get(FILE_PATH), (randomString(10)+"\n").getBytes());
		
		assertFalse(fileMonitor.hasChanged());
		
		fileMonitor.update();
		
		assertTrue(fileMonitor.hasChanged());
	}
	
	@Test
	public void noUpdateExceptionTest() throws Exception{		
		assertFalse(fileMonitor.hasChanged());
		
		Files.write(Paths.get(FILE_PATH), (randomString(10)+"\n").getBytes());
		
		assertFalse(fileMonitor.hasChanged());
	}

	@Test
	public void contructionTest() throws Exception{
		beforeFunction();
	}
	
	public String randomString(int length) {
		int leftLimit = 97;
	    int rightLimit = 122;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(length);
	    
	    for (int i = 0; i < length; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    
	    return buffer.toString();
	}

}