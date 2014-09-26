package com.rankminer.audioanalyser;

import java.io.File;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import com.rankminer.audioanalyser.config.Client;
import com.rankminer.audioanalyser.config.Config;

/**
 * 
 * Launches application
 * @author Amit
 * 
 *
 */
public final class ApplicationLauncher {

	private static final Logger LOGGER = Logger
			.getLogger(ApplicationLauncher.class);

	public ApplicationLauncher() {
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public Config readConfigurationFile(String fileName) {
		
		return null;
	}
	
	/**
	 * Test function to create a sample configuration file.
	 * @throws JAXBException
	 */
	public void writeConfigurationFile() throws JAXBException {
		Config config = new Config();

		Client c1 = new Client();
		c1.setDataArchiveDirectory("/opt/archive");
		c1.setDataErrorArchiveDirectory("/opt/archiveError");
		c1.setDataInputDirectory("/opt/input");
		c1.setDataOutputDirectory("/opt/output");
		c1.setName("DTC");
		c1.setPollingInterval(3600);

		config.getClientList().add(c1);
		
		c1 = new Client();
		c1.setDataArchiveDirectory("/opt/archive");
		c1.setDataErrorArchiveDirectory("/opt/archiveError");
		c1.setDataInputDirectory("/opt/input");
		c1.setDataOutputDirectory("/opt/output");
		c1.setName("CDC");
		c1.setPollingInterval(600);

		config.getClientList().add(c1);
		

		File file = new File("configuration"+ new Date().toString() + ".xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(config, file);

	}

	/**
	 * Load the Spring Integration Application Context
	 *
	 * @param args
	 *            - command line arguments
	 */
	public static void main(final String... args) {
		ApplicationLauncher l = new ApplicationLauncher();
		
		/*
		 * final Scanner scanner = new Scanner(System.in);
		 * 
		 * 
		 * if (LOGGER.isInfoEnabled()) {
		 * LOGGER.info("\n========================================================="
		 * + "\n                                                         " +
		 * "\n    Please press 'q + Enter' to quit the application.    " +
		 * "\n                                                         " +
		 * "\n=========================================================" ); }
		 * 
		 * 
		 * while (true) {
		 * 
		 * final String input = scanner.nextLine();
		 * 
		 * if("q".equals(input.trim())) { break; }
		 * 
		 * try {
		 * 
		 * } catch (Exception e) { LOGGER.error("An exception was caught: " +
		 * e); }
		 * 
		 * System.out.print("Please enter a string and press <enter>:");
		 * 
		 * }
		 * 
		 * if (LOGGER.isInfoEnabled()) {
		 * LOGGER.info("Exiting application...bye."); }
		 * 
		 * System.exit(0);
		 * 
		 * }
		 */
	}
}
