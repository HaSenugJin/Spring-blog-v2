package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.swing.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(BoardPersistRepository.class)
@DataJpaTest
public class BoardPersistRepositoryTest {

    @Autowired
    private BoardPersistRepository boardPersistRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void deleteById2_test() {

        // 찾아서 딜리트 하였으나 딜리트는 트랜잭션이 종료될 때 쿼리가 날아간다.
        // 하지만 쿼리가 발동하지 않는데 이는 트랜잭션이 자식 트랜잭션이라 그렇다
        // DataJpaTest 가 트랜잭션을 가지고 있는데, deleteById가 또 트랜잭션을 가지고 있음
        // 그러면 DataJpaTest 의 트랜잭션만 실행되고 딜리트의 트랜잭션은 무시된다.
        // 그래서 딜리트의 트랜잭션이 무시되었으니 트랜잭션이 종료되지 않아서 딜리트가 실행이 안됨
        // 삭제만 이렇다
        int id = 1;
        boardPersistRepository.deleteById2(id);

        // 쿼리를 강제로 날려서 테스트 해볼 수 있다.
        // 플러쉬 해서 강제로 버퍼로 쿼리내용을 흘려보낸다.
        em.flush();
    }

    @Test
    public void deleteById1_test() {
        int id = 1;
        boardPersistRepository.deleteById(id);
    }

    @Test
    public void save_test() {
        // given
        Board board = new Board("제목5", "내용5", "ssar");

        boardPersistRepository.save(board);
        System.out.println("save_test : " + board);

        // then
    }

    @Test
    public void findAll_test() {
        // given

        // when
        List<Board> boardList = boardPersistRepository.findAll();

        // then
        System.out.println("findAll_size : " + boardList.size());

        assertThat(boardList.size()).isEqualTo(4);
    }

    @Test
    public void findById_test() {
        Integer id = 1;

        Board board = boardPersistRepository.findById(id);
        em.clear();
        boardPersistRepository.findById(id);
    }

    @Test
    public void updateById_test() {
        // given
        Integer id = 1;
        String title = "제목수정1";

        // when
        Board board = boardPersistRepository.findById(id);
        board.setTitle(title);
        em.flush();
    }
}
