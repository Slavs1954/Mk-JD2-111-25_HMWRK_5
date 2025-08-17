package by.it_academy.jd2.entites.onetoone;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vote", schema = "vote_app")
public class VoteEntityOne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "artist_id")
    private long artistId;
    @Column(name = "about")
    private String about;

    @OneToOne()
    @JoinTable(
            name = "vote_to_genre",
            schema = "vote_app",
            joinColumns = @JoinColumn(name = "vote_id"),      // vote owns the mapping
            inverseJoinColumns = @JoinColumn(name = "genre_id", unique = true) // enforce one-to-one
    )
    private GenreEntityOne genre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public GenreEntityOne getGenre() {
        return genre;
    }

    public void setGenre(GenreEntityOne genre) {
        this.genre = genre;
    }
}
