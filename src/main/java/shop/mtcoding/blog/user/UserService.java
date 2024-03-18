package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;

import java.util.Optional;

@RequiredArgsConstructor
@Service // IoC 에 등록된다.
public class UserService {

    // 의존성 주입
    private final UserJAPRepository userJAPRepository;

    public User login(UserRequest.LoginDTO requestDTO) {
        // 해시검사 비교 여기에 들어간다.

        // 내가 ssar 1234 를 넣으면 옵셔널에 값이 들어가고
        // 내가 ssar 12345 를 넣으면 옵셔널에 값이 null 이된다.
        // orElseThrow 은 입셉션을 뛰워줄 수 있다. 값이 null 이면 쓰로우를 날리고 아니면 값을 받는다.
        User sessionUser = userJAPRepository.findByUsernameAndPassword(requestDTO.getUsername(),
                requestDTO.getPassword()).orElseThrow(() -> new Exception401("인증되지 않았습니다."));

        return sessionUser;
    }

    @Transactional
    public void join(UserRequest.JoinDTO requestDTO) {

        // 유저네임 중복검사
        Optional<User> userOP = userJAPRepository.findByUsername(requestDTO.getUsername());

        // 회원가입 할 때 유저 네임이 같은게 있으면 안됨, 비정상.
        if (userOP.isPresent()) {
            throw new Exception400("중복된 유저네임입니다.");
        }

        userJAPRepository.save(requestDTO.toEntity());
    }
}
