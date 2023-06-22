package ba.sum.fpmoz.artists.repositories;

import ba.sum.fpmoz.artists.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository <Post, Long> {}