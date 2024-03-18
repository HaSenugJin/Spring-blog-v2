package shop.mtcoding.blog.board;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.user.User;


import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "board_tb")
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;

    // orm
    // @JoinColumn(name = "user_id") // db에 만들어질 이름을 지정할 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // user_id 유저객체에 있는 pk를 잡아서 필드를 만들어준다.

    @CreationTimestamp // insert 될 때 날짜를 자동으로 넣어준다.
    private Timestamp createdAt;

    @Transient //테이블 생성될 때 제외된다.
    private Boolean isOwner;

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }
}
