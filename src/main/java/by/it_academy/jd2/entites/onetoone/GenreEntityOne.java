package by.it_academy.jd2.entites.onetoone;


import jakarta.persistence.*;

@Entity
@Table(name = "genre", schema = "vote_app")
public class GenreEntityOne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @OneToOne(mappedBy = "genre")
    private VoteEntityOne vote;
    public GenreEntityOne() {
    }
    public GenreEntityOne(String name) {}
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

    public VoteEntityOne getVote() {
        return vote;
    }

    public void setVote(VoteEntityOne vote) {
        this.vote = vote;
    }
}
