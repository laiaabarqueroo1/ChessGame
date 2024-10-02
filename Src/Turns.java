package Src;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Turns<E> {
    private ArrayList<E> llistatTorns;

    public Turns() {
        this.llistatTorns = new ArrayList<>();
    }

    public Turns(String nomFitxer) throws IOException {
        this.llistatTorns = new ArrayList<>();
        carregarDesDeFitxer(nomFitxer);
        if (llistatTorns.isEmpty()) {
            System.out.println("The file is empty or does not contain valid shifts.");
        }
    }

    public void afegirTorn(E torn) {
        llistatTorns.add(torn);
    }

    public void guardarAFitxer(String nomFitxer) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFitxer))) {
            for (E torn : llistatTorns) {
                writer.write(torn.toString());
                writer.newLine();
            }
        }
    }

    private void carregarDesDeFitxer(String nomFitxer) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomFitxer))) {
            String linia;
            while ((linia = reader.readLine()) != null) {
                llistatTorns.add((E) linia);
            }
        }
    }

    public E agafarPrimerTorn() {
        if (llistatTorns.isEmpty()) {
            System.out.println("there are not extra moves");        }
        return llistatTorns.remove(0);
    }

    public int obtenirNumTorns() {
        return llistatTorns.size();
    }

    public void imprimirTorns() {
        for (E torn : llistatTorns) {
            System.out.println(torn);
        }
    }
}
