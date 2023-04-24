import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;

public class CFManager {
    private final ArrayList<Persona> input_persone = new ArrayList<Persona>();
    public CFManager() {
    }

    public ArrayList<Persona> getInput_persone() {
        return input_persone;
    }


    FileInputStream file;
    XMLInputFactory input = XMLInputFactory.newInstance();
    XMLStreamReader reader;

    {//inizializzazione dello StreamReader per leggere il file InputPersone.xml
        try {
            file = new FileInputStream("fileXML/InputPersone.xml");
            reader = input.createXMLStreamReader(file);
            reader.next();
            leggi_nomi(reader);
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }
    }


    /**
     * Legge i dati delle persone da InputPersone.xml e li registra
     * in un ArrayList di Persone
     *
     * @param reader è lo strumento per il parsing inizializzato per
     *               leggere dal file InputPersone.xml
     */
    public void leggi_nomi(XMLStreamReader reader){
        while(true){//continua finché non viene lanciata un'eccezione quando sono finiti gli eventi dell'xml
            try {
                if (!reader.hasNext()) break;
                if(reader.getEventType() == XMLStreamConstants.START_ELEMENT && reader.getLocalName().equals("persona")) {
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
                    Data.Mese mese = Data.Mese.values()[Integer.parseInt(reader.getText().substring(5,7))-1];
                    int anno = Integer.parseInt(reader.getText().substring(0,4));
                    Data data = new Data(giorno, mese, anno);
                    input_persone.add(new Persona(nome,cognome,sesso,data,citta));
                }
                reader.next();
            } catch (XMLStreamException e) {
                throw new RuntimeException(e);
            }
        }

    }

    ArrayList<CodiceFiscale> cf_generati = new ArrayList<>();

    /**
     * Crea un codice fiscale per ogni persona della lista ricavata dall'input
     * e aggiunge i codici generati a un ArrayList di codici
     */
    public void genera_cf(){
        for(Persona persona : input_persone) {
            StringBuffer codice = new StringBuffer();
            codice.append(cognome_CF(persona.getCognome()));
            codice.append(nome_CF(persona.getNome()));
            cf_generati.add(new CodiceFiscale(codice.toString()));
        }
    }

    /**
     * Seleziona le tre lettere del cognome che andranno nel codice fiscale secondo
     * le linee guida
     * @param cognome il cognome della persona da cui ricavare le tre lettere
     * @return le tre lettere sotto forma di stringa
     */
    public String cognome_CF(String cognome){
        int cont = 0;
        String cognome_cf = "";
        for(int i=0; i<cognome.length(); i++){//aggiunge tutte le consonanti che trova in ordine
            if(cont==3)break;
            if(cognome.charAt(i) != 'A' && cognome.charAt(i) != 'E' && cognome.charAt(i) != 'I' && cognome.charAt(i) != 'O' && cognome.charAt(i) != 'U')
            {
                cognome_cf+=cognome.charAt(i);
                cont++;
            }
        }
        if(cont<3){//se finiscono le consonanti aggiunge tutte le vocali che trova in ordine
            for(int i=0; i<cognome.length(); i++){
                if(cont==3)break;
                if(cognome.charAt(i) == 'A' || cognome.charAt(i) == 'E' || cognome.charAt(i) == 'I' || cognome.charAt(i) == 'O' || cognome.charAt(i) == 'U')
                {
                    cognome_cf+=cognome.charAt(i);
                    cont++;
                }
            }
        }
        if(cont<3){//se finiscono anche le vocali aggiunge delle X per completare
            while(cont<3){
                cognome_cf+='X';
                cont++;
            }
        }
        return cognome_cf;
    }

    /**
     * Seleziona le tre lettere del nome che andranno nel codice fiscale secondo
     * le linee guida
     * @param nome il nome della persona da cui ricavare le tre lettere
     * @return le tre lettere sotto forma di stringa
     */
    public String nome_CF(String nome){
        int n_consonanti = 0;
        for(int i=0;i<nome.length();i++){//conta le consonanti del cognome visto che la scelta delle lettere sarà diversa in base a questo numero
            if(nome.charAt(i) != 'A' && nome.charAt(i) != 'E' && nome.charAt(i) != 'I' && nome.charAt(i) != 'O' && nome.charAt(i) != 'U')
                n_consonanti++;
        }
        String nome_cf = "";
        int cont = 0;
        if(n_consonanti>=4) {
            for (int i = 0; i < nome.length(); i++) {
                if (cont == 3) break;
                if (nome.charAt(i) != 'A' && nome.charAt(i) != 'E' && nome.charAt(i) != 'I' && nome.charAt(i) != 'O' && nome.charAt(i) != 'U') {
                    if (cont != 1) {//se le consonanti sono quattro o più si scelgono la prima, terza e quarta consonante, quindi saltiamo la seconda
                        nome_cf += nome.charAt(i);
                        cont++;
                    }
                }
            }
        }
        else{
            for (int i = 0; i < nome.length(); i++) {//aggiunge tutte le consonanti che trova in ordine
                if (cont == 3) break;
                if (nome.charAt(i) != 'A' && nome.charAt(i) != 'E' && nome.charAt(i) != 'I' && nome.charAt(i) != 'O' && nome.charAt(i) != 'U') {
                        nome_cf += nome.charAt(i);
                        cont++;
                }
            }
        }
        if(cont<3){
            for(int i=0; i<nome.length(); i++){//finite le consonanti aggiunge tutte le vocali che trova in ordine
                if(cont==3)break;
                if(nome.charAt(i) == 'A' || nome.charAt(i) == 'E' || nome.charAt(i) == 'I' || nome.charAt(i) == 'O' || nome.charAt(i) == 'U')
                {
                    nome_cf+=nome.charAt(i);
                    cont++;
                }
            }
        }
        if(cont<3){
            while(cont<3){//finite anche le vocali completa con delle X
                nome_cf+='X';
                cont++;
            }
        }
        return nome_cf;
    }
}
