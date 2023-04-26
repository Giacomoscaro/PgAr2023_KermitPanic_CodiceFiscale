import javax.xml.stream.XMLStreamException;

public class Main {
    public static void main(String[] args) throws XMLStreamException {
        CFManager c = new CFManager();
        CFManager.leggiComuni();
        c.leggi_persone();
        c.genera_cf();
        c.filtraggio_CF();
        c.scrivi_Risultato(c.writer);
    }
    
   

}