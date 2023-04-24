import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CFManager {
    private final ArrayList<Persona> lista_persone = new ArrayList<Persona>();
    public CFManager() {
    }

    public ArrayList<Persona> getLista_persone() {
        return lista_persone;
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
                    lista_persone.add(new Persona(nome,cognome,sesso,data,citta));
                }
                reader.next();
            } catch (XMLStreamException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
