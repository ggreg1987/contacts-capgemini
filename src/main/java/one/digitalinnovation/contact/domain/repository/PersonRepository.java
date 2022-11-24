package one.digitalinnovation.contact.domain.repository;

import one.digitalinnovation.contact.domain.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface PersonRepository extends JpaRepository<Person,String> {

    Optional<Person> findByCpf(String cpf);

    Boolean existsByCpf(String cpf);
}
