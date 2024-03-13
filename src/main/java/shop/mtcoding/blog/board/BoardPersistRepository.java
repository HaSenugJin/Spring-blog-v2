package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardPersistRepository {
    private final EntityManager em;

    public Board findById(Integer id) {
        Board board = em.find(Board.class, id);

        return board;
    }

    public List<Board> findAll() {
        Query query = em.createQuery("select b from Board b order by b.id desc", Board.class);

        return query.getResultList();
    }

    @Transactional
    public Board save(Board board) {

        // 1. 비영속 객체 생성
        em.persist(board);

        // board -> 영속 객체
        return board;
    }

    @Transactional
    public void deleteById(Integer id) {
        // 방법 1
        // 쿼리를 직접 작성
        Query query = em.createQuery("delete from Board b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Transactional
    public void deleteById2(Integer id) {
        // 방법 2
        // 조회하고 삭제
        Board board = findById(id); // id로 조회를 먼저 해주고
        em.remove(board); // PC에 객체 지우고, 트랜잭션 종료시에 삭제 쿼리가 날아간다.
        // 비즈니스 로직은 서비스에서 처리하는게 좋다.
        // 그래서 쌤은 방법 1을 좋아한다 하심
    }

    @Transactional
    public void updateById(Integer id, BoardRequest.UpdateDTO requestDTO) {
        Board board = findById(id);
        board.update(requestDTO);
    }
}