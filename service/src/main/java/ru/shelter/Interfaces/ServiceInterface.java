package ru.shelter.Interfaces;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// RQ и RS - Request и Response, соответственно
public interface ServiceInterface<RQ, RS, ID> {
    RS add(RQ Object);

    Optional<RS> get(ID id);

    RS update(RQ Object, ID id);

    boolean remove(ID id);

    List<RS> getAll();
}

