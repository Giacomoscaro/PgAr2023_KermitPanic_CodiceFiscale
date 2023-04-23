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
	
	String codice;
    static boolean isValid(){
        boolean valido = true;

        return valido;
    }

}
