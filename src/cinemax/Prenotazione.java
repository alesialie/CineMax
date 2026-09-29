
package cinemax;

/**
 * Rappresenta la prenotazione di uno o piu posti per una {@link Proiezione}
 * da parte di un {@link Utente} con ruolo {@link Ruolo#CLIENTE}.
 *
 * @author Ranya El Kachtaoui
 */
public class Prenotazione {

    private String codice;
    private Utente cliente;
    private Proiezione proiezione;
    private int numeroPosti;

    /**
     * Crea una nuova prenotazione.
     *
     * @param codice codice univoco della prenotazione
     * @param cliente cliente che ha effettuato la prenotazione
     * @param proiezione proiezione per cui si prenota
     * @param numeroPosti numero di posti richiesti
     */
    public Prenotazione(String codice, Utente cliente, Proiezione proiezione, int numeroPosti) {
        this.codice = codice;
        this.cliente = cliente;
        this.proiezione = proiezione;
        this.numeroPosti = numeroPosti;
    }

    public String getCodice() {
        return codice;
    }

    public Utente getCliente() {
        return cliente;
    }

    public Proiezione getProiezione() {
        return proiezione;
    }

    public int getNumeroPosti() {
        return numeroPosti;
    }

    /**
     * Calcola il costo totale della prenotazione.
     *
     * @return costo totale in euro (costo unitario per numero di posti)
     */
    public double getCostoTotale() {
        return proiezione.getCostoBiglietto() * numeroPosti;
    }

    @Override
    public String toString() {
        return codice + " - " + cliente.getCognome() + " - " + proiezione + " - " + numeroPosti + " posti";
    }
}
