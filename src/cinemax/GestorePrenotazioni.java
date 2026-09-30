package cinemax;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestorePrenotazioni {

    private final List<Prenotazione> prenotazioni;

    public GestorePrenotazioni() {
        this.prenotazioni = new ArrayList<>();
    }

    public GestorePrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = (prenotazioni != null) ? new ArrayList<>(prenotazioni) : new ArrayList<>();
    }

    public List<Prenotazione> getPrenotazioni() {
        return new ArrayList<>(prenotazioni);
    }

    // Gestione Prenotazioni

    public boolean effettuaPrenotazione(String codice, Utente cliente, Proiezione proiezione, int numeroPosti) {
        if (codice == null || codice.trim().isEmpty() || cliente == null || proiezione == null || numeroPosti <= 0) {
            return false;
        }

        // Il cliente deve avere il ruolo CLIENTE
        if (cliente.getRuolo() != Ruolo.CLIENTE) {
            return false;
        }

        // Non si può prenotare per proiezioni già passate
        if (proiezione.getDataOra().isBefore(LocalDateTime.now())) {
            return false;
        }

        // Verifica che il codice prenotazione non sia già stato usato
        if (cercaPerCodice(codice) != null) {
            return false;
        }

        // Verifica la disponibilità dei posti per la proiezione
        int postiDisponibili = getPostiDisponibili(proiezione);
        if (numeroPosti > postiDisponibili) {
            return false;
        }

        Prenotazione nuovaPrenotazione = new Prenotazione(codice, cliente, proiezione, numeroPosti);
        return prenotazioni.add(nuovaPrenotazione);
    }

    public boolean annullaPrenotazione(Utente utente, Prenotazione prenotazione) {
        if (utente == null || prenotazione == null || !prenotazioni.contains(prenotazione)) {
            return false;
        }

        // Può annullare il cliente che ha prenotato oppure un proiezionista
        boolean eProprietario = prenotazione.getCliente().equals(utente);
        boolean eProiezionista = utente.getRuolo() == Ruolo.PROIEZIONISTA;

        if (!eProprietario && !eProiezionista) {
            return false;
        }

        // Non si può annullare una proiezione già iniziata o passata
        if (prenotazione.getProiezione().getDataOra().isBefore(LocalDateTime.now())) {
            return false;
        }

        return prenotazioni.remove(prenotazione);
    }

    // Consultazione e Ricerca

    public Prenotazione cercaPerCodice(String codice) {
        if (codice == null) {
            return null;
        }
        for (Prenotazione p : prenotazioni) {
            if (p.getCodice().equalsIgnoreCase(codice)) {
                return p;
            }
        }
        return null;
    }

    public List<Prenotazione> getPrenotazioniPerUtente(Utente cliente) {
        List<Prenotazione> risultato = new ArrayList<>();
        if (cliente == null) {
            return risultato;
        }

        for (Prenotazione p : prenotazioni) {
            if (p.getCliente().equals(cliente)) {
                risultato.add(p);
            }
        }
        return risultato;
    }

    public List<Prenotazione> getPrenotazioniPerProiezione(Proiezione proiezione) {
        List<Prenotazione> risultato = new ArrayList<>();
        if (proiezione == null) {
            return risultato;
        }

        for (Prenotazione p : prenotazioni) {
            if (p.getProiezione().equals(proiezione)) {
                risultato.add(p);
            }
        }
        return risultato;
    }

    public boolean haPrenotazioni(Proiezione proiezione) {
        if (proiezione == null) {
            return false;
        }
        for (Prenotazione p : prenotazioni) {
            if (p.getProiezione().equals(proiezione)) {
                return true;
            }
        }
        return false;
    }

    public int getPostiOccupati(Proiezione proiezione) {
        if (proiezione == null) {
            return 0;
        }

        int totaleOccupati = 0;
        for (Prenotazione p : prenotazioni) {
            if (p.getProiezione().equals(proiezione)) {
                totaleOccupati += p.getNumeroPosti();
            }
        }
        return totaleOccupati;
    }

    public int getPostiDisponibili(Proiezione proiezione) {
        if (proiezione == null) {
            return 0;
        }
        return 0;
    }
}