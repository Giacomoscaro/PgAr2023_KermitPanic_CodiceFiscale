import java.util.HashMap;

public class Data {
    public enum Mese{GENNAIO,FEBBRAIO,MARZO,APRILE,MAGGIO,GIUGNO,LUGLIO,AGOSTO,SETTEMBRE,OTTOBRE,NOVEMBRE,DICEMBRE}
  //  HashMap<Mese,Integer> mese_giorni = { {Mese.GENNAIO, 31},{Mese.FEBBRAIO,28},{Mese.MARZO,31},{Mese.APRILE,30},{Mese.MAGGIO,31},{Mese.GIUGNO,30},{Mese.LUGLIO,31},{Mese.AGOSTO,31},{Mese.SETTEMBRE,30},{Mese.OTTOBRE,31},{Mese.NOVEMBRE,30},{Mese.DICEMBRE,31}};
    private int giorno;

    private Mese mese;
    private int anno;

    public Data(int giorno, char mese, int anno){

    }
}
