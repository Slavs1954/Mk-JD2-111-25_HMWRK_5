package by.it_academy.jd2.entites.onetomany;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vote", schema = "vote_app")
public class VoteEntityOneMany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "artist_id")
    private long artistId;
    @Column(name = "about")
    private String about;

    @ManyToOne()
    @JoinTable(
            name = "vote_to_genre",
            schema = "vote_app",
            joinColumns = @JoinColumn(name = "vote_id"),       // vote_id in join table
            inverseJoinColumns = @JoinColumn(name = "genre_id") // genre_id in join table
    )
    private GenreEntityOneMany genre;

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

    public GenreEntityOneMany getGenre() {
        return genre;
    }

    public void setGenre(GenreEntityOneMany genre) {
        this.genre = genre;
    }
}
