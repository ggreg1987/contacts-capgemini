package one.digitalinnovation.contact.domain.entities;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Person implements Serializable {

    @Id
    @CPF
    @Column(length = 11)
    private String cpf;

    @Column(length = 50,nullable = false)
    private String name;

    @Column(length = 20,nullable = false)
    private String email;
}
