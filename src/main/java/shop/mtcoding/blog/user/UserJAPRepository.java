package shop.mtcoding.blog.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// 자동 컴퍼넌트 스캔이 된다.
// findAll, findById, deleteById, save 네가지만 기본적으로 제공한다.
public interface UserJAPRepository extends JpaRepository<User, Integer> {

    // 추상 메서드 생성
    // 기본제공 해주지 않으니 직접 만들어야 한다.
    // 적어둔걸로 ? 걸고(where 절에) 카멜표기법으로 구분해서 지가 만들어줌
    // @Query("select u from User u where u.username = :username and u.password = :password")
    // Optional<User> null 체크 하려고 적어둠
    Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    Optional<User> findByUsername(@Param("username") String username);
}