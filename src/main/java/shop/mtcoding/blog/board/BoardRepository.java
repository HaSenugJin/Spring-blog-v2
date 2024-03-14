package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.user.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    @Transactional
    public void save(Board board) {
        em.persist(board);
    }

    public List<Board> findAll() {
        Query query = em.createQuery("select b from Board b order by b.id desc", Board.class);
        return query.getResultList();
    }

    public List<Board> findAllV2() {
        Query q1 = em.createQuery("select b from Board b order by b.id desc", Board.class);
        List<Board> boardList = q1.getResultList();

        Set<Integer> userIds = new HashSet<>();
        for (Board board : boardList) {
            userIds.add(board.getUser().getId());
            System.out.println(userIds);
        }

        Query q2 = em.createQuery("select u from User u where u.id in :userIds", User.class);
        q2.setParameter("userIds", userIds);
        List<User> userList = q2.getResultList();

        for (Board board : boardList) {
            for (User user : userList) {
                if (user.getId() == board.getUser().getId()) {
                    board.setUser(user);
                }
            }
        }

        return boardList;
    }


    // for문을 돌면서 동적 쿼리를 만들어 내는 법
    public List<Board> findAllV3() {
        Query q1 = em.createQuery("select b from Board b order by b.id desc", Board.class);
        List<Board> boardList = q1.getResultList();

        int[] userIds = boardList.stream().mapToInt(board -> board.getUser().getId()).distinct().toArray();

        String q2 = "select u from User u where u.id in (";
        for (int i = 0; i < userIds.length; i++) {
            if (i == userIds.length - 1) {
                q2 = q2 + userIds[i] + ")";
            } else {
                q2 = q2 + userIds[i] + ",";
            }
        }
        List<User> userList = em.createQuery(q2, User.class).getResultList();

        for (Board board : boardList) {
            for (User user : userList) {
                if (user.getId() == board.getUser().getId()) {
                    board.setUser(user);
                }
            }
        }

        return boardList;
    }

    public Board findByIdJoinUser(int id) {
        Query query = em.createQuery("select b from Board b join fetch b.user u where b.id = :id", Board.class);
        query.setParameter("id",id);
        return (Board) query.getSingleResult();
    }


    public Board findById(int id) {
        // id title content userid(이건 어디서 가져옴?) createdAt
        Board board = em.find(Board.class, id);
        return board;
    }


}
