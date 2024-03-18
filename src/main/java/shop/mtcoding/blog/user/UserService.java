package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception400;

import java.util.Optional;

@RequiredArgsConstructor
@Service // IoC 에 등록된다.
public class UserService {

    // 의존성 주입
    private final UserJAPRepository userJAPRepository;

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
