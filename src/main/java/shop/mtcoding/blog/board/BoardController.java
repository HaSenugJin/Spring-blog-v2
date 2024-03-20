package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.utils.ApiUtil;
import shop.mtcoding.blog.user.User;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    @GetMapping("/")
    public ResponseEntity<?> main() {
        List<BoardResponse.MainDTO> requestDTO = boardService.findAll();
        return ResponseEntity.ok(new ApiUtil(requestDTO));
    }

    @GetMapping("/api/boards/{id}")
    public ResponseEntity<?> findOne(@PathVariable Integer id) {
        Board board = boardService.updateForm(id);
        return ResponseEntity.ok(new ApiUtil(board));
    }

    @GetMapping("/api/boards/{id}/detail")
    public ResponseEntity<?> detail(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.findByJoinUser(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil(board));
    }

    @PostMapping("/api/boards")
    public ResponseEntity<?> save(@RequestBody BoardRequest.SaveDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.save(requestDTO, sessionUser);

        return ResponseEntity.ok(new ApiUtil(board));
    }

    @PutMapping("/api/boards/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id, @RequestBody BoardRequest.UpdateDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.update(id, sessionUser.getId(), requestDTO);

        return ResponseEntity.ok(new ApiUtil(board));
    }

    // SSR(서버 사이드 렌더링)은 DTO 를 만들지 않아도 된다. 필요한 데이터만 렌더링해서 클라이언트에게
    // 전달할 것이니까.

    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.delete(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil(null));
    }
}