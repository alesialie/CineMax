package cinemax;

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
        Scanner scanner = new Scanner(System.in);
        boolean continua = true;

        // TODO: qui andranno caricati i dati da file (proiezioni, utenti, prenotazioni)
        // usando le classi di gestione (es. GestoreProiezioni, GestoreUtenti, GestorePrenotazioni)
        // ancora da scrivere.

        while (continua) {
            System.out.println("=== CineMax ===");
            System.out.println("1. Login");
            System.out.println("2. Registrati");
            System.out.println("3. Continua come guest");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    // TODO: gestire il login
                    System.out.println("Login non ancora implementato.");
                    break;
                case "2":
                    // TODO: chiamare registraCliente()
                    System.out.println("Registrazione non ancora implementata.");
                    break;
                case "3":
                    // TODO: chiedere il titolo (anche parziale) e richiamare cercaProiezione()
                    System.out.println("Modalita guest non ancora implementata.");
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