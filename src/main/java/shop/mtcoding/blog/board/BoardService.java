package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service // IoC 에 등록된다.
public class BoardService {

    private final BoardJAPRepository boardJAPRepository;

    // 글 쓰기
    @Transactional
    public Board save(BoardRequest.SaveDTO requestDTO, User sessionUser) {
        return boardJAPRepository.save(requestDTO.toEntity(sessionUser));
    }

    // 글 수정
    @Transactional
    public Board update(Integer boardId, Integer sessionUserId, BoardRequest.UpdateDTO requestDTO) {

        // 조회 및 예외처리
        Board board = boardJAPRepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        // 권한 처리
        if (sessionUserId != board.getUser().getId()) {
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }

        // 글수정
        board.setTitle(requestDTO.getTitle());
        board.setContent(requestDTO.getContent());

        return board;
    } // 더티체킹

    // 글 수정 조회
    public Board updateForm(Integer boardId) {
        // 조회 및 예외처리
        Board board = boardJAPRepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        return board;
    }

    // 글삭제
    @Transactional
    public void delete(Integer boardId, Integer sessionUserId) {

        // 트랜잭션으로 묶여있으니 만약 딜리트를 먼저해도 문제는 없다.
        Board board = boardJAPRepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        if (sessionUserId != board.getUser().getId()) {
            throw new Exception403("게시글을 삭제할 권한이 없습니다.");
        }

        boardJAPRepository.deleteById(boardId);
    }

    // 글 목록 조회
    public List<Board> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return boardJAPRepository.findAll(sort);
    }

    // 글 상세보기
    public Board findByJoinUser(Integer boardId, User sessionUser) {
        Board board = boardJAPRepository.findByIdJoinUser(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));

        boolean isBoardOwner = false;
        if(sessionUser != null){
            if(sessionUser.getId() == board.getUser().getId()){
                isBoardOwner = true;
            }
        }

        board.setIsBoardOwner(isBoardOwner);

        board.getReplies().forEach(reply -> {
            boolean isReplyOwner = false;
            if (sessionUser != null) {
                if (reply.getUser().getId() == sessionUser.getId()) {
                    isReplyOwner = true;
                }
            }
            reply.setReplyOwner(isReplyOwner);
        });

        return board;
    }
}
