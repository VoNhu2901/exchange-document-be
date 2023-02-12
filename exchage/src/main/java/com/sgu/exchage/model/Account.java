package com.sgu.exchage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private boolean role;

    private String avatar;

    private boolean isBlock;

    private boolean isActive;

    private String otpCode;


    //    Modeling With a Shared Primary Key
    @OneToOne
    @MapsId
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "account")
    private List<Slider> sliders;

    @OneToMany(mappedBy = "account")
    private List<Post> posts;

    //    Modeling With a Foreign Key
    @OneToOne(mappedBy = "account")
    private Bill bill;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        return id != null && id.equals(((Account) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
