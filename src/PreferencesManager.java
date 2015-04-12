import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/*
 * This class handles preferences, including preferred file
 * directories and QR code size preferences.
 */
public class PreferencesManager {
	
	public String filePath;
	public int qrCodeSize;
	private static PreferencesManager singleton;
	
	private PreferencesManager(){
		if (singleton==null)
			loadPreferences();
	}
	
	public static PreferencesManager getInstance(){
		return singleton;
	}
	
	private void loadPreferences(){
		ArrayList<String> contents = new ArrayList<String>();
		try{
			File inputFile = new File("OrionPreferences.cfg");
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Preferences file not found. Loading default preferences.");
		} catch (IOException e){
			JOptionPane.showMessageDialog(null, "Error loading preferences file. Loading default preferences.");
		}
		
	}
	
	private void savePreferences(){
		
	}
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
		savePreferences();
	}
	public int getQrCodeSize() {
		return qrCodeSize;
	}
	public void setQrCodeSize(int qrCodeSize) {
		this.qrCodeSize = qrCodeSize;
		savePreferences();
	}
	
	
}
