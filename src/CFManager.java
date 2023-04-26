import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CFManager {
    public static HashMap<String,String> SiglaComuni = new HashMap<String,String>();
    private final ArrayList<Persona> input_persone = new ArrayList<Persona>();

    public CFManager() {
    }

    /**
     * Legge i dati delle persone da InputPersone.xml e li registra
     * in un ArrayList di Persone
     */
    public void leggi_persone() {
        FileInputStream file;
        XMLInputFactory input = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
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
            codice += SiglaComuni.get(persona.getCitta());
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
                    if (consonanti_aggiunte != 1) {
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

    /**
     * Seleziona le cifre del mese e del giorno e la lettera del mese
     * per il codice fiscale secondo le linee guida
     *
     * @param data  la data di nascita della persona
     * @param sesso il sesso della persona
     * @return stringa con due cifre finali dell'anno, lettera del mese, due cifre del giorno
     */
    public String data_CF(Data data, Persona.Sesso sesso) {
        String data_cf = "";
        data_cf += (Integer.toString(data.getAnno()).substring(2));//servono solo le ultime due cifre dell'anno
        data_cf += (CodiceFiscale.MeseCarattere.get(data.getMese()));
        int giorno = data.getGiorno();
        if (sesso.equals(Persona.Sesso.Femmina))
            giorno += 40;//se la persona è donna al numero del giorno va aggiunto 40
        if (giorno < 10)//se la data è una sola cifra va preceduta da uno zero
            data_cf += "0" + (giorno);
        else data_cf += (giorno);
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
                    SiglaComuni.put(comune, sigla);
                }
                reader.next();
            } catch (XMLStreamException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * Lista di codici fiscali presi dal file CodiciFiscali.xml
     */
    private final ArrayList<CodiceFiscale> codici_input = new ArrayList<>();

    FileInputStream file2;
    XMLInputFactory input2 = XMLInputFactory.newInstance();
    XMLStreamReader reader2;

    {//inizializzazione dello StreamReader per leggere il file CodiciFiscali.xml
        try {
            file2 = new FileInputStream("fileXML/CodiciFiscali.xml");
            reader2 = input2.createXMLStreamReader(file2);
            reader2.next();
            while (reader2.hasNext()) {
                if (reader2.getEventType() == XMLStreamConstants.START_ELEMENT && reader2.getLocalName().equals("codice")) {
                    reader2.next();
                    codici_input.add(new CodiceFiscale(reader2.getText()));
                }
                reader2.next();
            }
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }
    }

    private final ArrayList<CodiceFiscale> codici_invalidi = new ArrayList<>();
    private final ArrayList<CodiceFiscale> codici_spaiati = new ArrayList<>();

    /**
     * Ricevendo un codice come input controlla se questo corrisponde a uno dei codici
     * generati dal programma coi dati delle persone registrate
     * @param codice il codice da controllare
     * @return l'indice della lista di codici fiscali generati nel quale si trova un codice
     *         appaiato a quello da controllare
     */
    public int appaiato(CodiceFiscale codice) {
        for (int i = 0; i < cf_generati.size(); i++)
            if (codice.toString().equals(cf_generati.get(i).toString())) {
                return i;
            }
        return -1;
    }

    /**
     * Controlla ogni codice fiscale letto dal file xml e se è invalido lo aggiunge alla lista di quelli invalidi,
     * se non corrisponde a nessuno dei cf delle persone di cui sappiamo i dati lo aggiunge alla lista dei
     * cf spaiati, mentre se il cf risulta appaiato lo aggiunge all'oggetto Persona corrispondente
     */
    public void filtraggio_CF(){
        for(CodiceFiscale c : codici_input) {
            if(!CodiceFiscale.isValid(c.toString()))
                codici_invalidi.add(c);
            else if(appaiato(c)==-1)
                codici_spaiati.add(c);
            else input_persone.get(appaiato(c)).setCf(c);//l'indice restituito da appaiato() è sia l'indice del cf generato sia quello della persona inserita dall'xml nelle rispettive liste
        }
    }

    FileOutputStream risultato;
    XMLOutputFactory output = XMLOutputFactory.newInstance();
    XMLStreamWriter writer;
    {
        try{//inizializzazione del writer per generare l'xml definitivo
            risultato = new FileOutputStream("fileXML/codiciPersone.xml");
            writer = output.createXMLStreamWriter(risultato);
            writer.writeStartDocument("UTF-8", "1.0");
        }catch (Exception e) {
        System.out.println("Errore nell'inizializzazione del writer:");
        System.out.println(e.getMessage());
        }
    }

    /**
     * Usa il writer per scrivere tutti i dati delle persone con la grammatica xml
     * @param writer il writer per accedere al file
     * @throws XMLStreamException in caso di errore di scrittura
     */
    public void scrivi_persone(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement("persone");
        writer.writeAttribute("numero_persone", Integer.toString(input_persone.size()));
        for(Persona persona : input_persone){
            writer.writeStartElement("persona");
            writer.writeAttribute("id", Integer.toString(input_persone.indexOf(persona)));
            writer.writeStartElement("nome");
            writer.writeCharacters(persona.getNome());
            writer.writeEndElement();
            writer.writeStartElement("cognome");
            writer.writeCharacters(persona.getCognome());
            writer.writeEndElement();
            writer.writeStartElement("sesso");
            writer.writeCharacters(Character.toString(persona.getSesso().name().charAt(0)));
            writer.writeEndElement();
            writer.writeStartElement("comune_nascita");
            writer.writeCharacters(persona.getCitta());
            writer.writeEndElement();
            writer.writeStartElement("data_nascita");
            writer.writeCharacters(persona.getNascita().toString());
            writer.writeEndElement();
            writer.writeStartElement("codice_fiscale");
            writer.writeCharacters(persona.getCf().toString());
            writer.writeEndElement();
            writer.writeEndElement();
        }
    }

    /**
     * Usa il writer per scrivere una lista dei codici fiscali invalidi con la grammatica xml
     * @param writer il writer per accedere al file
     * @throws XMLStreamException in caso di errore di scrittura
     */
    public void scrivi_cf_invalidi(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement("invalidi");
        writer.writeAttribute("numero_cf_invalidi", Integer.toString(codici_invalidi.size()));
        for(CodiceFiscale codice : codici_invalidi){
            writer.writeStartElement("codice");
            writer.writeCharacters(codice.toString());
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }
    /**
     * Usa il writer per scrivere una lista dei codici fiscali spaiati con la grammatica xml
     * @param writer il writer per accedere al file
     * @throws XMLStreamException in caso di errore di scrittura
     */
    public void scrivi_cf_spaiati(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement("spaiati");
        writer.writeAttribute("numero_cf_spaiati", Integer.toString(codici_spaiati.size()));
        for(CodiceFiscale codice : codici_spaiati){
            writer.writeStartElement("codice");
            writer.writeCharacters(codice.toString());
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

    /**
     * Raccoglie gli altri metodi di scrittura dati e scrive il file xml definitivo
     * @param writer il writer per accedere al file
     * @throws XMLStreamException in caso di errore di scrittura
     */
    public void scrivi_Risultato(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement("output");
        scrivi_persone(writer);
        writer.writeStartElement("codici");
        scrivi_cf_invalidi(writer);
        scrivi_cf_spaiati(writer);
        writer.writeEndElement();
        writer.writeEndElement();
        writer.writeEndDocument();
        writer.flush();
        writer.close();
    }
}

