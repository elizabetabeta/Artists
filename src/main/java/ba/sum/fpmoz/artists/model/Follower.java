package ba.sum.fpmoz.artists.model;

import jakarta.persistence.*;

@Entity
@Table(name="followers")
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    User user;

    public Follower(Long id) {
        this.id = id;
    }

    public Follower() {}

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

}
