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
    
    // TODO: 글 상세보기 API 필요
    // TODO: 글 목록조회 API 필요
    // TODO: 글 목록조회 API 필요

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.save(requestDTO, sessionUser);

        return "redirect:/";
    }

    @PostMapping("/board/{id}/update")
    public String findById(@PathVariable Integer id, BoardRequest.UpdateDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.update(id, sessionUser.getId(), requestDTO);

        return "redirect:/board/" + id;
    }

    // SSR(서버 사이드 렌더링)은 DTO 를 만들지 않아도 된다. 필요한 데이터만 렌더링해서 클라이언트에게
    // 전달할 것이니까.


    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.delete(id, sessionUser.getId());
        return "redirect:/";
    }
}