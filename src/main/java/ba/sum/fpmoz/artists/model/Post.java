package ba.sum.fpmoz.artists.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    String image;

    @Column(nullable = true)
    Integer number_of_likes;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = true)
    User user;

    @OneToMany(mappedBy = "post")
    List<Like> likes;

    @OneToMany(mappedBy = "post")
    List<Comment> comments;

    public Post() {
    }

    public Post(Long id, String description, String image, Integer number_of_likes) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.number_of_likes = number_of_likes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNumber_of_likes() {
        return number_of_likes;
    }

    public void setNumber_of_likes(Integer number_of_likes) {
        this.number_of_likes = number_of_likes;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
