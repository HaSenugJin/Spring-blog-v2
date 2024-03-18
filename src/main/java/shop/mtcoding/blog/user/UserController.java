package shop.mtcoding.blog.user;

import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception500;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserJAPRepository userJAPRepository;
    private final HttpSession session;

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO requestDTO) {
        try {
            userJAPRepository.save(requestDTO.toEntity());
        } catch (DataIntegrityViolationException e) {
            throw new Exception400("동일한 유저네임이 존재합니다.");
        }
        return "redirect:/";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    @GetMapping("/user/update-form")
    public String updateForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Optional<User> userOp = userJAPRepository.findById(sessionUser.getId());
        if (userOp.isPresent()) {
            // 겟으로 받아옴.
            User user = userOp.get();
            request.setAttribute("user", user);
        }
        return "user/update-form";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO requestDTO) {
        try {
            User sessionUser = userJAPRepository.findByUsernameAndPassword(requestDTO.getUsername(), requestDTO.getPassword());
            session.setAttribute("sessionUser", sessionUser);
        } catch (EmptyResultDataAccessException e) {
            throw new Exception401("유저아이디 혹은 비밀번호가 잘못되었습니다.");
        }
        return "redirect:/";
    }

    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        userRepository.update(sessionUser.getId(), requestDTO);
        return "redirect:/";
    }
}
