package service;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log{

	private final static Logger log = Logger.getLogger(Log.class.getName());

	public Log() throws SecurityException {


		FileHandler fh;
		try {
			fh = new FileHandler("log/log.txt");
			fh.setFormatter(new SimpleFormatter());
			fh.setLevel(Level.INFO);
			log.addHandler(fh);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void info(String msg) {
		log.info(msg);
	}
	
	public void warning(String msg) {
		log.warning(msg);
	}

}
