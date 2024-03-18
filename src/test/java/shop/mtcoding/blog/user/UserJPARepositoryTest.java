package shop.mtcoding.blog.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

// 임포트가 필요없다. (자동으로 해줌 하지만 적어도 문제는 없음)
@DataJpaTest
public class UserJPARepositoryTest {

    @Autowired
    private UserJAPRepository userJAPRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void save_test(){
        // given
        User user = User.builder()
                .username("happy")
                .password("1234")
                .email("happy@nate.com")
                .build();

        // when
        userJAPRepository.save(user);

        // then
    }

    @Test
    public void findById_test(){
        // given
        int id = 5;

        // when

        // 존재하지 않는값이 온다면 널을 받는다.
        Optional<User> userOp =  userJAPRepository.findById(id);

        // 만약에 존재 한다면
        if (userOp.isPresent()) {
            // 겟으로 받아옴.
            User user = userOp.get();
            System.out.println("findById_test : " + user.getUsername());
        }

        // then
    }

    @Test
    public void findAll_test(){
        // given

        // when
        userJAPRepository.findAll();

        // then
    }

    @Test
    public void findAll_orderBy_test(){
        // given


        // when
        userJAPRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        // then
    }

    @Test
    public void findAll_paging_test() throws JsonProcessingException {
        // given

        // when
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 2, sort);
        Page<User> userPG = userJAPRepository.findAll(pageable);
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(userPG);
        System.out.println(json);

        // then
    }
    
    @Test
    public void findByUsernameAndPassword_test(){
        // given
        String username = "ssar";
        String password = "1234";

        // when
        userJAPRepository.findByUsernameAndPassword(username, password);

        // then
    }

    @Test
    public void update_test(){
        // given
        User user = User.builder()
                .id(1)
                .username("happy")
                .password("1234")
                .email("happy@nate.com")
                .build();

        // when
        userJAPRepository.save(user);
        em.flush();

        // then
    }
}
