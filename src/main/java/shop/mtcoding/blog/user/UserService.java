package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception404;

import java.util.Optional;

@RequiredArgsConstructor
@Service // IoC 에 등록된다.
public class UserService {

    // 의존성 주입
    private final UserJAPRepository userJAPRepository;

    // 회원정보 조회
    public UserResponse.DTO findById(int id) {
        User user = userJAPRepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다"));
        return new UserResponse.DTO(user); // 엔티티 생명 종료
    }


    // 회원정보 수정
    @Transactional
    public User update(int id, UserRequest.UpdateDTO requestDTO) {
        User user = userJAPRepository.findById(id).orElseThrow(()
                -> new Exception404("회원정보를 찾을 수 없습니다."));
        user.setPassword(requestDTO.getPassword());
        user.setEmail(requestDTO.getEmail());

        return user;
    } // 더티체킹

    // 로그인
    public User login(UserRequest.LoginDTO requestDTO) {
        // 해시검사 비교 여기에 들어간다.

        // 내가 ssar 1234 를 넣으면 옵셔널에 값이 들어가고
        // 내가 ssar 12345 를 넣으면 옵셔널에 값이 null 이된다.
        // orElseThrow 은 입셉션을 뛰워줄 수 있다. 값이 null 이면 쓰로우를 날리고 아니면 값을 받는다.
        User sessionUser = userJAPRepository.findByUsernameAndPassword(requestDTO.getUsername(),
                requestDTO.getPassword()).orElseThrow(() -> new Exception401("인증되지 않았습니다."));

        return sessionUser;
    }

    // 회원가입
    @Transactional
    public User join(UserRequest.JoinDTO requestDTO) {

        // 유저네임 중복검사
        // 여기에 orElseThrow 를 못쓰는 이유는 찾았을 때 쓰로우를 날려야 하기 때문 위랑 다르다
        Optional<User> userOP = userJAPRepository.findByUsername(requestDTO.getUsername());

        // 회원가입 할 때 유저 네임이 같은게 있으면 안됨, 비정상.
        if (userOP.isPresent()) {
            throw new Exception400("중복된 유저네임입니다.");
        }

        return userJAPRepository.save(requestDTO.toEntity());
    }
}
