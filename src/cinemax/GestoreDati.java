package cinemax;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestisce la lettura e la scrittura dei dati su file CSV nella cartella "data",
 * utilizzando la virgola come separatore dei campi.
 *
 * @author Sara Parenzan
 */
public class GestoreDati {

    private static final String DIRECTORY_DATI = "data/";

    /**
     * Salva la lista degli utenti su un file CSV specificato ("utenti.csv").
     *
     * @param nomeFile nome del file (es. "utenti.csv")
     * @param utenti lista degli utenti da salvare
     */
    public static void salvaUtenti(String nomeFile, List<Utente> utenti) {
        String percorso = DIRECTORY_DATI + nomeFile;
        try (PrintWriter pw = new PrintWriter(new FileWriter(percorso))) {
            for (Utente u : utenti) {
                // Unisce i campi della classe Utente usando la virgola
                String riga = String.join(",",
                        gestisciVirgole(u.getNome()),
                        gestisciVirgole(u.getCognome()),
                        gestisciVirgole(u.getUsername()),
                        gestisciVirgole(u.getPasswordCifrata()),
                        (u.getDataNascita() != null ? u.getDataNascita().toString() : ""),
                        gestisciVirgole(u.getDomicilio()),
                        u.getRuolo().name()
                );
                pw.println(riga);
            }
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio del file " + nomeFile + ": " + e.getMessage());
        }
    }

    /**
     * Carica gli utenti da un file CSV specificato (es. "utenti.csv").
     *
     * @param nomeFile nome del file da leggere
     * @return una lista di oggetti Utente caricati dal file
     */
    public static List<Utente> caricaUtenti(String nomeFile) {
        List<Utente> utenti = new ArrayList<>();
        String percorso = DIRECTORY_DATI + nomeFile;

        try (BufferedReader br = new BufferedReader(new FileReader(percorso))) {
            String riga;
            while ((riga = br.readLine()) != null) {
                if (riga.trim().isEmpty()) {
                    continue;
                }

                // Divide la riga usando la virgola come separatore (-1 mantiene i campi vuoti)
                String[] parti = riga.split(",", -1);
                if (parti.length >= 7) {
                    String nome = parti[0];
                    String cognome = parti[1];
                    String username = parti[2];
                    String passwordCifrata = parti[3];
                    LocalDate dataNascita = parti[4].isEmpty() ? null : LocalDate.parse(parti[4]);
                    String domicilio = parti[5];
                    Ruolo ruolo = Ruolo.valueOf(parti[6]);

                    // Ricostruisce l'oggetto Utente con il costruttore originale
                    Utente u = new Utente(nome, cognome, username, passwordCifrata, dataNascita, domicilio, ruolo);
                    utenti.add(u);
                }
            }
        } catch (IOException e) {
            // Se il file non esiste ancora, restituisce la lista vuota
        }
        return utenti;
    }

    /**
     * Metodo di supporto per evitare che eventuali virgole all'interno
     * di stringhe di testo spezzino il formato CSV (racchiude tra virgolette se necessario).
     */
    private static String gestisciVirgole(String testo) {
        if (testo == null) {
            return "";
        }
        if (testo.contains(",")) {
            return "\"" + testo + "\"";
        }
        return testo;
    }
}