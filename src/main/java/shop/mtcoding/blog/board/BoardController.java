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

    private final BoardRepository boardRepository;
    private final HttpSession session;

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardRepository.save(requestDTO.toEntity(sessionUser));

        return "redirect:/";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        Board board = boardRepository.findByIdJoinUser(id);
        request.setAttribute("board", board);

        return "board/detail";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        List<Board> boardList = boardRepository.findAll();

        request.setAttribute("boardList", boardList);

        return "index";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "/board/save-form";
    }

    @GetMapping("/board/{id}/update-form")
    public String update(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
        Board board = boardRepository.findById(id);
        request.setAttribute("board", board);

        return "board/update-form";
    }

    @PostMapping("/board/{id}/update")
    public String findById(@PathVariable Integer id, BoardRequest.UpdateDTO requestDTO) {
        boardRepository.updateById(id, requestDTO.getTitle(), requestDTO.getContent());

        return "redirect:/board/" + id;
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id) {
        boardRepository.deleteById(id);

        return "redirect:/";
    }
}
