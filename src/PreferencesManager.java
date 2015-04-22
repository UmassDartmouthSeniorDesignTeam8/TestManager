import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/*
 * This class handles preferences, including preferred file
 * directories and QR code size preferences.
 */
public class PreferencesManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7317879850271771653L;
	public static String filePath;
	public static int qrCodeSize;
	private static PreferencesManager singleton;
	
	private PreferencesManager(){
	}
	
	public static PreferencesManager getInstance(){
		if (singleton==null)
			loadPreferences();
		return singleton;
	}
	
	// This will attempt to load the preferences file from the running directory. Default are loaded on error.
	private static void loadPreferences(){
		if (singleton==null)
			singleton = new PreferencesManager();
		ObjectInput object = null;
		try{
			InputStream file = new FileInputStream("OrionPreferences.cfg");
			InputStream buffer = new BufferedInputStream(file);
			object = new ObjectInputStream(buffer);
			try {
				PreferencesManager mgr = (PreferencesManager)object.readObject();
				singleton = mgr;
			} catch (ClassNotFoundException e) {
				System.out.println( "Preferences file not found. Loading default preferences.\n" + e.getMessage());
				loadDefaultPreferences();
			}
		} catch (FileNotFoundException e) {
			System.out.println( "Preferences file not found. Loading default preferences.\n" + e.getMessage());
			loadDefaultPreferences();
		} catch (IOException e){
			System.out.println( "Error loading preferences file. Loading default preferences.\n" + e.getMessage());
			loadDefaultPreferences();
		} finally {
			if (object!=null)
				try {
					object.close();
				} catch (IOException e) {
					
				}
		}
		
	}
	
	// Saves the preferences file
	private static void savePreferences(){
		ObjectOutput output = null;
		try{
			OutputStream file = new FileOutputStream("OrionPreferences.cfg");
			OutputStream buffer = new BufferedOutputStream(file);
			output = new ObjectOutputStream(buffer);
			output.writeObject(singleton);
		} catch (IOException e){
			System.out.println( "Error loading preferences file. Loading default preferences.\n"+e.getMessage());
		} finally {
			try{
				output.close();
			} catch (IOException e){
				
			}
		}
	}
	
	public static String getFilePath() {
		getInstance();
		return singleton.filePath;
	}
	public void setFilePath(String filePath) {
		getInstance();
		singleton.filePath = filePath;
		savePreferences();
	}
	public int getQrCodeSize() {
		getInstance();
		return singleton.qrCodeSize;
	}
	public void setQrCodeSize(int qrCodeSize) {
		getInstance();
		singleton.qrCodeSize = qrCodeSize;
		savePreferences();
	}
	
	private static void loadDefaultPreferences(){
		singleton.qrCodeSize = 58;
		singleton.filePath = 	Paths.get("data/").toAbsolutePath().toString();
		singleton.savePreferences();
	}
}
