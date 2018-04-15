/**
 * Sample Skeleton for 'SpellChecker.fxml' Controller Class
 */

package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

public class SpellCheckerController {

	private Dictionary modello;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnSpellCheck"
    private Button btnSpellCheck; // Value injected by FXMLLoader

    @FXML // fx:id="btnClearText"
    private Button btnClearText; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnLanguage"
    private ComboBox<String> btnLanguage; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtTestoInserito"
    private TextArea txtTestoInserito; // Value injected by FXMLLoader

    @FXML // fx:id="txtParoleErrate"
    private TextArea txtParoleErrate; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtLabErr"
    private Label txtLabErr; // Value injected by FXMLLoader

    @FXML // fx:id="txtLabTmp"
    private Label txtLabTmp; // Value injected by FXMLLoader

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	if (btnLanguage.getValue() == null) {
    		txtTestoInserito.clear();
    		txtTestoInserito.appendText("Selezionare una lingua!\n");
    		return;
    	}
    	
    	String testo = txtTestoInserito.getText();
    	if (testo.isEmpty()) {
    		txtTestoInserito.clear();
    		txtTestoInserito.appendText("Inserire un testo!\n");
    		return;
    	}
    
    	modello.loadDictionary(btnLanguage.getValue());

    	testo = testo.toLowerCase();
    	String reg = "[.,\\/#!?$%\\^&\\*;:{}=\\-_'~()\\[\\]]";
    	testo = testo.replaceAll(reg, "");
    	
    	List<String> inputTextList = new LinkedList<String>();
    	StringTokenizer st = new StringTokenizer(testo, " ");
    	while (st.hasMoreTokens())
    		inputTextList.add(st.nextToken());
    	
    	List<RichWord> testoChecked = modello.spellCheckText(inputTextList);
    	int numErrori = 0;  String ww = "";
    	for (RichWord r : testoChecked) {
    		if (! r.isCorretta()) {
    			numErrori++;
    			ww += r.getParola() + "\n";
    		     
    		}
    	}
    	txtParoleErrate.clear();
    	txtParoleErrate.appendText(ww);
    	txtLabErr.setText("");
    	txtLabErr.setText("The text contains " + numErrori + " errors");
    	
    }
    
    @FXML
    void doClearText(ActionEvent event) {
    	
    	txtTestoInserito.clear();
    	txtParoleErrate.clear();
    	txtLabErr.setText(""); 
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	
    	 assert btnLanguage != null : "fx:id=\"btnLanguage\" was not injected: check your FXML file 'SpellChecker.fxml'.";
         assert txtTestoInserito != null : "fx:id=\"txtTestoInserito\" was not injected: check your FXML file 'SpellChecker.fxml'.";
         assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'SpellChecker.fxml'.";
         assert txtParoleErrate != null : "fx:id=\"txtParoleErrate\" was not injected: check your FXML file 'SpellChecker.fxml'.";
         assert txtLabErr != null : "fx:id=\"txtLabErr\" was not injected: check your FXML file 'SpellChecker.fxml'.";
         assert btnClearText != null : "fx:id=\"btnClearText\" was not injected: check your FXML file 'SpellChecker.fxml'.";
         assert txtLabTmp != null : "fx:id=\"txtLabTmp\" was not injected: check your FXML file 'SpellChecker.fxml'.";

    }
    
	public void setModel(Dictionary modello) {
		
		this.modello = modello; 
		
		btnLanguage.getItems().add("Italian");
		btnLanguage.getItems().add("English");
		
	}
}
