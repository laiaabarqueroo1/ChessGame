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
    public void afegirTorn(E torn) {
        llistatTorns.add(torn);
    }

    public int obtenirNumTorns() {
        return llistatTorns.size();
    }

    public E agafarPrimerTorn() {
        if (llistatTorns.isEmpty()) {
            System.out.println("There's no more extra turns");
        }
        return llistatTorns.remove(0);
    }
}