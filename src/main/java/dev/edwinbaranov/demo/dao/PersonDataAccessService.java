package dev.edwinbaranov.demo.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import dev.edwinbaranov.demo.model.Person;

//FAKE REPOSITORY to show example of switching between different repositories
@Repository("postgres")
public class PersonDataAccessService implements PersonDao {

	@Override
	public int insertPerson(UUID id, Person person) {
		return 0;
	}

	@Override
	public List<Person> selectAllPeople() {
		return List.of(new Person(UUID.randomUUID(), "FROM POSTGRES DB"));
	}

	@Override
	public Optional<Person> selectPersonByID(UUID id) {
		return null;
	}

	@Override
	public int deletePersonById(UUID id) {
		return 0;
	}

	@Override
	public int updatePersonById(UUID id, Person person) {
		return 0;
	}

}
