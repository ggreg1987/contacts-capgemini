package one.digitalinnovation.contact.domain.repository;

import one.digitalinnovation.contact.domain.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PhoneRepository extends JpaRepository<Phone,Long> {
}
