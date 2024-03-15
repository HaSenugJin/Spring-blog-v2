package shop.mtcoding.blog.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import java.util.List;

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

    @Test
    public void findAll_lazyLoading_test() {
        List<Board> boardList = boardRepository.findAll();
        boardList.forEach(board -> {
            System.out.println(board.getUser().getUsername());
        });

    }

    @Test
    public void findAll_test() {
        // given
        // when
        boardRepository.findAll();
        // then
    }

    @Test
    public void findAll_custom_inquery_test() {
        List<Board> boardList = boardRepository.findAll();

        int[] userIds = boardList.stream().mapToInt(board -> board.getUser().getId()).distinct().toArray();
        for (int i : userIds){
            System.out.println(i);
        }

        // select * from user_tb where id in (3,2,1,1);
        // select * from user_tb where id in (3,2,1);
    }

//    @Test
//    public void filter_test() {
//        List<Integer> integerList1 = Arrays.asList(1, 2, 3);
//        List<Integer> integerList2 = Arrays.asList(1, 2, 3);
//        List<String> stringList = Arrays.asList("ssar", "cos", "love");
//
//        for (Integer in : integerList1) {
//            List<Integer> integerList = integerList1.stream().filter(in1 -> in1 <= in).toList();
//        }
//    }

    @Test
    public void findAll2_test() {
        List<Board> boardList = boardRepository.findAllV2();
        System.out.println("findAllV2_test : 조회완료 쿼리 2번");
        boardList.forEach(board -> {
            System.out.println(board);
        });
    }

    @Test
    public void findAll3_test() {
        List<Board> boardList = boardRepository.findAllV3();
        System.out.println("findAllV2_test : 조회완료 쿼리 2번");
        boardList.forEach(board -> {
            System.out.println(board);
        });
    }


    @Test
    public void randomquery_test(){
        int[] ids = {1,2};

        // select u from User u where u.id in (?,?);
        String q = "select u from User u where u.id in (";
        for (int i=0; i<ids.length; i++){
            if(i==ids.length-1){
                q = q + "?)";
            }else{
                q = q + "?,";
            }
        }
        System.out.println(q);
    }

    @Test
    public void deleteById_test(){
        // given
        int id = 1;

        // when
        // 내가 쿼리를 직접 적었으니까 플러쉬 안해도 된다.
        boardRepository.deleteById(id);

        // then
        System.out.println(boardRepository.findAll().size());
    }
}