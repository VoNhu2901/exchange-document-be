package com.sgu.exchage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Favourite extends AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    Modeling With a Foreign Key
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "person_id")
    private Account account;

    @OneToMany(mappedBy = "favourite", cascade = CascadeType.ALL)
    private Set<Post> posts;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Favourite)) {
            return false;
        }
        return id != null && id.equals(((Favourite) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
