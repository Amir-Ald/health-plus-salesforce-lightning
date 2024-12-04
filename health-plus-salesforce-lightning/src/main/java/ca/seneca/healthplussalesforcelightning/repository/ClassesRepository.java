package ca.seneca.healthplussalesforcelightning.repository;

import ca.seneca.healthplussalesforcelightning.model.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Long> {
}
