package ca.seneca.healthplussalesforcelightning.repository;

import ca.seneca.healthplussalesforcelightning.model.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembersRepository extends JpaRepository<Members, Long> {
    List<Members> findByStatusIgnoreCase(String status);
}
