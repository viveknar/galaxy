package galaxy;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {
	
	private static final Logger logger = Logger.getLogger(Logging.class.getPackage().getName());
	private FileHandler fh;
	private static Logging logInstance = null;
	
	protected Logging() {
	
	}
	
	public static Logging getInstance() {
		if (logInstance ==  null) {
			return new Logging();
		}
		return logInstance;
	}
	
	public void WriteToLog(String msg, String level) {	
	    try {  

	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler("LogFile.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	        // the following statement is used to log any messages
	        if (level.toLowerCase().equals("info"))
	        	logger.info(msg);  
	        
        	if (level.toLowerCase().equals("severe"))
	        	logger.severe(msg);  
	        	
	        if (level.toLowerCase().equals("warning"))
	        	logger.warning(msg);  


	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
 
	}
}
