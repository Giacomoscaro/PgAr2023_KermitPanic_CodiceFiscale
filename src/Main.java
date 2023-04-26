import javax.xml.stream.XMLStreamException;

public class Main {
    public static void main(String[] args) throws XMLStreamException {
        // test della classe data
        Data d = new Data(5,Data.Mese.APRILE,2020);
        System.out.println(d.toString());
        CFManager c = new CFManager();
        c.genera_cf();
        /*for (int i=0; i<100; i++) {
            System.out.println(c.getCf_generati().get(i).toString());
            if(CodiceFiscale.isValid(c.getCf_generati().get(i).toString()))
                System.out.println("VALIDO");
            else System.out.println("NON VALIDO");
        }
        for ( int i = 0 ; i < 10 ; i++ ) {
            System.out.println(c.getCodici_input().get(i).toString());
        }*/
        c.filtraggio_CF();
        c.scrivi_Risultato(c.writer);
    }
    
   

}