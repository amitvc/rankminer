//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.25 at 10:41:20 PM EDT 
//


package com.rankminer.audioanalyser.config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;

import com.rankminer.audioanalyser.jni.CoreRankMinerImpl;


/**
 * <p>Java class for client complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="client">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInputDirectory" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataOutputDirectory" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataArchiveDirectory" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataErrorArchiveDirectory" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pollingInterval" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "client", propOrder = {
    "identifier",
    "dataInputDirectory",
    "dataOutputDirectory",
    "dataArchiveDirectory",
    "dataErrorArchiveDirectory",
    "pollingInterval"
})
public class Client implements Runnable {
	private static final Logger LOGGER = Logger
			.getLogger(Client.class);
    @XmlElement(required = true)
    protected String identifier;
    @XmlElement(required = true)
    protected String dataInputDirectory;
    @XmlElement(required = true)
    protected String dataOutputDirectory;
    @XmlElement(required = true)
    protected String dataArchiveDirectory;
    @XmlElement(required = true)
    protected String dataErrorArchiveDirectory;
    protected long pollingInterval;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifier(String value) {
        this.identifier = value;
    }

    /**
     * Gets the value of the dataInputDirectory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInputDirectory() {
        return dataInputDirectory;
    }

    /**
     * Sets the value of the dataInputDirectory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInputDirectory(String value) {
        this.dataInputDirectory = value;
    }

    /**
     * Gets the value of the dataOutputDirectory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataOutputDirectory() {
        return dataOutputDirectory;
    }

    /**
     * Sets the value of the dataOutputDirectory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataOutputDirectory(String value) {
        this.dataOutputDirectory = value;
    }

    /**
     * Gets the value of the dataArchiveDirectory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataArchiveDirectory() {
        return dataArchiveDirectory;
    }

    /**
     * Sets the value of the dataArchiveDirectory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataArchiveDirectory(String value) {
        this.dataArchiveDirectory = value;
    }

    /**
     * Gets the value of the dataErrorArchiveDirectory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataErrorArchiveDirectory() {
        return dataErrorArchiveDirectory;
    }

    /**
     * Sets the value of the dataErrorArchiveDirectory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataErrorArchiveDirectory(String value) {
        this.dataErrorArchiveDirectory = value;
    }

    /**
     * Gets the value of the pollingInterval property.
     * 
     */
    public long getPollingInterval() {
        return pollingInterval;
    }

    /**
     * Sets the value of the pollingInterval property.
     * 
     */
    public void setPollingInterval(long value) {
        this.pollingInterval = value;
    }
    
    public void run() {
    	try {
    		File [] files = new File(dataInputDirectory).listFiles();
    		for(File file : files) {
    			if(file.isFile()) {
    				try {
						byte[] audioData = extractData(file);
						double[] featureVector = CoreRankMinerImpl.processFile(audioData);
						SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
						createFeatureVectorFile(dataOutputDirectory, 
								file.getName() + "" + formatter.format(new Date()) + ".dat",
								featureVector);
						archiveAudioFile(dataInputDirectory, file.getName(), dataArchiveDirectory);
					} catch (IOException e) {
						e.printStackTrace();
						LOGGER.error("Unable to extract data from file " + file.getName());
					}
    				
    			}
    		}
			Thread.sleep(pollingInterval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Archive audio file from input directory to archive directory. 
     * @param dataInputDirectory
     * @param name
     * @param dataArchiveDirectory
     * @throws IOException 
     */
    private static void archiveAudioFile(String dataInputDirectory, String name,
			String dataArchiveDirectory) throws IOException {
    	File audioFileInput = new File(dataInputDirectory + File.separator + name);
    	Files.move(audioFileInput.toPath(), new File(dataArchiveDirectory + File.separator + name).toPath());
	}

	/**
     * Method extracts audio file data.
     * @param file audio file
     * @return byte[] of data in audio file
     * @throws IOException
     */
    public static byte[] extractData(File file) throws IOException {
    	 BufferedInputStream bis = null;
    	 BufferedOutputStream bos = null;
    	 bis = new BufferedInputStream(new FileInputStream(file));
    	 ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	 bos = new BufferedOutputStream(baos);
    	 int c;
    	 while ((c = bis.read()) != -1) {
    		 bos.write(c);
    	 }
    	 bos.flush();
    	 bis.close();
    	 bos.close();
    	 return baos.toByteArray();
    }
    
    /**
     * 
     * @param directory
     * @param fileName
     * @param data
     * @throws IOException 
     */
    public static void createFeatureVectorFile(String directory, String fileName, double[] data) throws IOException {
    	File featureVectorFile = new File(directory + File.separator + fileName);
    	BufferedWriter output = new BufferedWriter(new FileWriter(featureVectorFile));
    	StringBuffer sb = new StringBuffer();
    	for(int i=0;i<data.length;i++) {
    		sb.append(data[i] + ",");
    	}
    	output.write(sb.toString());
    	output.flush();
    	output.close();
    }
}
