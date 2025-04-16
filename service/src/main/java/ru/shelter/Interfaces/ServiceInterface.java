package ru.shelter.Interfaces;



import java.util.ArrayList;
import java.util.Optional;

public interface ServiceInterface<T, ID> {
    T add(T Object);

    Optional<T> get(ID id);

    boolean update(T Object, ID id);

    boolean remove(ID id);

    ArrayList<T> getAll();
}

