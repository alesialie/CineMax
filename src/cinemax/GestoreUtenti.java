package cinemax;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

/**
 * Gestisce le operazioni legate agli utenti del sistema Cinemax,
 * inclusa la registrazione, l'autenticazione (login) e la gestione della lista utenti.
 *
 * @author Daria Alesia Ilie
 */

public class GestoreUtenti {

    private final List<Utente> listaUtenti;

    /**
     * Costruisce un nuovo oggetto GestoreUtenti caricando la lista degli utenti
     * esistenti dal file di persistenza "utenti.csv".
     */

    public GestoreUtenti() {
        this.listaUtenti = GestoreDati.caricaUtenti("utenti.csv");
    }

    /**
     * Registra un nuovo utente nel sistema.
     * <p>
     * Esegue le seguenti verifiche e operazioni:
     * <ul>
     *   <li>Valida la presenza di tutti i campi obbligatori.</li>
     *   <li>Verifica l'univocità dello username (case-insensitive).</li>
     *   <li>Controlla che la data di nascita non sia nel futuro.</li>
     *   <li>Verifica che l'utente sia maggiorenne (almeno 18 anni).</li>
     *   <li>Cifra la password prima di memorizzarla.</li>
     *   <li>Aggiunge il nuovo utente alla lista locale e aggiorna il file di persistenza.</li>
     * </ul>
     *
     * @param nome        Il nome dell'utente (obbligatorio).
     * @param cognome     Il cognome dell'utente (obbligatorio).
     * @param username    Lo username scelto dall'utente, deve essere unico (obbligatorio).
     * @param password    La password in chiaro dell'utente, verrà cifrata prima del salvataggio (obbligatoria).
     * @param dataNascita La data di nascita dell'utente, l'utente deve essere maggiorenne (obbligatoria).
     * @param domicilio   L'indirizzo di domicilio dell'utente (opzionale).
     * @param ruolo       Il ruolo assegnato all'utente nel sistema (obbligatorio).
     *
     * @throws IllegalArgumentException Se uno dei campi obbligatori è nullo o vuoto,
     *                                  se lo username esiste già, se la data di nascita è nel futuro
     *                                  o se l'utente ha meno di 18 anni.
     */

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

        if (dataNascita.plusYears(18).isAfter(oggi)) {
            throw new IllegalArgumentException("L'utente deve avere almeno 18 anni per registrarsi.");
        }

        String passwordCifrata = cifra(password);
        Utente nuovoUtente = new Utente(nome, cognome, username, passwordCifrata, dataNascita, domicilio, ruolo);

        listaUtenti.add(nuovoUtente);

        GestoreDati.salvaUtenti("utenti.csv", listaUtenti);
    }

    /**
     * Calcola l'impronta SHA-256 di una password e la restituisce come
     * stringa esadecimale minuscola.
     *
     * @param passwordInChiaro password da cifrare
     * @return impronta esadecimale di 64 caratteri
     * @throws IllegalStateException se l'algoritmo SHA-256 non e' disponibile
     */

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

    /*TODO verificare se il metodo serve o eliminarlo
     *

    public List<Utente> getListaUtenti() {
        return List.copyOf(listaUtenti);
    }
     */

    /**
     * Autentica un utente nel sistema verificando le credenziali fornite.
     *
     * @param username Lo username dell'utente da autenticare.
     * @param password La password in chiaro inserita dall'utente.
     * @return Il {@link Ruolo} dell'utente se l'autenticazione ha successo;
     *         {@code null} se le credenziali sono errate, incomplete o l'utente non esiste.
     */

    public Ruolo login(String username, String password) {

        if (username == null || username.isBlank() ||
                password == null || password.isBlank()) {
            return null;
        }

        String passwordCifrataInput = cifra(password);

        for (Utente u : listaUtenti) {

            if (u.getUsername().equalsIgnoreCase(username)) {

                if (u.getPasswordCifrata().equals(passwordCifrataInput)) {
                    return u.getRuolo();
                }

                return null;
            }
        }
        return null;
    }
}