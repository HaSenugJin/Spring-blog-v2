package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.board.Board;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final EntityManager em;

    @Transactional
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Transactional
    public User update(int id, UserRequest.UpdateDTO requestDTO) {
        User user = findById(id);
        user.setPassword(requestDTO.getPassword());
        user.setEmail(requestDTO.getEmail());
        return user;
    }

    public User findByUsernameAndPassword(UserRequest.LoginDTO loginDTO) {
        Query query = em.createQuery("select u from User u where u.username = :username and u.password = :password", User.class);
        query.setParameter("username", loginDTO.getUsername());
        query.setParameter("password", loginDTO.getPassword());
        return (User) query.getSingleResult();
    }

    public User findById(int id) {
        User user = em.find(User.class, id);
        return user;
    }
}
