package co.edu.puj.rpg.modelo.tablero;

import co.edu.puj.rpg.modelo.Arquero;
import co.edu.puj.rpg.modelo.Guerrero;
import co.edu.puj.rpg.modelo.Mago;
import co.edu.puj.rpg.modelo.Personaje;

import java.util.List;

public class Tablero {

    public static final int TAMANO = 5;

    private char[][] matriz = new char[TAMANO][TAMANO];
    private int cofreX;
    private int cofreY;

    // x = columna (horizontal), y = fila (vertical)
    public Tablero(int cofreX, int cofreY) {
        this.cofreX = cofreX;
        this.cofreY = cofreY;
        limpiar();
    }

    private void limpiar() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = '.';
            }
        }
    }

    // Vuelve a dibujar el mapa según dónde está cada personaje vivo
    public void actualizar(List<Personaje> heroes, List<Personaje> enemigos) {
        limpiar();
        matriz[cofreY][cofreX] = 'C';

        for (Personaje e : enemigos) {
            if (e.estaVivo()) {
                matriz[e.getPosicionY()][e.getPosicionX()] = 'E';
            }
        }
        for (Personaje h : heroes) {
            if (h.estaVivo()) {
                char simbolo = (h instanceof Guerrero) ? 'G' : (h instanceof Mago) ? 'M' : (h instanceof Arquero) ? 'A' : 'H';
                matriz[h.getPosicionY()][h.getPosicionX()] = simbolo;
            }
        }
    }

    public void mostrarTablero(char[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print("[" + matriz[i][j] + "]");
            }
            System.out.println();
        }
    }

    public boolean dentroDeLimites(int x, int y) {
        return x >= 0 && x < TAMANO && y >= 0 && y < TAMANO;
    }

    public boolean esCofre(int x, int y) {
        return x == cofreX && y == cofreY;
    }

    public char[][] getMatriz() {
        return matriz;
    }
}