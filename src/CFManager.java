import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CFManager {
	public static HashMap<String,String> ComuniSigla = new HashMap<String,String>();
	
	private static ArrayList<Persona> input_persone = new ArrayList<Persona>();
    

    public CFManager() {
    }

    public ArrayList<Persona> getInput_persone() {
        return input_persone;
    }


    


    /**
     * Legge i dati delle persone da InputPersone.xml e li registra
     * in un ArrayList di Persone
     *
     * @param reader è lo strumento per il parsing inizializzato per
     *               leggere dal file InputPersone.xml
     */
    public void leggi_nomi() {
    	FileInputStream file;
        XMLInputFactory input = XMLInputFactory.newInstance();
        XMLStreamReader reader=null;

        {//inizializzazione dello StreamReader per leggere il file InputPersone.xml
            try {
                file = new FileInputStream("fileXML/InputPersone.xml");
                reader = input.createXMLStreamReader(file);
                reader.next();
            } catch (Exception e) {
                System.out.println("Errore nell'inizializzazione del reader:");
                System.out.println(e.getMessage());
            }
        }
    	
    	while (true) {//continua finché non viene lanciata un'eccezione quando sono finiti gli eventi dell'xml
            try {
                if (!reader.hasNext()) break;
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT && reader.getLocalName().equals("persona")) {
                    reader.next();
                    reader.next();
                    reader.next();
                    String nome = reader.getText();
                    reader.next();
                    reader.next();
                    reader.next();
                    reader.next();
                    String cognome = reader.getText();
                    reader.next();
                    reader.next();
                    reader.next();
                    reader.next();
                    Persona.Sesso sesso = Persona.carattereSesso.get(reader.getText().charAt(0));
                    reader.next();
                    reader.next();
                    reader.next();
                    reader.next();
                    String citta = reader.getText();
                    reader.next();
                    reader.next();
                    reader.next();
                    reader.next();
                    int giorno = Integer.parseInt(reader.getText().substring(8));
                    Data.Mese mese = Data.Mese.values()[Integer.parseInt(reader.getText().substring(5, 7)) - 1];
                    int anno = Integer.parseInt(reader.getText().substring(0, 4));
                    Data data = new Data(giorno, mese, anno);
                    input_persone.add(new Persona(nome, cognome, sesso, data, citta));
                }
                reader.next();
            } catch (XMLStreamException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private final ArrayList<CodiceFiscale> cf_generati = new ArrayList<>();

    public ArrayList<CodiceFiscale> getCf_generati() {
        return cf_generati;
    }

    /**
     * Crea un codice fiscale per ogni persona della lista ricavata dall'input
     * e aggiunge i codici generati a un ArrayList di codici
     */
    public void genera_cf() {
        for (Persona persona : input_persone) {
            String codice = "";
            codice += cognome_CF(persona.getCognome());
            codice += nome_CF(persona.getNome());
            codice += data_CF(persona.getNascita(), persona.getSesso());
            codice += ComuniSigla.get(persona.getCitta());
            codice += CodiceFiscale.generaCarattereControllo((codice));
            cf_generati.add(new CodiceFiscale(codice));
        }
    }

    /**
     * Seleziona le tre lettere del cognome che andranno nel codice fiscale secondo
     * le linee guida
     *
     * @param cognome il cognome della persona da cui ricavare le tre lettere
     * @return le tre lettere sotto forma di stringa
     */
    public String cognome_CF(String cognome) {
        int cont = 0;
        String cognome_cf = "";
        for (int i = 0; i < cognome.length(); i++) {//aggiunge tutte le consonanti che trova in ordine
            if (cont == 3) break;
            if (cognome.charAt(i) != 'A' && cognome.charAt(i) != 'E' && cognome.charAt(i) != 'I' && cognome.charAt(i) != 'O' && cognome.charAt(i) != 'U') {
                cognome_cf += cognome.charAt(i);
                cont++;
            }
        }
        if (cont < 3) {//se finiscono le consonanti aggiunge tutte le vocali che trova in ordine
            for (int i = 0; i < cognome.length(); i++) {
                if (cont == 3) break;
                if (cognome.charAt(i) == 'A' || cognome.charAt(i) == 'E' || cognome.charAt(i) == 'I' || cognome.charAt(i) == 'O' || cognome.charAt(i) == 'U') {
                    cognome_cf += cognome.charAt(i);
                    cont++;
                }
            }
        }
        if (cont < 3) {//se finiscono anche le vocali aggiunge delle X per completare
            while (cont < 3) {
                cognome_cf += 'X';
                cont++;
            }
        }
        return cognome_cf;
    }

    /**
     * Seleziona le tre lettere del nome che andranno nel codice fiscale secondo
     * le linee guida
     *
     * @param nome il nome della persona da cui ricavare le tre lettere
     * @return le tre lettere sotto forma di stringa
     */
    public String nome_CF(String nome) {
        int n_consonanti = 0;
        for (int i = 0; i < nome.length(); i++) {//conta le consonanti del cognome visto che la scelta delle lettere sarà diversa in base a questo numero
            if (nome.charAt(i) != 'A' && nome.charAt(i) != 'E' && nome.charAt(i) != 'I' && nome.charAt(i) != 'O' && nome.charAt(i) != 'U')
                n_consonanti++;
        }
        String nome_cf = "";
        int cont = 0;
        int consonanti_aggiunte = 0;
        if (n_consonanti >= 4) {
            for (int i = 0; i < nome.length(); i++) {
                if (cont == 3) break;
                if (nome.charAt(i) != 'A' && nome.charAt(i) != 'E' && nome.charAt(i) != 'I' && nome.charAt(i) != 'O' && nome.charAt(i) != 'U') {
                       if(consonanti_aggiunte != 1) {
                           nome_cf += nome.charAt(i);
                           cont++;
                       }
                       consonanti_aggiunte++;
                }
            }
        } else {
            for (int i = 0; i < nome.length(); i++) {//aggiunge tutte le consonanti che trova in ordine
                if (cont == 3) break;
                if (nome.charAt(i) != 'A' && nome.charAt(i) != 'E' && nome.charAt(i) != 'I' && nome.charAt(i) != 'O' && nome.charAt(i) != 'U') {
                    nome_cf += nome.charAt(i);
                    cont++;
                }
            }
        }
        if (cont < 3) {
            for (int i = 0; i < nome.length(); i++) {//finite le consonanti aggiunge tutte le vocali che trova in ordine
                if (cont == 3) break;
                if (nome.charAt(i) == 'A' || nome.charAt(i) == 'E' || nome.charAt(i) == 'I' || nome.charAt(i) == 'O' || nome.charAt(i) == 'U') {
                    nome_cf += nome.charAt(i);
                    cont++;
                }
            }
        }
        if (cont < 3) {
            while (cont < 3) {//finite anche le vocali completa con delle X
                nome_cf += 'X';
                cont++;
            }
        }
        return nome_cf;
    }
    /*public int seconda_consonante(String nome){
        int  consonanti = 0;
        for(int i=0; i<nome.length(); i++){
            if (nome.charAt(i) != 'A' && nome.charAt(i) != 'E' && nome.charAt(i) != 'I' && nome.charAt(i) != 'O' && nome.charAt(i) != 'U')
                consonanti ++;
            if(consonanti==2)
                return i;
        }
        return -1;
    }*/
    /**
     * Seleziona le cifre del mese e del giorno e la lettera del mese
     * per il codice fiscale secondo le linee guida
     *
     * @param data la data di nascita della persona
     * @param sesso il sesso della persona
     * @return stringa con due cifre finali dell'anno, lettera del mese, due cifre del giorno
     */
    public String data_CF(Data data, Persona.Sesso sesso){
        String data_cf = "";
        int f = 0;
        data_cf+=(Integer.toString(data.getAnno()).substring(2));//servono solo le ultime due cifre dell'anno
        data_cf+=(CodiceFiscale.MeseCarattere.get(data.getMese()));
        int giorno = data.getGiorno();
        if(sesso.equals(Persona.Sesso.Femmina))giorno+=40;//se la persona è donna al numero del giorno va aggiunto 40
        if(giorno<10)//se la data è una sola cifra va preceduta da uno zero
            data_cf+="0"+(giorno);
        else data_cf+=(giorno);
        return data_cf;
    }

    /**
    * Legge i nomi dei comuni e le sigle a loro associate dal file Comuni.xml
    * e li associa tra loro
    */
    public static void leggiComuni() {
    	FileInputStream file;
        XMLInputFactory input = XMLInputFactory.newInstance();
        XMLStreamReader reader=null;
        
      //inizializzazione dello StreamReader per leggere il file Comuni.xml
        try {
            file = new FileInputStream("fileXML/Comuni.xml");
            reader = input.createXMLStreamReader(file);
            reader.next();
            //leggi_comuni_e_sigle(reader1);
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }
        
        while(true) {
            try {
                if (!reader.hasNext()) break;
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT && reader.getLocalName().equals("comune")) {
                    reader.next();
                    reader.next();
                    reader.next();
                    String comune = reader.getText();
                    reader.next();
                    reader.next();
                    reader.next();
                    reader.next();
                    String sigla = reader.getText();
                    ComuniSigla.put(comune, sigla);
                }
                reader.next();
            } catch (XMLStreamException e) {
                throw new RuntimeException(e);
            }
        }
    }


}

