package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Modello dell'Applicazione
 */
public class Dictionary {
	
	private List<String> paroleDizionario; 
	private String lingua;

	public Dictionary() {
		
	}
	
	/**
	 * Permette di caricare in memoria il dizionario della lingua desiderata
	 * @param language lingua desiderata
	 */
	public void loadDictionary(String language) {
		
		paroleDizionario = new ArrayList<String>();
		lingua = language;
		
		// CODICE PER LEGGERE LE PAROLE DA FILE
		try {
			
			FileReader fr = new FileReader("rsc/" + lingua + ".txt");
			BufferedReader br = new BufferedReader(fr);
			String word;
			while((word = br.readLine()) != null) 
					paroleDizionario.add(word);
			br.close();
			
		} catch(IOException e) {
			System.out.println("Errore nella lettura del file");
		}
		
	}
	
	/**
	 * Esegue il controllo ortografico sul testo in input
	 * @param inputTextList lista di parole inserite dall'utente
	 * @return lista di {@code RichWord} 
	 */
	public List<RichWord> spellCheckText(List<String> inputTextList) {
		
		List<RichWord> ris = new LinkedList<RichWord>();
		
		// LA PAROLA E' CORRETTA SE E' PRESENTE NEL DIZIONARIO 
		for (String s : inputTextList) 
				if(this.paroleDizionario.contains(s)) 
					ris.add(new RichWord(s,true));
				else 
					ris.add(new RichWord(s,false));
		
		return ris;
		
	}

}
