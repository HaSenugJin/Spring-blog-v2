package shop.mtcoding.blog.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserRepository.class)
@DataJpaTest // db만 테스트 하면되니까 Datasource(connection pool), EntityManager 두개만 뛰운다.
public class userRepositoryTest {

    @Autowired // 이 어노테이션을 써주면?
    private UserRepository userRepository;

    @Test
    public void findByUsername_test(){
        UserRequest.LoginDTO reqDTO = new UserRequest.LoginDTO();

        reqDTO.setUsername("ssar");
        reqDTO.setPassword("1234");
        User user = userRepository.findByUsernameAndPassword(reqDTO);
    }
}
