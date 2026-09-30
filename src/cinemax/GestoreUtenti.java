package cinemax;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daria Alesia Ilie
 * **/

public class GestoreUtenti {

    private List<Utente> listaUtenti;

    // Costruttore: carica la lista degli utenti esistenti dal file CSV
    public GestoreUtenti() {
        this.listaUtenti = GestoreDati.caricaUtenti("utenti.csv");
        if (this.listaUtenti == null) {
            this.listaUtenti = new ArrayList<>();
        }
    }

    public void registra(String nome, String cognome, String username, String password, LocalDate dataNascita, String domicilio, Ruolo ruolo){

        if (nome == null || nome.isBlank() ||
                cognome == null || cognome.isBlank() ||
                username == null || username.isBlank() ||
                password == null || password.isBlank() ||
                dataNascita == null || ruolo == null) {

            throw new IllegalArgumentException("Tutti i campi obbligatori devono essere compilati.");
        }

        boolean usernameEsistente = listaUtenti.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));

        if (usernameEsistente) {
            throw new IllegalArgumentException("Username già esistente.");
        }

        LocalDate oggi = LocalDate.now();
        if (dataNascita.isAfter(oggi)) {
            throw new IllegalArgumentException("Data di nascita non valida.");
        }

        if (dataNascita.plusYears(18).isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("L'utente deve avere almeno 18 anni per registrarsi.");
        }

        String passwordCifrata = cifra(password);
        Utente nuovoUtente = new Utente(nome, cognome, username, passwordCifrata, dataNascita, domicilio, ruolo);

        listaUtenti.add(nuovoUtente);

        GestoreDati.salvaUtenti("utenti.csv", listaUtenti);
    }

    public static String cifra(String passwordInChiaro) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] impronta = digest.digest(passwordInChiaro.getBytes(StandardCharsets.UTF_8));
            StringBuilder esadecimale = new StringBuilder();
            for (byte b : impronta) {
                esadecimale.append(String.format("%02x", b));
            }
            return esadecimale.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo SHA-256 non disponibile", e);
        }
    }

    public List<Utente> getListaUtenti() {
        return listaUtenti;
    }

    public Ruolo login(){
        return null;
    }
}
