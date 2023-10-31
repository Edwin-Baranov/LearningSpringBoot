package dev.edwinbaranov.demo.dao;
//
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.edwinbaranov.demo.model.Person;

public interface PersonDao {
    int insertPerson(UUID id, Person person);

    default int insertPerson(Person person) {
        UUID id = UUID.randomUUID();
        return insertPerson(id, person);
    }

    List<Person> selectAllPeople();

    Optional<Person> selectPersonByID(UUID id);
    
    int deletePersonById(UUID id);

    int updatePersonById(UUID id, Person person);
}
