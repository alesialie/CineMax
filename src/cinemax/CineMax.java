package cinemax;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        List<Proiezione> proiezioni = GestoreDati.caricaProiezioni("proiezioni.csv");
        GestoreProiezioni gestoreProiezioni = new GestoreProiezioni(proiezioni);

        // =====================================================================
        // MENU PRINCIPALE
        // =====================================================================
        Scanner scanner = new Scanner(System.in);
        boolean continua = true;

        while (continua) {
            System.out.println("=== CineMax ===");
            System.out.println("1. Login");
            System.out.println("2. Registrati");
            System.out.println("3. Visualizza tutte le proiezioni");
            System.out.println("4. Cerca proiezioni");
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
                case "3":
                    mostraLista(gestoreProiezioni.getProiezioni());
                    break;

                case "4":
                    menuRicerca(scanner, gestoreProiezioni);
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
     * menù per la scelta della ricerca
     */
    private static void menuRicerca(Scanner scanner, GestoreProiezioni gestore) {
        System.out.println("\n--- RICERCA PROIEZIONI ---");
        System.out.println("1. Per titolo");
        System.out.println("2. Per genere");
        System.out.println("3. Per fascia di prezzo");
        System.out.println("0. Torna indietro");
        System.out.print("Scelta criterio: ");

        String scelta = scanner.nextLine();

        switch (scelta) {
            case "1":
                System.out.print("Inserisci titolo: ");
                String titolo = scanner.nextLine();
                mostraLista(gestore.cercaTitolo(titolo));
                break;

            case "2":
                System.out.print("Inserisci genere: ");
                String genere = scanner.nextLine();
                mostraLista(gestore.cercaGenere(genere));
                break;

            case "3":
                try {
                    System.out.print("Prezzo minimo (€): ");
                    double min = Double.parseDouble(scanner.nextLine());
                    System.out.print("Prezzo massimo (€): ");
                    double max = Double.parseDouble(scanner.nextLine());
                    mostraLista(gestore.cercaCosto(min, max));
                } catch (NumberFormatException e) {
                    System.out.println("Errore: inserisci un valore numerico valido per il prezzo.");
                }
                break;

            case "0":
                break;

            default:
                System.out.println("Scelta non valida.");
        }
    }

    /**
     * stampa lista proiezioni
     * @param lista lista delle proiezioni mostrate
     */
    private static void mostraLista(List<Proiezione> lista) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("Nessuna proiezione trovata.");
            return;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("TITOLO\t\tGENERE\t\tDATA E ORA\t\tPREZZO");

        for (Proiezione p : lista) {
            System.out.println(p.getFilm().getTitolo() + "\t\t" +
                    p.getFilm().getGenere() + "\t\t" +
                    p.getDataOra().format(fmt) + "\t\t€" +
                    p.getCostoBiglietto());
        }
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