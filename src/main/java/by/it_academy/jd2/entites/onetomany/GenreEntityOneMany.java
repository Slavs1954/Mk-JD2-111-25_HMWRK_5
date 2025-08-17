package by.it_academy.jd2.entites.onetomany;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genre", schema = "vote_app")
public class GenreEntityOneMany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "genre")
    private List<VoteEntityOneMany> votes = new ArrayList<>();
    public GenreEntityOneMany() {
    }
    public GenreEntityOneMany(String name) {}
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

    public List<VoteEntityOneMany> getVotes() {
        return votes;
    }

    public void setVotes(List<VoteEntityOneMany> votes) {
        this.votes = votes;
    }
}
