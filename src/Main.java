public class Main {
    public static void main(String[] args) {
        // test della classe data
        Data d = new Data(5,Data.Mese.APRILE,2020);
        System.out.println(d.toString());
        CFManager c = new CFManager();
        c.leggi_nomi();
        c.leggiComuni();
        c.genera_cf();
        
        for (int i=0; i<100; i++) {
            System.out.println(c.getCf_generati().get(i).toString());
            if(CodiceFiscale.isValid(c.getCf_generati().get(i).toString()))
                System.out.println("VALIDO");
            else System.out.println("NON VALIDO");
        }

    }
    
   

}