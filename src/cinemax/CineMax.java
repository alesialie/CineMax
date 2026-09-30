package cinemax;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principale dell'applicazione CineMax. Contiene il metodo main
 * e il menu iniziale da cui si accede come guest, ci si registra o si
 * effettua il login.
 *
 * @author Daria Alesia Ilie
 */
public class CineMax {

    /**
     * Punto di ingresso dell'applicazione.
     *
     * @param args argomenti da riga di comando (non usati)
     */
    public static void main(String[] args) {

        // =====================================================================
        // PROVA / TEST AUTOMATICO DI PERSISTENZA ALL'AVVIO
        // =====================================================================
        System.out.println("=== ESECUZIONE TEST PERSISTENZA AUTOMATICO ===");

        // 1. Test Gestore Utenti (salvataggio automatico in utenti.csv)
        GestoreUtenti gestoreUtenti = new GestoreUtenti();
        try {
            gestoreUtenti.registra(
                    "Daria", "Ilie", "daria.ilie", "password032",
                    LocalDate.of(2005, 2, 26), "Fagnano", Ruolo.BIGLIETTAIO
            );
            System.out.println("[TEST] Utente 'daria.ilie' registrato e salvato correttamente!");
        } catch (IllegalArgumentException e) {
            System.out.println("[TEST INFO] Utente non registrato (potrebbe esistere già): " + e.getMessage());
        }

        // 2. Test Gestore Proiezioni (caricamento e salvataggio in proiezioni.csv)
        List<Proiezione> proiezioniCaricate = GestoreDati.caricaProiezioni("proiezioni.csv");
        GestoreProiezioni gestoreProiezioni = new GestoreProiezioni(proiezioniCaricate);

        Film filmProva = new Film("Inception", "Fantascienza", "Christopher Nolan", 2010, 148, 12);
        Proiezione proiezioneProva = new Proiezione(filmProva, LocalDateTime.of(2026, 10, 15, 20, 30), 8.50);

        if (gestoreProiezioni.aggiungiProiezione(proiezioneProva)) {
            gestoreProiezioni.salvaDati();
            System.out.println("[TEST] Proiezione di 'Inception' aggiunta e salvata correttamente!");
        } else {
            System.out.println("[TEST INFO] Proiezione non aggiunta (sovrapposizione o già presente).");
        }
        System.out.println("==============================================\n");

        // =====================================================================
        // MENU PRINCIPALE
        // =====================================================================
        Scanner scanner = new Scanner(System.in);
        boolean continua = true;

        while (continua) {
            System.out.println("=== CineMax ===");
            System.out.println("1. Login");
            System.out.println("2. Registrati");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    System.out.println("Login non ancora implementato.");
                    break;
                case "2":
                    System.out.println("Registrazione non ancora implementata.");
                    break;
                case "0":
                    continua = false;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }

        scanner.close();
        System.out.println("Arrivederci!");
    }
}