package ba.sum.fpmoz.artists.model;

import jakarta.persistence.*;

@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String text;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = true)
    User user;

    @ManyToOne
    @JoinColumn(name="post_id", nullable = true)
    Post post;

    public Comment(Long id, String text, User user, Post post) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.post = post;
    }

    public Comment() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
