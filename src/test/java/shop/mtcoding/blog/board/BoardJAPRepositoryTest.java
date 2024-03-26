package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import shop.mtcoding.blog.user.User;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class BoardJAPRepositoryTest {

    @Autowired
    private BoardJAPRepository boardJAPRepository;

    @Autowired
    private EntityManager em;

    // save
    @Test
    public void save_test(){
        // given
        User sessionUser = User.builder().id(1).build();
        Board board = Board.builder()
                .title("제목5")
                .content("내용5")
                .user(sessionUser)
                .build();
        // when
        boardJAPRepository.save(board);

        // then
        System.out.println("save_test : " + board.getId());
    }

    // findById
    @Test
    public void findById_test(){
        // given
        int id = 1;

        // when

        // 존재하지 않는값이 온다면 널을 받는다.
        Optional<Board> boardOp =  boardJAPRepository.findById(id);

        // 만약에 존재 한다면
        if (boardOp.isPresent()) {
            // 겟으로 받아옴.
            Board board = boardOp.get();
            System.out.println("findById_test : " + board.getTitle());
        }

        // then
    }

    // findByIdJoinUser
    @Test
    public void findByIdJoinUser_test(){
        // given
        int id = 4;

        // when
        boardJAPRepository.findByIdJoinUser(id).get();

        // then
    }


    // findAll (sort)
    @Test
    public void findAll_orderBy_test(){
        // given
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        // when
        List<Board> boardList = boardJAPRepository.findAll(sort);

        // then
        // System.out.println("findAll_test : " + boardList);
    }

    // deleteById
    @Test
    public void deleteById_test(){
        // given
        int id = 1;

        // when
        boardJAPRepository.deleteById(id);
        em.flush();

        // then

    }

    // findAll (pageable)
}
