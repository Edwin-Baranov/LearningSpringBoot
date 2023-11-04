package dev.edwinbaranov.demo.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.edwinbaranov.demo.model.Person;

public class FakePersonDataAccessServiceTest {
	private FakePersonDataAccessService underTest;

	@BeforeEach
	public void setup() {
		underTest = new FakePersonDataAccessService();
	}

	@Test
	public void canPerformCrud() {
		// Given person called James Bond
		UUID idOne = UUID.randomUUID();
		Person personOne = new Person(idOne, "James Bond");

		// ...And Anna Smith
		UUID idTwo = UUID.randomUUID();
		Person personTwo = new Person(idTwo, "Anna Smith");

		// When James and Anna added to db
		underTest.insertPerson(idOne, personOne);
		underTest.insertPerson(idTwo, personTwo);

		// Then can retrieve James by id
		assertThat(underTest.selectPersonByID(idOne))
				.isPresent()
				.hasValueSatisfying(personFromDB -> assertThat(personFromDB)
						.usingRecursiveComparison()
						.isEqualTo(personOne));

		// ...And also Anna by id
		assertThat(underTest.selectPersonByID(idTwo))
				.isPresent()
				.hasValueSatisfying(personFromDB -> assertThat(personFromDB)
						.usingRecursiveComparison()
						.isEqualTo(personTwo));

		// When get all people
		List<Person> people = underTest.selectAllPeople();

		// ...List should have size 2 and should have both James and Anna
		assertThat(people)
				.hasSize(2)
				.usingRecursiveFieldByFieldElementComparator()
				.containsExactlyInAnyOrder(personOne, personTwo);

		// ...An update request (James Bond name to Jake Black)
		Person personUpdate = new Person(idOne, "Jake Black");

		// When Update
		assertThat(underTest.updatePersonById(idOne, personUpdate)).isEqualTo(1);

		// Then person with idOne should have name Jake Black
		assertThat(underTest.selectPersonByID(idOne))
				.isPresent()
				.hasValueSatisfying(
						personFromDB -> assertThat(personFromDB)
								.usingRecursiveComparison()
								.isEqualTo(personUpdate));

		// When Delete Jake Black
		assertThat(underTest.deletePersonById(idOne)).isEqualTo(1);

		// Then getting personOne should be empty
		assertThat(underTest.selectPersonByID(idOne)).isEmpty();

		// DB should only contain Anna Smith
		assertThat(underTest.selectAllPeople())
				.hasSize(1)
				.usingRecursiveFieldByFieldElementComparator()
				.containsExactlyInAnyOrder(personTwo);
	}

	@Test
	public void willReturn0IfNoPersonFoundToDelete() {
		//Given
		UUID id = UUID.randomUUID();

		//When
		assertThat(underTest.deletePersonById(id)).isEqualTo(0);
	}

	@Test
	public void willReturn0IfNoPersonFoundToUpdate() {
		//Given
		UUID id = UUID.randomUUID();
		Person person = new Person(id, "James NotInDb");

		//When
		assertThat(underTest.updatePersonById(id, person)).isEqualTo(0);
	}
}
