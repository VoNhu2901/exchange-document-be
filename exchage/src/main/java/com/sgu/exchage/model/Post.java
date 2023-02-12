package com.sgu.exchage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Post extends AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Long price;

    @Enumerated(EnumType.STRING)
    private Status status;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<PostImage> postImages;

    @ManyToOne
    @JoinColumn(name = "favourite_id")
    private Favourite favourite;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    //    Modeling With a Foreign Key
    @OneToOne(mappedBy = "post")
    private Bill bill;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}