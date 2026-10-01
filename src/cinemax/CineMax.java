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

    /**
     * Metodo di supporto per cercare il primo utente con ruolo CLIENTE nella lista.
     */
    private static Utente trovaPrimoCliente(List<Utente> utenti) {
        for (Utente u : utenti) {
            if (u.getRuolo() == Ruolo.CLIENTE) {
                return u;
            }
        }
        return null;
    }
}