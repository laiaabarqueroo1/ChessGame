package Src;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static char[][] tauler;

    public static void main(String[] args) {
        mostrarMenu();
    }

    // Mostra el menú principal
    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcio = 0;

        while (opcio != 3) {
            System.out.println("Menú principal:");
            System.out.println("1. Jugar nova partida");
            System.out.println("2. Reproduir partida des d'un fitxer");
            System.out.println("3. Sortir");
            System.out.print("Selecciona una opció: ");
            opcio = scanner.nextInt();
            scanner.nextLine(); // Neteja el buffer

            switch (opcio) {
                case 1:
                    jugarNovaPartida();
                    break;
                case 2:
                    reproduirPartida();
                    break;
                case 3:
                    System.out.println("Sortint del programa.");
                    break;
                default:
                    System.out.println("Opció no vàlida, torna a intentar-ho.");
            }
        }
    }

    // Inicia una nova partida d'escacs
    public static void jugarNovaPartida() {
        Turns<String> torns = new Turns<>();
        inicialitzarTauler();
        Scanner scanner = new Scanner(System.in);
        boolean jocAcabat = false;

        while (!jocAcabat) {
            mostrarTauler();
            System.out.print("Introdueix el torn (per exemple, E2 E4): ");
            String torn = scanner.nextLine();
            torns.afegirTorn(torn);

            // Converteix el torn en coordenades i mou les peces
            if (tornToPosition(torn)) {
                mostrarTauler(); // Mostra el tauler després del moviment
            } else {
                System.out.println("Torn invàlid. Torna a intentar-ho.");
            }

            // Aquí es podria afegir la lògica per comprovar si el joc ha acabat.
            System.out.print("El joc ha acabat? (si/no): ");
            jocAcabat = scanner.nextLine().equalsIgnoreCase("si");
        }

        // Guardar la partida en un fitxer al final
        System.out.print("Introdueix el nom del fitxer per guardar la partida: ");
        String nomFitxer = scanner.nextLine();
        try {
            torns.guardarAFitxer(nomFitxer);
            System.out.println("Partida guardada correctament.");
        } catch (IOException e) {
            System.out.println("Error en guardar la partida: " + e.getMessage());
        }
    }

    // Reprodueix una partida des d'un fitxer
    public static void reproduirPartida() {
        try {
            Turns<String> torns = llegirTorns();
            inicialitzarTauler();
            while (torns.obtenirNumTorns() > 0) {
                mostrarTauler();
                String torn = torns.agafarPrimerTorn();
                System.out.println("Reproduint torn: " + torn);
                tornToPosition(torn);
                mostrarTauler();
            }
        } catch (IOException e) {
            System.out.println("Error en carregar la partida: " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Llegeix el fitxer de torns i retorna la llista de torns
    public static Turns<String> llegirTorns() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introdueix el nom del fitxer per carregar la partida: ");
        String nomFitxer = scanner.nextLine();
        try {
            return new Turns<>(nomFitxer);
        } catch (IOException e) {
            System.out.println("Error en llegir el fitxer: " + e.getMessage());
            return llegirTorns(); // Crida recursiva en cas d'error
        }
    }

    // Converteix un torn (per exemple, "E2 E4") en coordenades i mou les peces
    public static boolean tornToPosition(String torn) {
        // Exemple bàsic de conversió de torn en coordenades
        String[] parts = torn.split(" ");
        if (parts.length != 2) return false;

        int[] origen = convertirPosicio(parts[0]);
        int[] desti = convertirPosicio(parts[1]);

        if (origen == null || desti == null) return false;

        // Mou la peça de l'origen al destí si és un moviment vàlid
        tauler[desti[0]][desti[1]] = tauler[origen[0]][origen[1]];
        tauler[origen[0]][origen[1]] = ' ';
        return true;
    }

    // Converteix una posició com "E2" en coordenades del tauler
    public static int[] convertirPosicio(String posicio) {
        if (posicio.length() != 2) return null;

        char columna = posicio.charAt(0);
        char fila = posicio.charAt(1);

        int x = 8 - Character.getNumericValue(fila); // La fila és inversa
        int y = columna - 'A';

        if (x < 0 || x >= 8 || y < 0 || y >= 8) return null;

        return new int[] {x, y};
    }

    // Mostra el tauler actual d'escacs
    public static void mostrarTauler() {
        System.out.println("  A B C D E F G H");
        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(tauler[i][j] + " ");
            }
            System.out.println(8 - i);
        }
        System.out.println("  A B C D E F G H");
    }

    // Inicialitza el tauler d'escacs amb les peces blanques i negres
    public static void inicialitzarTauler() {
        tauler = new char[8][8];
        iniciarJocBlanques();
        iniciarJocNegres();
        for (int i = 2; i < 6; i++) {
            Arrays.fill(tauler[i], ' '); // Caselles buides
        }
    }

    // Inicialitza les peces blanques en el tauler
    public static void iniciarJocBlanques() {
        tauler[6] = new char[] {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'}; // Peons
        tauler[7] = new char[] {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'}; // Peces principals
    }

    // Inicialitza les peces negres en el tauler
    public static void iniciarJocNegres() {
        tauler[1] = new char[] {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'}; // Peons
        tauler[0] = new char[] {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'}; // Peces principals
    }
}
