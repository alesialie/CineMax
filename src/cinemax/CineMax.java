package cinemax;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

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
        GestoreUtenti gestoreUtenti = new GestoreUtenti();
        GestorePrenotazioni gestorePrenotazioni = new GestorePrenotazioni();

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
                    effettuaLogin(scanner, gestoreUtenti, gestoreProiezioni, gestorePrenotazioni);
                    break;
                case "2":
                    effettuaRegistrazione(scanner, gestoreUtenti);
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
     * Gestisce la procedura di Login e reindirizza l'utente al menù del suo ruolo
     * */

    private static void effettuaLogin(Scanner scanner, GestoreUtenti gestoreUtenti, GestoreProiezioni gestoreProiezioni, GestorePrenotazioni gestorePrenotazioni) {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        Utente utenteLoggato = gestoreUtenti.login(username, password);

        if (utenteLoggato == null) {
            System.out.println("Credenziali errate o utente non trovato.");
            return;
        }

        System.out.println("\nBenvenuto/a " + utenteLoggato.getNome() + " " + utenteLoggato.getCognome() + "!");

        // In base al ruolo indirizziamo l'utente al menu dedicato
        if (utenteLoggato.getRuolo() == Ruolo.CLIENTE) {
            menuCliente(scanner, utenteLoggato, gestoreProiezioni, gestorePrenotazioni);
        } else if (utenteLoggato.getRuolo() == Ruolo.BIGLIETTAIO) {
            // Se esiste un menu gestore, invocarlo qui
            System.out.println("Accesso come Bigliettaio.");
        } else if (utenteLoggato.getRuolo() == Ruolo.PROIEZIONISTA) {
        System.out.println("Accesso effettuato come Proiezionista.");
        }
    }

    /**
     * Gestisce l'inserimento dei dati e la registrazione di un nuovo cliente.
     */
    private static void effettuaRegistrazione(Scanner scanner, GestoreUtenti gestoreUtenti) {
        System.out.println("\n--- REGISTRAZIONE ---");

        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();

            System.out.print("Cognome: ");
            String cognome = scanner.nextLine().trim();

            System.out.print("Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();
            System.out.print("Data di nascita (GG/MM/AAAA): ");
            String dataStr = scanner.nextLine().trim();

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNascita = LocalDate.parse(dataStr, fmt);

            System.out.print("Domicilio (opzionale, premere INVIO per saltare): ");
            String domicilio = scanner.nextLine().trim();

            // Di default, le registrazioni da menu pubblico creano utenti CLIENTE
            gestoreUtenti.registra(nome, cognome, username, password, dataNascita, domicilio, Ruolo.CLIENTE);

            System.out.println("\nRegistrazione completata con successo! Ora puoi effettuare il login.");

        } catch (DateTimeParseException e) {
            System.out.println("\nErrore: Formato data non valido. Usare GG/MM/AAAA (es. 15/05/1995).");
        } catch (IllegalArgumentException e) {
            System.out.println("\nErrore nella registrazione: " + e.getMessage());
        }
    }

    /**
     * Menù per la scelta della ricerca
     */
    private static void menuRicerca(Scanner scanner, GestoreProiezioni gestore) {
        System.out.println("\n--- RICERCA PROIEZIONI ---");
        System.out.println("1. Per titolo");
        System.out.println("2. Per genere");
        System.out.println("3. Per fascia di prezzo");
        System.out.println("0. Torna indietro");
        System.out.print("Scelta criterio: ");

        String scelta = scanner.nextLine().trim();

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
     * Stampa lista proiezioni
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
    /**
     * Sottomenu riservato agli utenti registrati con ruolo CLIENTE.
     */
    private static void menuCliente(Scanner scanner, Utente cliente, GestoreProiezioni gestoreProiezioni, GestorePrenotazioni gestorePrenotazioni) {
        boolean inMenuCliente = true;

        while (inMenuCliente) {
            System.out.println("\n=== AREA CLIENTE ===");
            System.out.println("1. Nuova prenotazione");
            System.out.println("2. Prenotazioni attive");
            System.out.println("3. Annulla prenotazione");
            System.out.println("0. Logout");
            System.out.print("Scelta: ");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    nuovaPrenotazione(scanner, cliente, gestoreProiezioni, gestorePrenotazioni);
                    break;

                case "2":
                    mostraPrenotazioniCliente(cliente, gestorePrenotazioni);
                    break;

                case "3":
                    annullaPrenotazioneCliente(scanner, cliente, gestorePrenotazioni);
                    break;

                case "0":
                    inMenuCliente = false;
                    System.out.println("Logout effettuato.");
                    break;

                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }

    /**
     * Procedura per effettuare una nuova prenotazione.
     */
    private static void nuovaPrenotazione(Scanner scanner, Utente cliente, GestoreProiezioni gestoreProiezioni, GestorePrenotazioni gestorePrenotazioni) {
        List<Proiezione> proiezioni = gestoreProiezioni.getProiezioni();

        if (proiezioni == null || proiezioni.isEmpty()) {
            System.out.println("Nessuna proiezione disponibile al momento.");
            return;
        }

        System.out.println("\n--- NUOVA PRENOTAZIONE ---");
        System.out.println("Elenco proiezioni disponibili:\n");

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (int i = 0; i < proiezioni.size(); i++) {
            Proiezione p = proiezioni.get(i);
            int postiDisp = gestorePrenotazioni.getPostiDisponibili(p);
            System.out.println((i + 1) + ". " + p.getFilm().getTitolo() +
                    " | Data/Ora: " + p.getDataOra().format(fmt) +
                    " | Prezzo: €" + p.getCostoBiglietto() +
                    " | Posti disponibili: " + postiDisp);
        }

        try {
            System.out.print("\nInserisci il numero corrispondente alla proiezione (0 per annullare): ");
            int sceltaIndice = Integer.parseInt(scanner.nextLine());

            if (sceltaIndice == 0) {
                return;
            }

            if (sceltaIndice < 1 || sceltaIndice > proiezioni.size()) {
                System.out.println("Selezione non valida.");
                return;
            }

            Proiezione proiezioneScelta = proiezioni.get(sceltaIndice - 1);

            System.out.print("Inserisci il numero di posti da prenotare: ");
            int numeroPosti = Integer.parseInt(scanner.nextLine());

            // Generazione automatica di un codice univoco
            String codicePrenotazione = "PREN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            boolean esito = gestorePrenotazioni.effettuaPrenotazione(codicePrenotazione, cliente, proiezioneScelta, numeroPosti);

            if (esito) {
                gestorePrenotazioni.salvaPrenotazioni();
                System.out.println("\nPrenotazione completata con successo!");
                System.out.println("Codice Prenotazione: " + codicePrenotazione);
                System.out.println("Costo Totale: €" + (proiezioneScelta.getCostoBiglietto() * numeroPosti));
            } else {
                System.out.println("\nImpossibile effettuare la prenotazione. Verificare la disponibilità dei posti o l'orario della proiezione.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Errore: Inserire un valore numerico valido.");
        }
    }

    /**
     * Stampa le prenotazioni attive associate al cliente loggato.
     */
    private static void mostraPrenotazioniCliente(Utente cliente, GestorePrenotazioni gestorePrenotazioni) {
        List<Prenotazione> miePrenotazioni = gestorePrenotazioni.getPrenotazioniPerUtente(cliente);

        System.out.println("\n--- LE MIE PRENOTAZIONI ATTIVE ---");

        if (miePrenotazioni.isEmpty()) {
            System.out.println("Non hai nessuna prenotazione attiva.");
            return;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Prenotazione p : miePrenotazioni) {
            System.out.println("Codice: " + p.getCodice() +
                    " | Film: " + p.getProiezione().getFilm().getTitolo() +
                    " | Data/Ora: " + p.getProiezione().getDataOra().format(fmt) +
                    " | Posti: " + p.getNumeroPosti() +
                    " | Totale: €" + p.getCostoTotale());
        }
    }

    /**
     * Procedura per permettere al cliente di annullare una propria prenotazione esistente.
     */
    private static void annullaPrenotazioneCliente(Scanner scanner, Utente cliente, GestorePrenotazioni gestorePrenotazioni) {
        System.out.println("\n--- ANNULLA PRENOTAZIONE ---");
        mostraPrenotazioniCliente(cliente, gestorePrenotazioni);

        List<Prenotazione> miePrenotazioni = gestorePrenotazioni.getPrenotazioniPerUtente(cliente);
        if (miePrenotazioni.isEmpty()) {
            return;
        }

        System.out.print("\nInserisci il codice della prenotazione da annullare (INVIO per annullare): ");
        String codice = scanner.nextLine().trim();

        if (codice.isEmpty()) {
            return;
        }

        Prenotazione daAnnullare = gestorePrenotazioni.cercaPerCodice(codice);

        if (daAnnullare == null) {
            System.out.println("Nessuna prenotazione trovata con il codice specificato.");
            return;
        }

        boolean esito = gestorePrenotazioni.annullaPrenotazione(cliente, daAnnullare);

        if (esito) {
            gestorePrenotazioni.salvaPrenotazioni();
            System.out.println("Prenotazione annullata correttamente.");
        } else {
            System.out.println("Impossibile annullare la prenotazione (potrebbe appartenere a un altro utente o essere riferita a una proiezione già passata).");
        }
    }
}