package shop.mtcoding.blog.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "user_tb")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    // 해쉬로 바꾸면 문자의 개수가 늘어나니 숫자 제한을 12자로 하면 안된다.
    private String password;
    private String email;

    @CreationTimestamp // insert 될 때 날짜를 자동으로 넣어준다.
    private Timestamp createdAt;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
