package cinemax;

import java.time.LocalDate;

/**
 * Rappresenta un utente registrato sulla piattaforma CineMax: puo essere
 * un cliente, un proiezionista o un bigliettaio, a seconda del {@link Ruolo}.
 *
 * @author Daria Alesia Ilie
 * */
public class Utente {

    private String nome;
    private String cognome;
    private String username;
    private String passwordCifrata;
    private LocalDate dataNascita; // facoltativa, puo essere null
    private String domicilio;
    private Ruolo ruolo;

    /**
     * Crea un nuovo utente.
     *
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param username username univoco usato per il login
     * @param passwordCifrata password gia cifrata (mai in chiaro)
     * @param dataNascita data di nascita, puo essere null se non fornita
     * @param domicilio luogo di domicilio dell'utente
     * @param ruolo ruolo dell'utente nel sistema
     */
    public Utente(String nome, String cognome, String username, String passwordCifrata,
                  LocalDate dataNascita, String domicilio, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.passwordCifrata = passwordCifrata;
        this.dataNascita = dataNascita;
        this.domicilio = domicilio;
        this.ruolo = ruolo;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordCifrata() {
        return passwordCifrata;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    @Override
    public String toString() {
        return nome + " " + cognome + " (" + username + ") - " + ruolo;
    }
}
