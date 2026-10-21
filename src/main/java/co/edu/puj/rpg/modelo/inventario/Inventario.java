package co.edu.puj.rpg.modelo.inventario;

import java.util.ArrayList;
import java.util.List;

public class Inventario<T> {

    private List<T> items = new ArrayList<>();

    public void agregar(T item) {
        items.add(item);
    }

    public T obtener(int index) {
        return items.get(index);
    }

    // Saca el elemento del inventario y lo devuelve (para gastar una poción)
    public T quitar(int index) {
        return items.remove(index);
    }

    public int cantidad() {
        return items.size();
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public List<T> getItems() {
        return items;
    }
}
