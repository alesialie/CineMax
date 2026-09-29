package cinemax;

import java.time.LocalDate;

public class GestoreUtenti {

    public void registra(String nome, String cognome, String username, String password, LocalDate dataNascita, String domicilio, Ruolo ruolo){

        if (nome == null || nome.isBlank() ||
                cognome == null || cognome.isBlank() ||
                username == null || username.isBlank() ||
                password == null || password.isBlank() ||
                dataNascita == null || ruolo == null) {

            throw new IllegalArgumentException("Tutti i campi obbligatori devono essere compilati.");
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

    }

    public Ruolo login(){
        return null;
    }
}
