import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import java.io.FileInputStream;
import java.util.Map;
import java.util.HashMap;


public class CodiceFiscale {
    
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

}
