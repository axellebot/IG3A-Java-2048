package miniprojet.model;

import java.util.ArrayList;

/**
 * @author Axel LE BOT
 */
public class Grid {

    private int size;
    private Tile grille[][];

    public Grid(int size) {
        this.size = size;
        this.grille = new Tile[this.size][this.size];

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                grille[i][j] = new Tile(0, j, i);
            }
        }
    }

    public Grid(Grid g) {
        this.size = g.size;
        this.grille = new Tile[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.grille[i][j] = new Tile(g.getGrille()[i][j]);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Tile[][] getGrille() {
        return grille;
    }

    @Override
    public String toString() {
        String _tmp = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                _tmp += grille[i][j].toString() + "\t";
            }
            _tmp += "\n";
        }
        return _tmp;
    }

    public void addRandom() {
        ArrayList<Tile> emptyTile = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grille[i][j].getValue() == 0) {
                    emptyTile.add(grille[i][j]);
                }
            }
        }
        if (emptyTile.size() > 0) {
            int index = (int) (Math.random() * (emptyTile.size() - 1));

            double rand = Math.random();
            if (rand <= 0.5) {
                emptyTile.get(index).setValue(2);

            } else {
                if (rand > 0.5) {
                    emptyTile.get(index).setValue(4);

                }
            }
        }
    }

    public int[] fetchColumn(int j) {
        int tab[] = new int[size];

        //initaliser les cases de tab à 0
        for (int i = 0; i < size; i++) {
            tab[i] = 0;
        }

        int k = 0;

        for (int i = 0; i < size; i++) {
            if (grille[i][j].getValue() > 0) {
                tab[k] = grille[i][j].getValue();
                k++;
            }
        }
        return tab;
    }

    public int[] fetchLine(int i) {
        int tab[] = new int[size];

        //initaliser les cases de tab à 0
        for (int j = 0; j < size; j++) {
            tab[j] = 0;
        }

        int k = 0;

        for (int j = 0; j < size; j++) {
            if (grille[i][j].getValue() > 0) {
                tab[k] = grille[i][j].getValue();
                k++;
            }
        }
        return tab;
    }

    public void pushUp() {
        for (int j = 0; j < size; j++) { // pour chaque colonne
            int tab[] = fetchColumn(j); //recupérer la colonne condensé
            int i = 0, k = 0;

            while (k < size) {  //chaque ligne de la colonne
                if (k + 1 < size && tab[k] == tab[k + 1]) {
                    this.grille[i][j].setValue(tab[k] + tab[k + 1]);
                    k += 2;
                } else {
                    this.grille[i][j].setValue(tab[k]);
                    k++;
                }
                i++;
            }
            for (; i < size; i++) {
                this.grille[i][j].setValue(0);
            }
        }
    }

    public void pushDown() {
        for (int j = 0; j < size; j++) { // pour chaque colonne
            int tab[] = fetchColumn(j); //recupérer la colonne condensé
            int i = this.size - 1, k = this.size - 1;

            while (k >= 0) {  //chaque ligne de la colonne
                if (tab[k] != 0) {
                    if (k - 1 >= 0 && tab[k] == tab[k - 1]) {
                        this.grille[i][j].setValue(tab[k] + tab[k - 1]);
                        k -= 2;
                    } else {
                        this.grille[i][j].setValue(tab[k]);
                        k--;
                    }
                    i--;
                } else {
                    k--;
                }
            }
            for (; i >= 0; i--) {
                this.grille[i][j].setValue(0);
            }
        }
    }

    public void pushLeft() {
        for (int i = 0; i < size; i++) { // pour chaque ligne
            int tab[] = fetchLine(i); //recupérer la ligne condensé
            int j = 0, k = 0;

            while (k < size) {  //chaque colonne de la ligne
                if (k + 1 < size && tab[k] == tab[k + 1]) {
                    this.grille[i][j].setValue(tab[k] + tab[k + 1]);
                    k += 2;
                } else {
                    this.grille[i][j].setValue(tab[k]);
                    k++;
                }
                j++;
            }
            for (; j < size; j++) {
                this.grille[i][j].setValue(0);
            }
        }
    }

    public void pushRight() {
        for (int i = 0; i < size; i++) { // pour chaque ligne
            int tab[] = fetchLine(i); //recupérer la ligne condensé
            int j = this.size - 1, k = this.size - 1;

            while (k >= 0) {  //chaque ligne de la colonne
                if (tab[k] != 0) {
                    if (k - 1 >= 0 && tab[k] == tab[k - 1]) {
                        this.grille[i][j].setValue(tab[k] + tab[k - 1]);
                        k -= 2;
                    } else {
                        this.grille[i][j].setValue(tab[k]);
                        k--;
                    }
                    j--;
                } else {
                    k--;
                }
            }
            for (; j >= 0; j--) {
                this.grille[i][j].setValue(0);
            }
        }

    }

    /**
     * Compare 2 grid
     *
     * @param g1 Grid 1
     * @param g2 Grid 2
     * @return true if same grid false otherwise
     */
    public static boolean equalsGrille(Grid g1, Grid g2) {
        if (!(g1.getSize() == g2.getSize())) { // not same size ?
            return false;
        } else {
            for (int i = 0; i < g1.getSize(); i++) {
                for (int j = 0; j < g1.getSize(); j++) {
                    if (g1.getGrille()[i][j].getValue() != g2.getGrille()[i][j].getValue()) { // same value ?
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public boolean grillePerdu() {
        int k = 0;

        Grid g_tmp = new Grid(this);

        boolean grilleRempli = false;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.grille[i][j].getValue() != 0) {
                    k++;
                }
            }
        }
        if (k == this.getSize() * this.getSize()) {
            grilleRempli = true;
        }
        if (grilleRempli) {
            g_tmp.pushDown();
            g_tmp.pushUp();
            g_tmp.pushLeft();
            g_tmp.pushRight();
            return Grid.equalsGrille(this, g_tmp);
        }
        return false;
    }

    public boolean grilleGagne() {
        return (getValueMax() == 2048);
    }

    public int getValueMax() {
        int max = 0;
        int currentValue = 0;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                currentValue = this.grille[i][j].getValue();
                max = Math.max(currentValue, max);
                if (max == 2048) {
                    return currentValue;
                }
            }
        }
        return max;
    }
}
