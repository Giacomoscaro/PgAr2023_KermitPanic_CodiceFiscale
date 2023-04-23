import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;

public class Data {
    /**
     * Enum del mese con valori interi corrispondenti
     */
	public enum Mese{GENNAIO(1),FEBBRAIO(2),MARZO(3),APRILE(4),MAGGIO(5),GIUGNO(6),LUGLIO(7),AGOSTO(8),SETTEMBRE(9),OTTOBRE(10),NOVEMBRE(11),DICEMBRE(12);
    		private int indice;
    		private Mese(int indice) {this.indice=indice;} // il costruttore degli enum è sempre private
    		public int getIndice() {return this.indice;} // per restituire il valore numerico di un mese
    }
    
    /**
     * HashMap che conserva coppie di valori Mese - giorni
     */
    public static final HashMap<Mese,Integer> mese_giorni = new HashMap<Mese,Integer>()
    /* Per inizializzarlo si è costretti a usare l'inizializzazione a doppie graffe
    * (il metodo Map.entry() non è definito per gli HashMap
    */														
    {{ 	put(Mese.GENNAIO, 31);
    	put(Mese.FEBBRAIO,28);
    	put(Mese.MARZO,31);
    	put(Mese.APRILE,30);
    	put(Mese.MAGGIO,31);
    	put(Mese.GIUGNO,30);
    	put(Mese.LUGLIO,31);
    	put(Mese.AGOSTO,31);
    	put(Mese.SETTEMBRE,30);
    	put(Mese.OTTOBRE,31);
    	put(Mese.NOVEMBRE,30);
    	put(Mese.DICEMBRE,31);
    }};
    														
    
    private int giorno;

    private Mese mese;
    private int anno;

    /**
     * Costruisce una data usando come mese il carattere
     * utilizzanto nel codice fiscale
     * @param giorno
     * @param mese carattere del mese secondo la codifica nel codice fiscale
     * @param anno
     */
    public Data(int giorno, char mese, int anno){
    		this.giorno=giorno;
    		this.anno=anno;
    		this.mese = CodiceFiscale.CarattereMese.get(mese);
    }
    
    /**
     * Ritorna una stringa con la data in formato GG/MM/AAAA
     */
    public String toString() {
    	return String.format("%02d/%02d/%4d", giorno, mese.getIndice(), anno);
    }
}
