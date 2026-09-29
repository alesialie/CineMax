
package cinemax;

import java.time.LocalDateTime;

/**
 * Rappresenta una proiezione di un {@link Film} in una data e ora precise,
 * con un costo del biglietto associato.
 *
 * @author Ranya El Kachtaoui
 */
public class Proiezione {

    /** Capienza fissa della sala, come da specifiche del progetto. */
    public static final int CAPIENZA_SALA = 200;

    private Film film;
    private LocalDateTime dataOra;
    private double costoBiglietto;

    /**
     * Crea una nuova proiezione.
     *
     * @param film film che verra proiettato
     * @param dataOra data e ora della proiezione
     * @param costoBiglietto costo del biglietto in euro
     */
    public Proiezione(Film film, LocalDateTime dataOra, double costoBiglietto) {
        this.film = film;
        this.dataOra = dataOra;
        this.costoBiglietto = costoBiglietto;
    }

    public Film getFilm() {
        return film;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public double getCostoBiglietto() {
        return costoBiglietto;
    }

    public void setDataOra(LocalDateTime dataOra) {
        this.dataOra = dataOra;
    }

    /**
     * Controlla se questa proiezione si sovrappone, in termini di orario,
     * a un'altra proiezione, tenendo conto della durata del film.
     *
     * @param altra l'altra proiezione con cui verificare la sovrapposizione
     * @return true se le due proiezioni si sovrappongono nel tempo
     */
    public boolean siSovrappone(Proiezione altra) {
        LocalDateTime inizioA = this.dataOra;
        LocalDateTime fineA = this.dataOra.plusMinutes(this.film.getDurataMinuti());
        LocalDateTime inizioB = altra.dataOra;
        LocalDateTime fineB = altra.dataOra.plusMinutes(altra.film.getDurataMinuti());

        return inizioA.isBefore(fineB) && inizioB.isBefore(fineA);
    }

    @Override
    public String toString() {
        return film.getTitolo() + " - " + dataOra + " - " + costoBiglietto + " EUR";
    }
}
