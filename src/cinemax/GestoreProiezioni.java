package cinemax;

import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**@author Elisabetta Della Moretta**/

/** gestione dati fatta in un altro file**/

public class GestoreProiezioni {
    public static final int CAPIENZA_SALA = 200;
    private List<Proiezione> proiezioni;

    public GestoreProiezioni(List<Proiezione> proiezioni) {
        this.proiezioni = (proiezioni != null) ? proiezioni : new ArrayList<>();
    }

    /**
     *
     * @param Titolo titolo del film
     * @return lista proiezioni trovate
     */

    /**
     * ricerca delle proiezioni tramite titolo**/
    public List<Proiezione> cercaTitolo(String Titolo) {
        List<Proiezione> risultato = new ArrayList<>();

        if (Titolo == null || Titolo.isEmpty())
            return proiezioni;


        for(Proiezione p : proiezioni){
            if(p.getFilm() != null && p.getFilm().titoloContiene(Titolo)){
                risultato.add(p);
            }
        }
        return risultato;
    }

    /**
     * ricerca film per genere
     * @param Genere genere del film
     * @return lista proiezini trovate
     * **/
    public List<Proiezione> cercaGenere(String Genere) {
        List<Proiezione> risultato = new ArrayList<>();

        if (Genere == null || Genere.isEmpty())
            return proiezioni;

        for (Proiezione p : proiezioni) {
            if (p.getFilm() != null && p.getFilm().getGenere().equalsIgnoreCase(Genere)) {
                risultato.add(p);
            }
        }
        return risultato;
    }

    /**
     * ricerca per data
     * @param inizio Limite inferiore per l'intervallo di date.
     * @param fine Limite superiore per l'intervallo di date.
     * @return lista proiezioni trovate
     * **/
    public List<Proiezione> cercaData(LocalDateTime inizio, LocalDateTime fine) {
        List<Proiezione> risultato = new ArrayList<>();

        for (Proiezione p : proiezioni) {
            boolean b = true;

            if (inizio != null && p.getDataOra().isBefore(inizio)) {
                b = false;
            }
            if (fine != null && p.getDataOra().isAfter(fine)) {
                b = false;
            }
            if (b) {
                risultato.add(p);
            }
        }
        return risultato;
    }

    /**
     * ricerca per costo
     * @param costoMinimo Costo minimo del biglietto.
     * @param costoMassimo Costo massimo del biglietto
     * @return lista proiezioni trovate
     */
    public List<Proiezione> cercaCosto(double costoMinimo, double costoMassimo) {
        List<Proiezione> risultato = new ArrayList<>();

        for (Proiezione p : proiezioni) {
            double costo = p.getCostoBiglietto();
            if (costo >= costoMinimo && costo <= costoMassimo) {
                risultato.add(p);
            }
        }
        return risultato;
    }

    /**
     * Ricerca combinata dei precedenti
     *
     * @param Titolo titolo del film.
     * @param Genere Genere del film.
     * @param inizio Limite inferiore per l'intervallo di date.
     * @param fine Limite superiore per l'intervallo di date.
     * @param costoMinimo Costo minimo del biglietto.
     * @param costoMassimo Costo massimo del biglietto
     * @return lista proiezioni trovate
     */

    public List<Proiezione> cercaAvanzata(String Titolo, String Genere,
                                          LocalDateTime inizio, LocalDateTime fine,
                                          double costoMinimo, double costoMassimo) {
        List<Proiezione> risultato = new ArrayList<>();

        for (Proiezione p : proiezioni) {
            boolean b = true;

            if (Titolo != null && !Titolo.isEmpty()) {
                if (p.getFilm() == null || !p.getFilm().titoloContiene(Titolo)) {
                    b = false;
                }
            }

            if (Genere != null && !Genere.isEmpty()) {
                if (p.getFilm() == null || !p.getFilm().getGenere().equalsIgnoreCase(Genere)) {
                    b = false;
                }
            }

            if (inizio != null && p.getDataOra().isBefore(inizio)) {
                b = false;
            }

            if (fine != null && p.getDataOra().isAfter(fine)) {
                b = false;
            }

            if (costoMassimo > 0) {
                double costo = p.getCostoBiglietto();
                if (costo < costoMinimo || costo > costoMassimo) {
                    b = false;
                }
            }

            if (b) {
                risultato.add(p);
            }
        }
        return risultato;
    }

    /** aggiunta proiezione
     * @param utente Corrente utente che prova a fare l'operazione
     * @param nuovaProiezione Proiezione da aggiungere.
     * @return true se aggiunta con successo
     **/
    public boolean aggiungiProiezione(Utente utente, Proiezione nuovaProiezione) {

        if(utente == null || utente.getRuolo() != Ruolo.PROIEZIONISTA){
            return false;
        }

        if (nuovaProiezione == null) {
            return false;
        }
        for (Proiezione p : proiezioni) {
        if (p.siSovrappone(nuovaProiezione)) {
                return false;
            }
        }
        return proiezioni.add(nuovaProiezione);
    }

    /**
     * Modifica la data/ora di una proiezione esistente
     * @param utente Corrente utente che prova a fare l'operazione
     * @param proiezioneDaModificare Proiezione da aggiornare.
     * @param nuovaDataOra  Nuova data e ora.
     * @return true se modificata con successo, false altrimenti.
     */

    public boolean modificaProiezione(Utente utente, Proiezione proiezioneDaModificare, LocalDateTime nuovaDataOra) {

        if(utente == null || utente.getRuolo() != Ruolo.PROIEZIONISTA){
            return false;
        }

        if (proiezioneDaModificare == null || nuovaDataOra == null) {
            return false;
        }

        if (!proiezioni.contains(proiezioneDaModificare)) {
            return false;
        }

        Proiezione temp = new Proiezione(proiezioneDaModificare.getFilm(), nuovaDataOra, proiezioneDaModificare.getCostoBiglietto());

        for (Proiezione p : proiezioni) {
            if (!p.equals(proiezioneDaModificare)) {
                if (p.siSovrappone(temp)) {
                    return false;
                }
            }
        }
        proiezioneDaModificare.setDataOra(nuovaDataOra);
        return true;
    }
    /**
     * Rimuove una proiezione
     * @param utente Corrente utente che prova a fare l'operazione
     * @param proiezione Proiezione da eliminare.
     * @return true se trovata ed eliminata, false altrimenti.
     */
    public boolean eliminaProiezione(Utente utente, Proiezione proiezione) {

        if(utente == null || utente.getRuolo() != Ruolo.PROIEZIONISTA){
            return false;
        }

        if (proiezione == null) {
            return false;
        }
        return proiezioni.remove(proiezione);
    }
    /**
     * Salva la lista delle proiezioni richiamando GestoreDati
     */
    public void salvaDati() {
        GestoreDati.salvaProiezioni("proiezioni.csv", this.proiezioni);
    }
}