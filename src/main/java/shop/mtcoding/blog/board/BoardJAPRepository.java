package shop.mtcoding.blog.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardJAPRepository extends JpaRepository<Board, Integer> {

    @Query("select b from Board b join fetch b.user u where b.id = :id")
    Optional<Board> findByIdJoinUser(@Param("id") int id);

//    @Query("select b from Board b join fetch b.user u left join fetch b.replies r where b.id = :id")
//    Optional<Board> findByIdJoinUserAndReplies(@Param("id") int id);
}