package shop.mtcoding.blog.Reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.mtcoding.blog.reply.ReplyJPARepository;

@DataJpaTest
public class ReplyJAPRepositoryTest {

    @Autowired
    private ReplyJPARepository replyJPARepository;


    

}
