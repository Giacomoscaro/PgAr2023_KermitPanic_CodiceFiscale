import java.util.HashMap;

public class Persona {
    public enum Sesso{Maschio,Femmina}
    public static final HashMap<Character,Persona.Sesso> carattereSesso = new HashMap<Character,Persona.Sesso>()
    {{
        put('M', Persona.Sesso.Maschio);
        put('F', Persona.Sesso.Femmina);
    }};
    private final String nome;
    private final String cognome;
    private final Sesso sesso;
    private final Data nascita;
    private final String citta;
    private CodiceFiscale cf;

    public Persona(String nome, String cognome, Sesso sesso, Data nascita, String citta) {
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.nascita = nascita;
        this.citta = citta;
        this.cf = new CodiceFiscale("ASSENTE");
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Sesso getSesso() {
        return sesso;
    }

    public Data getNascita() {
        return nascita;
    }

    public String getCitta() {
        return citta;
    }

    public CodiceFiscale getCf() {
        return cf;
    }

    public void setCf(CodiceFiscale cf) {
        this.cf = cf;
    }

    public String toString(){
        StringBuffer persona = new StringBuffer();
        persona.append("\nNome:\t" + nome).append("\nCognome:\t" + cognome).append("\nSesso:\t" + sesso).append("\nData di nascita:\t" + nascita.toString()).append("\nCittà:\t" + citta);
        return persona.toString();
    }
}
