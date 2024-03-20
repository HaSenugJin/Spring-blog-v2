package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.utils.ApiUtil;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    // 회원정보 조회
    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> userinfo(@PathVariable Integer id) {
        UserResponse.DTO requestDTO = userService.findById(id);
        return ResponseEntity.ok(new ApiUtil(requestDTO));
    }

    // 아이디를 언제 받아와야 하는가 관리자가 로그인 해서 바꿀 수 있게 하려면 필요하다.
    // 하지만 프론트엔드 입장에서는 주소를 이렇게 적는편이 알기 쉽고 좋다.
    // 회원정보 수정
    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UserRequest.UpdateDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User newssionUser = userService.update(sessionUser.getId(), requestDTO);
        session.setAttribute("sessionUser", newssionUser);
        return ResponseEntity.ok(new ApiUtil(newssionUser));
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO requestDTO) {
        User user = userService.join(requestDTO);
        return ResponseEntity.ok(new ApiUtil(user));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO requestDTO) {
        User sessionUser = userService.login(requestDTO);
        session.setAttribute("sessionUser", sessionUser);
        return ResponseEntity.ok(new ApiUtil(null));
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        session.invalidate();
        return ResponseEntity.ok(new ApiUtil(null));
    }
}
