package ru.shelter.Interfaces;



import java.util.ArrayList;
import java.util.Optional;

// RQ и RS - Request и Response, соответственно
public interface ServiceInterface<RQ, RS, ID> {
    RS add(RQ Object);

    Optional<RS> get(ID id);

    boolean update(RQ Object, ID id);

    boolean remove(ID id);

    ArrayList<RS> getAll();
}

