package com.eric.proyecto.springboot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eric.proyecto.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.eric.proyecto.springboot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{

    @Query("select p.name, length(p.name) from Person p")
    public List<Object[]> getPersonNameLenght();

    @Query("select count(p) from Person p")
    Long totalPerson();

    @Query("select min(p.id) from Person p")
    Long minId();
    
    @Query("select max(p.id) from Person p")
    Long maxId();
    
    List<Person> findAllByOrderByNameAscLastnameDesc();

    @Query("select p from Person p order by p.name asc")
    List<Person> getAllOrdered();

    List<Person> findByIdBetween(Long id1, Long id2);

    List<Person> findByNameBetween(String name1, String name2);

    @Query("select p from Person p where p.name between ?1 and ?2 order by p.name")
    List<Person> findAllBetweenName(String c1, String c2);
    
    @Query("select p from Person p where p.id between ?1 and ?2 order by p.name")
    List<Person> findAllBetweenId(Long minId, Long maxId);

    @Query("select p from Person p where p.id between 2 and 5")
    List<Person> findAllBetweenId();
    
    @Query("select upper(p.name || ' ' || p.lastname) from Person p")
    List<String> findAllFullNameConcatUpper();
       
    @Query("select lower(concat(p.name, ' ', p.lastname)) from Person p")
    List<String> findAllFullNameConcatLower();

    @Query("select p.name || ' ' || p.lastname from Person p")
    List<String> findAllFullNameConcat();
    
    @Query("select count(distinct(p.programmingLanguage)) from Person p")
    Long findAllProgrammingLanguageDistinctCount();

    @Query("select distinct(p.programmingLanguage) from Person p")
    List<String> findAllProgrammingLanguageDistinct();

    @Query("select p.name from Person p")
    List<String> findAllNames();
    
    @Query("select distinct(p.name) from Person p")
    List<String> findAllNamesDistinct();

    @Query("select new com.eric.proyecto.springboot.jpa.springboot_jpa.dto.PersonDto(p.name, p.lastname) from Person p")
    List<PersonDto> findAllPersonDto();
    
    @Query("select new Person(p.name, p.lastname) from Person p")
    List<Person> findAllPersonalizedObjectPerson();

    @Query("select p.name from Person p where p.id=?1")
    String getNameById(Long id);

    @Query("select p.id from Person p where p.id=?1")
    Long getIdById(Long id);

    @Query("select concat(p.name, ' ', p.lastname) as fullname from Person p where p.id=?1")
    String getFullNameById(Long id);

    @Query("select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);

    @Query("select p from Person p where p.name=?1")
    Optional<Person> findOneName(String name);

    @Query("select p from Person p where p.name like %?1%")
    Optional<Person> findOneLikeName(String name);
    //! Hacen lo mismo, pero en el List te devuelve TODOS los que contengan el parametro
    @Query("select p from Person p where p.name like %?1%")
    List<Person> findOneLikeName2(String name);
    //! Este de abajo tiene el problema en que si 2 nombres en la BBDD comparten el parametro da error
    Optional<Person> findByNameContaining(String name);

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("select p from Person p where p.programmingLanguage=?1")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage);

    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("select p, p.programmingLanguage from Person p")
    List<Object[]> findAllMixPerson();

    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonDataFullList();
    
    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id=?1")
    Object obtenerPersonDataById(Long id);

    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    @Query("select p.name, p.programmingLanguage from Person p where p.name=?1")
    List<Object[]> obtenerPersonData(String name);

     @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1")
    List<Object[]> obtenerPersonDataByProgrammingLanguage(String programmingLanguage);

    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Object[]> obtenerPersonData(String programmingLanguage, String name);
}
