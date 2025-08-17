package by.it_academy.jd2.entites.manytomany;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vote", schema = "vote_app")
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "artist_id")
    private long artistId;
    @Column(name = "about")
    private String about;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "vote_to_genre",
            schema = "vote_app",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "vote_id")
    )
    private List<GenreEntity> genre = new ArrayList<GenreEntity>();

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

    public List<GenreEntity> getGenre() {
        return genre;
    }

    public void setGenre(List<GenreEntity> genre) {
        this.genre = genre;
    }
}
