package co.edu.puj.rpg.modelo.inventario;

import co.edu.puj.rpg.exception.CapacidadExcedidaException;

import java.util.ArrayList;
import java.util.List;

public class Inventario<T> {

    private List<T> items = new ArrayList<>();
    private final int capacidadMaxima;

    public Inventario(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    // Lanza CapacidadExcedidaException (verificada) si el inventario ya está lleno
    public void agregar(T item) throws CapacidadExcedidaException {
        if (items.size() >= capacidadMaxima) {
            throw new CapacidadExcedidaException("inventario", capacidadMaxima);
        }
        items.add(item);
    }

    public T obtener(int index) {
        return items.get(index);
    }

    public T quitar(int index) {
        return items.remove(index);
    }

    public int cantidad() {
        return items.size();
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public List<T> getItems() {
        return items;
    }
}