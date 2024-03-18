package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

import javax.swing.plaf.PanelUI;
import java.util.Optional;

@RequiredArgsConstructor
@Service // IoC 에 등록된다.
public class BoardService {

    public final BoardJAPRepository boardJAPRepository;

    @Transactional
    public void save(BoardRequest.SaveDTO requestDTO, User sessionUser) {
        boardJAPRepository.save(requestDTO.toEntity(sessionUser));
    }

    @Transactional
    public Board update(int boardId, int sessionUserId, BoardRequest.UpdateDTO requestDTO) {

        // 조회 및 예외처리
        Board board = boardJAPRepository.findById(boardId).orElseThrow(()
                -> new Exception404("게시글을 찾을 수 없습니다."));

        // 권한 처리
        if (sessionUserId != board.getUser().getId()) {
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }

        // 글수정
        board.setTitle(requestDTO.getTitle());
        board.setContent(requestDTO.getContent());

        return board;
    } // 더티체킹

    public Board updateForm(int boardId, int sessionUserId) {

        // 조회 및 예외처리
        Board board = boardJAPRepository.findById(boardId).orElseThrow(()
                -> new Exception404("게시글을 찾을 수 없습니다."));

        // 권한 처리
        if (sessionUserId != board.getUser().getId()) {
            throw new Exception403("게시글 수정페이지로 이동할 권한이 없습니다.");
        }

        return board;
    }
}
