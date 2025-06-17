package com.eric.proyecto.springboot.jpa.springboot_jpa;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.eric.proyecto.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.eric.proyecto.springboot.jpa.springboot_jpa.entities.Person;
import com.eric.proyecto.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner{

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		update();
	}

	@Transactional
	public void queriesFunctionAggregation(){
		
		Long count = repository.totalPerson();
		System.out.println("Total de registros en tabla " + count);
		Long min = repository.minId();
		System.out.println("El id mas bajo " + min);
		long max = repository.maxId();
		System.out.println("El id mas alto " + max);
		System.out.println("Consulta nombre y su tamaño");
		List<Object[]> regs = repository.getPersonNameLenght();
		regs.forEach(reg -> {
			String name = (String)reg[0];
			Integer length = (Integer) reg[1];
			System.out.println("Name = "+name+ ", length = "+length);
		});

	}

	@Transactional
	public void personalizedQueriesBetween() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Consulta personas con between");
		List<Person> persons = repository.findAllBetweenId(2L, 5L);
		persons.forEach(person -> System.out.println(person));

		persons = repository.findAllBetweenName("J", "P");
		persons.forEach(person -> System.out.println(person));

		persons = repository.getAllOrdered();
		persons.forEach(person -> System.out.println(person));

		System.out.println("Consulta personas con between con los ids que mando");        
        System.out.println("Entre qué id mínimo vamos a buscar?");
		Long minId = scanner.nextLong();        
        System.out.println("Entre qué id máximo vamos a buscar?");
        Long maxId = scanner.nextLong();
		List<Person> persons2 = repository.findAllBetweenId(minId, maxId);
		persons2.forEach(p -> System.out.println(p));
		scanner.close();
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesConcatUpperAndLowerCase(){
		System.out.println("Consulta nombres y apellidos");
		List<String> names = repository.findAllFullNameConcat();
		names.forEach(name -> System.out.println(name));

		System.out.println("Consulta nombres y apellidos uppercase");
		List<String> namesUpper = repository.findAllFullNameConcatUpper();
		namesUpper.forEach(nameUpper -> System.out.println(nameUpper));

		System.out.println("Consulta nombres y apellidos lowercase");
		List<String> namesLower = repository.findAllFullNameConcatLower();
		namesLower.forEach(nameLower -> System.out.println(nameLower));

	}

	@Transactional
	public void personalizedQueriesDistinct(){
		System.out.println("Consultas con nombres de personas");
		List<String> names = repository.findAllNames();
		names.forEach(name -> System.out.println(name));

		System.out.println("Consultas con nombres no repetidos");
		List<String> names2 = repository.findAllNamesDistinct();
		names2.forEach(name -> System.out.println(name));
		
		System.out.println("Consultas con lenguajes no repetidos");
		List<String> programmingLanguages = repository.findAllProgrammingLanguageDistinct();
		programmingLanguages.forEach(programmingLanguage -> System.out.println(programmingLanguage));
		
		System.out.println("Consultas con total lenguajes");
		Long programmingLanguagesQuantity = repository.findAllProgrammingLanguageDistinctCount();
		System.out.println(programmingLanguagesQuantity);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries2(){
		System.out.println("===== Consulta por objeto persona y lenguaje de programación =====");
		List<Object[]> personRegs = repository.findAllMixPerson();

		personRegs.forEach(regs -> {
			System.out.println("programminLanguage= " + regs[1] + ", person= " + regs[0]);
		});

		System.out.println("Consulta que puebla y devuelve un objeto entity de una instancia personalizada");
		List<Person> persons = repository.findAllPersonalizedObjectPerson();
		persons.forEach(person -> System.out.println(person));

		System.out.println("Consulta que devuelve objeto Dto de una clase personalizada");
		List<PersonDto> personsDto = repository.findAllPersonDto();
		personsDto.forEach(pDto -> System.out.println(pDto));
	}

	@Transactional(readOnly = true)
	public void personalizedQueries() {
		Scanner scanner = new Scanner(System.in);

		try {
			System.out.println("Ingrese el ID");
			Long id = scanner.nextLong();
			
			String name = repository.getNameById(id);
			Long idDb = repository.getIdById(id);

			String fullName = repository.getFullNameById(id);

			Object[] personReg = (Object[]) repository.obtenerPersonDataById(id);

			List<Object[]> regs = repository.obtenerPersonDataFullList();

			if (name != null && idDb != null && fullName != null && personReg != null && regs != null) {
				System.out.println("El nombre perteneciente al ID " + id + " es " + name);
				System.out.println("El nombre completo de la persona con ID " + id + " es " + fullName);
				System.out.println("id=" + personReg[0] + ", nombre= " + personReg[1] + ", apellido= " + personReg[2] + ", lenguaje de programacion= " + personReg[3]);
				System.out.println("===== Lista completa =====");
				regs.forEach(reg -> System.out.println("id=" + reg[0] + ", nombre= " + reg[1] + ", apellido= " + reg[2] + ", lenguaje de programacion= " + reg[3]));
			} else {
				System.out.println("No existe el ID " + id + " en la base de datos");
			}
		} catch (InputMismatchException e) {
			System.out.println("Error: Debe ingresar un número válido");
		} finally {
			scanner.close();
		}
	}

	@Transactional
	public void delete(){
		repository.findAll().forEach(System.out::println);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingresa el ID de la persona a eliminar");
		Long id = scanner.nextLong();

		repository.findById(id).ifPresentOrElse(
			person -> {
				System.out.println(person);
				repository.delete(person);
				System.out.println("Persona eliminada");
				repository.findAll().forEach(System.out::println);
			}, 
			() -> System.out.println("No se encuentra el ID " +id)
		);
		scanner.close();
	}

	@Transactional
	public void update() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingresa el ID de la persona a editar");
		Long id = scanner.nextLong();
		
		repository.findById(id).ifPresentOrElse(
			person -> {
				System.out.println(person);
				System.out.println("Ingrese el lenguaje de programación");
				String programmingLanguage = scanner.next();
				if(programmingLanguage.equals(person.getProgrammingLanguage())){
					System.out.println("Este ya es el lenguaje que tenía");
				} else {
					person.setProgrammingLanguage(programmingLanguage);
					Person updatedPerson = repository.save(person);
					System.out.println(updatedPerson);
				}
			},
			() -> System.out.println("No se encuentra el ID " + id)
		);
		scanner.close();

	}

	@Transactional
	public void create() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingresa el nombre");
		String name = scanner.next();
		System.out.println("Ingresa el apellido");
		String lastname = scanner.next();
		System.out.println("Ingresa el lenguaje de programación");
		String programmingLanguage = scanner.next();
		scanner.close();

		Person person = new Person(null, name, lastname, programmingLanguage);
		Person newPerson = repository.save(person);
		System.out.println(newPerson);

		repository.findById(newPerson.getId()).ifPresent(p-> System.out.println(p));

	}

	@Transactional(readOnly = true)
	public void findOne() {
		// Person person = null;
		// Optional<Person> optinalPerson = repository.findById(1L);
		// if(!optinalPerson.isEmpty()) {
		// 	person = optinalPerson.get();
		// } 
		// System.out.println(person);

		repository.findByNameContaining("eri").ifPresent(System.out::println);

	}
	
	@Transactional(readOnly = true)
	public void list() {
		
		// List<Person> persons = (List<Person>) repository.findAll();
		// List<Person> persons = (List<Person>) repository.buscarByProgrammingLanguage("Java");
		List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("Java", "Eric");
		
		persons.stream().forEach(person -> System.out.println(person));
		
		List<Object[]> personsValues =  repository.obtenerPersonDataByProgrammingLanguage("Java");
		personsValues.stream().forEach(person -> {
			System.out.println(person[0] + " es experto en " + person[1]);
		});

	}

}
