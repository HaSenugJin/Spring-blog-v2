package shop.mtcoding.blog.board;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    // 매니투원은 이걸이 디폴트 전략이다.
    // orm
    // @JoinColumn(name = "user_id") // db에 만들어질 이름을 지정할 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // user_id 유저객체에 있는 pk를 잡아서 필드를 만들어준다.

    @CreationTimestamp // insert 될 때 날짜를 자동으로 넣어준다.
    private Timestamp createdAt;

    // 원투매니는 레이지가 디폴트 전략이다.
    // 댓글이 없어도 크기는 0으로 유지하게 new 해놔야함 안그러면 for 문에서 터짐
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

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
