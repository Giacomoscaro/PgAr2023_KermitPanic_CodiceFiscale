import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import java.io.FileInputStream;
import java.util.Map;
import java.util.HashMap;


public class CodiceFiscale {
	
	private static final String LETTERE="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMERI="0123456789";
    
	/**
	 * Mappatura tra mesi e il carattere corrispondente nel codice fiscale
	 */
	public static final HashMap<Character,Data.Mese>CarattereMese = new HashMap<Character,Data.Mese>()
    														{{  put('A', Data.Mese.GENNAIO);
    															put('B', Data.Mese.FEBBRAIO);
    															put('C', Data.Mese.MARZO);
    															put('D', Data.Mese.APRILE);
    															put('E', Data.Mese.MAGGIO);
    															put('H', Data.Mese.GIUGNO);
    															put('L', Data.Mese.LUGLIO);
    															put('M', Data.Mese.AGOSTO);
    															put('P', Data.Mese.SETTEMBRE);
    															put('R', Data.Mese.OTTOBRE);
    															put('S', Data.Mese.NOVEMBRE);
    															put('T', Data.Mese.DICEMBRE);
    														}};
	public static final HashMap<Data.Mese,Character>MeseCarattere = new HashMap<Data.Mese,Character>()
	{{  put(Data.Mese.GENNAIO, 'A');
		put(Data.Mese.FEBBRAIO, 'B');
		put(Data.Mese.MARZO, 'C');
		put(Data.Mese.APRILE, 'D');
		put(Data.Mese.MAGGIO, 'E');
		put(Data.Mese.GIUGNO, 'H');
		put(Data.Mese.LUGLIO, 'L');
		put(Data.Mese.AGOSTO, 'M');
		put(Data.Mese.SETTEMBRE, 'P');
		put(Data.Mese.OTTOBRE, 'R');
		put(Data.Mese.NOVEMBRE, 'S');
		put(Data.Mese.DICEMBRE, 'T');
	}};
	private String codice;

	public CodiceFiscale(String codice) {
		this.codice = codice;
	}

	static boolean isValid(){
		boolean valido = true;

		return valido;
	}
	public String toString(){
		return codice.toString();
	}

	/**
     * Mappatura tra caratteri dispari e valori interi utilizzata
     * per il calcolo del carattere di controllo
     */
    public static final HashMap<Character,Integer>CaratteriDispari = new HashMap<Character,Integer>()
    														{{  put('0', 1);
    															put('1', 0);
    															put('2', 5);
    															put('3', 7);
    															put('4', 9);
    															put('5', 13);
    															put('6', 15);
    															put('7', 17);
    															put('8', 19);
    															put('9', 21);
    															put('A', 1);
    															put('B', 0);
    															put('C', 5);
    															put('D', 7);
    															put('E', 9);
    															put('F', 13);
    															put('G', 15);
    															put('H', 17);
    															put('I', 19);
    															put('J', 21);
    															put('K', 2);
    															put('L', 4);
    															put('M', 18);
    															put('N', 20);
    															put('O', 11);
    															put('P', 3);
    															put('Q', 6);
    															put('R', 8);
    															put('S', 12);
    															put('T', 14);
    															put('U', 16);
    															put('V', 10);
    															put('W', 22);
    															put('X', 25);
    															put('Y', 24);
    															put('Z', 23);
    														}};
    														
    /**
    * Mappatura tra caratteri pari e valori interi utilizzata
    * per il calcolo del carattere di controllo
    */
    public static final HashMap<Character,Integer>CaratteriPari = new HashMap<Character,Integer>()
    														{{  put('0', 0);
    															put('1', 1);
    															put('2', 2);
    															put('3', 3);
    															put('4', 4);
    															put('5', 5);
    															put('6', 6);
    															put('7', 7);
    															put('8', 8);
    															put('9', 9);
    															put('A', 0);
    															put('B', 1);
    															put('C', 2);
    															put('D', 3);
    															put('E', 4);
    															put('F', 5);
    															put('G', 6);
    															put('H', 7);
    															put('I', 8);
    															put('J', 9);
    															put('K', 10);
    															put('L', 11);
    															put('M', 12);
    															put('N', 13);
    															put('O', 14);
    															put('P', 15);
    															put('Q', 16);
    															put('R', 17);
    															put('S', 18);
    															put('T', 19);
    															put('U', 20);
    															put('V', 21);
    															put('W', 22);
    															put('X', 23);
    															put('Y', 24);
    															put('Z', 25);
    														}};
    														
    public static final HashMap<Integer,Character>RestoCaratteri = new HashMap<Integer,Character>()
    														{{  put(0, 'A');
    															put(1, 'B');
    															put(2, 'C');
    															put(3, 'D');
    															put(4, 'E');
    															put(5, 'F');
    															put(6, 'G');
    															put(7, 'H');
    															put(8, 'I');
    															put(9, 'J');
    															put(10, 'K');
    															put(11, 'L');
    															put(12, 'M');
    															put(13, 'N');
    															put(14, 'O');
    															put(15, 'P');
    															put(16, 'Q');
    															put(17, 'R');
    															put(18, 'S');
    															put(19, 'T');
    															put(20, 'U');
    															put(21, 'V');
    															put(22, 'W');
    															put(23, 'X');
    															put(24, 'Y');
    															put(25, 'Z');
    														}};
    
	/**
	 * Controlla se l'input è una lettera (A-Z)
	 * @param c il carattere da controllare
	 * @return true se il carattere è una lettera
	 */
	private static boolean isAlpha(char c) {
		if(LETTERE.indexOf(c)==-1)
			return false;
		else return true; // corrispondenza trovata quindi c è una lettera
	}
	
	/**
	 * Controlla se l'input è un numero
	 * @param c il carattere da controllare
	 * @return true se il carattere è un numero
	 */
	private static boolean isNum(char c) {
		if(NUMERI.indexOf(c)==-1)
			return false;
		else return true; //corrispondenza trovata quindi c è un numero
	}
	
	private static boolean controlloCognome(String cognome) {
		//la stringa deve essere di 3 caratteri
		if( !(isAlpha(cognome.charAt(0)) && isAlpha(cognome.charAt(1)) && isAlpha(cognome.charAt(2)) ) )
			return false;
		
		//aggiungere elementi per la validità del cognome
		return true;
	}
	
	private static boolean controlloNome(String nome) {
		//la stringa deve essere di 3 caratteri
		if( !(isAlpha(nome.charAt(0)) && isAlpha(nome.charAt(1)) && isAlpha(nome.charAt(2)) ) )
				return false;
		
		//aggiungere elementi pper la validità del nome
		return true;
	}
	
	private static boolean controlloAnno(String anno) {
		//la stringa deve essere di 2 numeri
		if( !(isNum(anno.charAt(0)) && isNum(anno.charAt(1))) )
			return false;
		else return true;
	}
	
	private static boolean controlloMese(char mese) {
		if(CarattereMese.containsKey(mese)) //se il carattere è tra quelli validi per il mese
			return true;
		else return false;
	}
	
	private static boolean controlloGiorno(String giorno) {
		//la stringa deve essere di 2 numeri
		if(!(isNum(giorno.charAt(0)) && isNum(giorno.charAt(1))) )
			return false;
		/**
		 * Il valore del giorno deve essere tra 01 e 71
		 * Non si fa distinzione tra uomini e donne (si fa solo per la generazione del CF)
		 */
		if(Integer.parseInt(giorno)<71) //in base ai controlli fatti, il giorno sarà sempre maggiore di 0
			return true;
		else return false;
	}
	
	private static boolean controlloComune(String comune) {
		//la string deve essere di 1 lettera e 3 numeri
		if( !(isAlpha(comune.charAt(0)) && isNum(comune.charAt(1)) && isNum(comune.charAt(2)) && isNum(comune.charAt(3)) ) )
			return false;

//		if(CFManager.CodiciComuni.containsKey(comune))
//			return true;
//		else return false;
		return true;
	}
	
	/**
	 * Genera il carattere di controllo dato il codice fiscale
	 * @param codice il codice fiscale SENZA il carattere di controllo
	 * @return il carattere di controllo
	 */
	public static char generaCarattereControllo(String codice) {
		if(codice.length() != 15) //il codice in input deve essere di 15 caratteri
			return '0'; //valurare se sollevare un'eccezione
		
		/*
		 * Sommare il valore dei caratteri del CF usado i mapping
		 * per i caratteri dispari e i caratteri pari
		 * ATTENZIONE: la posizione viene contata da 1
		 */
		int somma=0;
		for(int i=1; i < codice.length()+1; i++) {
			if(i%2 == 0) //pos pari
				somma += CaratteriPari.get(codice.charAt(i-1));
			else somma += CaratteriDispari.get(codice.charAt(i-1)); //pos dispari
		}
		
		return  RestoCaratteri.get(somma%26);
	}
	
	/**
	 * Controlla la validità di un codice fiscale in input
	 * @param codice il codice fiscale completo
	 * @return vero se il codice fiscale è valido
	 */
	public static boolean isValid(String codice){
        
		/*
		 *  FST  PLA  98   M  01    B157   E
		 *  
		 *  0-2  3-5  6-7  8  9-10  11-14  15
		 *  
		 */
		
        boolean cognome = controlloCognome(codice.substring(0, 3));
        boolean nome= controlloNome(codice.substring(3,6));
        boolean anno=controlloAnno(codice.substring(6,8));
        boolean mese=controlloMese(codice.charAt(8));
        boolean giorno=controlloGiorno(codice.substring(9,11));
        boolean comune=controlloComune(codice.substring(11,15));
        boolean controllo = codice.charAt(15) == generaCarattereControllo(codice.substring(0,15));
        
        
        return cognome && nome && anno && mese && giorno && comune && controllo;
    }

}
