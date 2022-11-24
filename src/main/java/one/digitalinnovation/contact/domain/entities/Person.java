package one.digitalinnovation.contact.domain.entities;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "person",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Phone> phones;

    public List<Phone> getPhones() {
        if(this.phones.isEmpty()) {
            this.phones = new ArrayList<>();
        }
        return this.phones;
    }
}
