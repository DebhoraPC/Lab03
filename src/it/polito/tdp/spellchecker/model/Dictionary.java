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
		//paroleDizionario = new LinkedList<String>();
		lingua = language;
		
		// CODICE PER LEGGERE LE PAROLE DA FILE
		try {
			
			FileReader fr = new FileReader("rsc/" + lingua + ".txt");
			BufferedReader br = new BufferedReader(fr);
			String word;
			while((word = br.readLine()) != null) 
				paroleDizionario.add(word.toLowerCase());
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
					ris.add(new RichWord(s, true));
				else 
					ris.add(new RichWord(s, false));
		
		return ris;
		
	}
	
	/**
	 * Esegue il controllo ortografico in modo lineare: itera su tutti gli elementi del vocabolario
	 * a partire dal primo. La ricerca termina quando viene trovato l'elemento cercato o si raggoinge 
	 * l'ultimo, nel caso in cui l'elemento cercato non sia presente nella lista.
	 * 
	 * @param inputTextList lista di parole inserite dall'utente
	 * @return lista di {@code RichWord}
	 */
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
		
		List<RichWord> ris = new LinkedList<RichWord>();
		
		for (String s : inputTextList) {
			boolean trovato = false;	
			for (String w : paroleDizionario) {
				if (w.equals(s)) {
					trovato = true;
					break;
				}
			}
			ris.add(new RichWord(s, trovato));
		}
		
		return ris;
		
	}
	
	/**
	 * Esegue il controllo ortografico in modo dicotomico: sapendo che il dizionario è ordinato, l'idea
	 * è quella di non iniziare la ricerca dal primo elemento, ma da quello centrale, cioè a metà
	 * dizionario. Si confronta questo elemento con quello cercato: se {@code corrisponde} la ricerca termina
	 * indicando che l'elemento è stato trovato; se è {@code superiore} la ricerca viene ripetuta sugli elementi
	 * precedenti (ovvero sulla prima metà del dizionario), scartando quelli successivi; se è {@code inferiore}
	 * la ricerca viene ripetuta sugli elementi successivi (ovvero sulla seconda metà del dizionario),
	 * scartando quelli precendenti. Il procedimento viene ripetuto iterativamente fino a quando o si trova 
	 * l'elemento cercato, o tutti gli elementi vengono scartati. In quest'ultimo caso la ricerca termina indicando
	 * che il valore non è stato trovato.
	 *  
	 * @param inputTextList lista di parole inserite dall'utente
	 * @return lista di {@code RichWord}
	 */
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		
		List<RichWord> ris = new LinkedList<RichWord>();
		
		for (String s : inputTextList) {
			
			boolean trovato = false;
			int fine = paroleDizionario.size();
			int inizio = 0; 
			
			while (inizio != fine) {
				int medio = inizio + (fine - inizio) / 2;
				if (s.compareTo(paroleDizionario.get(medio)) == 0) {
					trovato = true;
					break;
				}
				else if (s.compareToIgnoreCase(paroleDizionario.get(medio)) > 0) 
					inizio = medio + 1;
				else 
					fine = medio;
			}
			ris.add(new RichWord(s, trovato));
		}
		
		return ris;
		
	}

}
