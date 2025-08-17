package by.it_academy.jd2.entites.manytomany;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genre", schema = "vote_app")
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "genre")
    private List<VoteEntity> votes =  new ArrayList<VoteEntity>();
    public GenreEntity() {
    }
    public GenreEntity(String name) {}
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VoteEntity> getVotes() {
        return votes;
    }

    public void setVotes(List<VoteEntity> votes) {
        this.votes = votes;
    }
}
