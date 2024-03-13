package shop.mtcoding.blog.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(BoardRepository.class)
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void findById_test() {
        int id = 1;

        Board board = boardRepository.findById(id);
        System.out.println(board.getUser().getUsername());
    }

    @Test
    public void findByJoinId_test() {
        int id = 1;

        Board board = boardRepository.findByIdJoinUser(id);
    }
}