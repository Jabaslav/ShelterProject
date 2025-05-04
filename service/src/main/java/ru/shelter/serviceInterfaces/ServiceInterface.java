package ru.shelter.serviceInterfaces;



import java.util.List;
import java.util.Optional;

// RQ и RS - Request и Response, соответственно
public interface ServiceInterface<RQ, RS, ID> {

    Optional<RS> get(ID id);

    boolean remove(ID id);

    List<RS> getAll();
}

