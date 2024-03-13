package shop.mtcoding.blog.board;

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
    private BoardPersistRepository boardNativeRepository;

    @Test
    public void save_test() {
        // given
        Board board = new Board("제목5", "내용5", "ssar");

        boardNativeRepository.save(board);
        System.out.println("save_test : " + board);

        // then

    }

    @Test
    public void findById_test() {
        // given
        int id = 1;

        // when
        Board board = boardNativeRepository.findById(1);
        // System.out.println("findById_test : " + board);

        assertThat(board.getTitle()).isEqualTo("제목1");
    }

    @Test
    public void findAll_test() {
        // given

        // when
        List<Board> boardList = boardNativeRepository.findAll();

        // then
        System.out.println("findAll_size : " + boardList.size());
        System.out.println("findAll_Username : " + boardList.get(2).getUsername());

        assertThat(boardList.size()).isEqualTo(4);
        assertThat(boardList.get(2).getUsername()).isEqualTo("ssar");
    }

    @Test
    public void deleteById_test() {
        // given
        int id = 1;

        // when
        boardNativeRepository.deleteById(id);

        // then
        List<Board> boardList = boardNativeRepository.findAll();

        // then

        assertThat(boardList.size()).isEqualTo(3);
    }

    @Test
    public void updateById_test() {

        // given
        int id = 1;
        String title = "제목2";
        String content = "승진하";
        String username = "하승진";

        // when
        boardNativeRepository.updateById(id, title, content, username);

        // then
        List<Board> boardList = boardNativeRepository.findAll();

        // then
        Board board = boardNativeRepository.findById(id);
        System.out.println("updateById_test : " + board);
        assertThat(board.getTitle()).isEqualTo("제목2");
        assertThat(board.getCreatedAt()).isEqualTo("승진하");
        assertThat(board.getUsername()).isEqualTo("하승진");
    }
}
