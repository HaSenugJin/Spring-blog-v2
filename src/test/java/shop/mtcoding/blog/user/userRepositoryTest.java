package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserRepository.class)
@DataJpaTest // db만 테스트 하면되니까 Datasource(connection pool), EntityManager 두개만 뛰운다.
public class userRepositoryTest {

    @Autowired // 이 어노테이션을 써주면?
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void findByUsername_test(){
        UserRequest.LoginDTO reqDTO = new UserRequest.LoginDTO();

        reqDTO.setUsername("ssar");
        reqDTO.setPassword("1234");
        User user = userRepository.findByUsernameAndPassword(reqDTO);
    }

    @Test
    public void findById_test(){
        // given
        int id = 1;

        // when
        userRepository.findById(id);

        // then
    }

    @Test
    public void update_test(){
        // given
        UserRequest.UpdateDTO reqDTO = new UserRequest.UpdateDTO();
        int id = 1;
        reqDTO.setPassword("4587");
        reqDTO.setEmail("ssar@naver.com");

        // when
        userRepository.update(id, reqDTO);
        em.flush();
        // then
    }
}
