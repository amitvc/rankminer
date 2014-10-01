package com.rankminer.audioanalyser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
	 * @throws JAXBException 
	 */
	public Config readConfigurationFile(String fileName) throws JAXBException {
		File file = new File(fileName);
		JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
		Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();
		Config config = (Config)jaxbMarshaller.unmarshal(file);
		return config;
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
		c1.setIdentifier("DTC");
		c1.setPollingInterval(3600);

		config.getClientList().add(c1);
		
		c1 = new Client();
		c1.setDataArchiveDirectory("/opt/archive");
		c1.setDataErrorArchiveDirectory("/opt/archiveError");
		c1.setDataInputDirectory("/opt/input");
		c1.setDataOutputDirectory("/opt/output");
		c1.setIdentifier("CDC");
		c1.setPollingInterval(600);

		config.getClientList().add(c1);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		File file = new File("configuration"+ formatter.format(new Date()) + ".xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		System.out.println("Generating sample configuration file - " + file.getName() );
		jaxbMarshaller.marshal(config, file);
		
	}

	/**
	 * Load the Spring Integration Application Context
	 *
	 * @param args
	 *            - command line arguments
	 */
	public static void main(final String args[]) {
		ApplicationLauncher l = new ApplicationLauncher();
		if(args.length == 0) {
			reportUsage();
			System.exit(0);
		}
		
		if(args[0].equalsIgnoreCase("generate")) {
			try {
				l.writeConfigurationFile();
			} catch (JAXBException e) {
				LOGGER.error("Problems writing configuration file. Exiting application");	
				e.printStackTrace();
				System.exit(0);
			}
		} else {
			try {
				Config config = l.readConfigurationFile(args[0]);
				l.spawnAudioProcessorThread(config);
			} catch (JAXBException e) {
				LOGGER.error("Problems reading configuration. Exiting application");
				e.printStackTrace();
			}
			final Scanner scanner = new Scanner(System.in);
			System.out.print("Please enter 'q' to exit application:");
			while (true) {
				final String input = scanner.nextLine();
				if ("q".equals(input.trim())) {
					break;
				}
				System.out.print("Please enter a string and press <enter>:");
			}
			System.exit(0);
		}
	}

	/**
	 * Method spawns threads which run in background to process audio files.
	 * The Config object drives the application audio analyser threads.
	 * @param config
	 */
	private void spawnAudioProcessorThread(Config config) {
		List<Client> clientList = config.getClientList();
		for(Client client : clientList) {
			Thread clientThread = new Thread(client);
			clientThread.start();
		}
	}

	
	/**
	 * Method displays usage of the application.
	 */
	private static void reportUsage() {
		System.out.println("Application runs as background process\n"+
							"Type java -jar audioanalyser.jar configuration_file_name\n"+
							"Type java -jar audioanalyser.jar generate - to generate sample configuration file.");
		
	}			
}
