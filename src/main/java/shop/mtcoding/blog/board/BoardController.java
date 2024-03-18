package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.user.User;
import java.util.List;


@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.save(requestDTO, sessionUser);

        return "redirect:/";
    }

    @GetMapping("/board/{id}/update-form")
    public String update(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
        Board board = boardService.updateForm(id);
        request.setAttribute("board", board);
        return "board/update-form";
    }

    @PostMapping("/board/{id}/update")
    public String findById(@PathVariable Integer id, BoardRequest.UpdateDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.update(id, sessionUser.getId(), requestDTO);

        return "redirect:/board/" + id;
    }

    // SSR(서버 사이드 렌더링)은 DTO 를 만들지 않아도 된다. 필요한 데이터만 렌더링해서 클라이언트에게
    // 전달할 것이니까.
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.findByJoinUser(id, sessionUser);

        request.setAttribute("board", board);
        return "board/detail";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        List<Board> boardList = boardService.findAll();
        request.setAttribute("boardList", boardList);

        return "index";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "/board/save-form";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.delete(id, sessionUser.getId());
        return "redirect:/";
    }
}