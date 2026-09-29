package cinemax;

/**
 * Rappresenta un film, con le informazioni richieste dal progetto CineMax:
 * titolo, genere, regista, anno, durata ed eta minima consigliata.
 *
 * @author Sara Parenzan
 */
public class Film {

    private String titolo;
    private String genere;
    private String regista;
    private int anno;
    private int durataMinuti;
    private int etaMinima;

    /**
     * Crea un nuovo film con tutti i suoi dati.
     *
     * @param titolo titolo del film
     * @param genere genere del film (es. "Commedia", "Azione")
     * @param regista nome del regista
     * @param anno anno di uscita
     * @param durataMinuti durata in minuti
     * @param etaMinima eta minima consigliata per il pubblico
     */
    public Film(String titolo, String genere, String regista, int anno, int durataMinuti, int etaMinima) {
        this.titolo = titolo;
        this.genere = genere;
        this.regista = regista;
        this.anno = anno;
        this.durataMinuti = durataMinuti;
        this.etaMinima = etaMinima;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getGenere() {
        return genere;
    }

    public String getRegista() {
        return regista;
    }

    public int getAnno() {
        return anno;
    }

    public int getDurataMinuti() {
        return durataMinuti;
    }

    public int getEtaMinima() {
        return etaMinima;
    }

    /**
     * Controlla se il titolo del film contiene (anche parzialmente,
     * senza distinzione tra maiuscole e minuscole) il testo indicato.
     *
     * @param testo testo da cercare nel titolo
     * @return true se il titolo contiene il testo indicato
     */
    public boolean titoloContiene(String testo) {
        if (testo == null) {
            return true;
        }
        return titolo.toLowerCase().contains(testo.toLowerCase());
    }

    @Override
    public String toString() {
        return titolo + " (" + anno + ") - " + genere + ", regia di " + regista;
    }
}
