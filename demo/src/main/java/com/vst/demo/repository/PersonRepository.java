package com.vst.demo.repository;

import com.vst.demo.enitity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {


    @Query(
            value = "select * from persons where email = :email",
            nativeQuery = true
    )
    Optional<Person> findByEmail(@Param("email") String email);
}
