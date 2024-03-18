package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog.user.User;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardJAPRepository boardJAPRepository;
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
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.updateForm(id, sessionUser.getId());
        request.setAttribute("board", board);
        return "board/update-form";
    }

    @PostMapping("/board/{id}/update")
    public String findById(@PathVariable Integer id, BoardRequest.UpdateDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.update(id, sessionUser.getId(), requestDTO);

        return "redirect:/board/" + id;
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Optional<Board> board = boardJAPRepository.findByIdJoinUser(id);

        // 로그인을 하고 게시글의 주인이면 isOwner 가 true 가 된다.
        boolean isOwner = false;
        if (sessionUser != null) {
            if (sessionUser.getId() == board.get().getUser().getId()) {
                isOwner = true;
            }
        }

        request.setAttribute("isOwner", isOwner);
        request.setAttribute("board", board);

        return "board/detail";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        List<Board> boardList = boardJAPRepository.findAll(sort);

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
        Optional<Board> boardOp = boardJAPRepository.findById(id);

        if (sessionUser.getId() != boardOp.get().getUser().getId()) {
            throw new Exception403("게시글을 삭제할 권한이 없습니다.");
        }

        if (boardOp.isPresent()) {
            Board board = boardOp.get();
            System.out.println("findById_test : " + board.getTitle());
            boardRepository.deleteById(id);
        }

        return "redirect:/";
    }
}
