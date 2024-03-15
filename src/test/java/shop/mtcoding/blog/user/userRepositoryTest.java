package shop.mtcoding.blog.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.stream.Stream;

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

    @Test
    public void save_test() {
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setUsername("user");
        joinDTO.setPassword("1234");
        joinDTO.setEmail("egdg@g");

        User user = joinDTO.toEntity();
        userRepository.save(user);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getEmail());
        System.out.println(user.getId());
    }
}
