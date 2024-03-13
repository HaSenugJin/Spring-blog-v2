package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    @GetMapping("/")
    public String index() {


        return "index";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "/board/save-form";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {


        return "board/detail";
    }

    @GetMapping("/board/{id}/update-form")
    public String update(@PathVariable(name = "id") Integer id, HttpServletRequest request) {

        return "board/update-form";
    }

    @PostMapping("/board/save")
    public String save() {


        return "redirect:/";
    }

    @PostMapping("/board/{id}/update")
    public String findById(@PathVariable Integer id) {



        return "redirect:/board/" + id;
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id) {


        return "redirect:/";
    }
}
