public class Main {
    public static void main(String[] args) {
        // test della classe data
        Data d = new Data(5,Data.Mese.APRILE,2020);
        System.out.println(d.toString());
        CFManager c = new CFManager();
        for (int i=0; i<10; i++)
            System.out.println(c.getInput_persone().get(i));
    }
    
   

}