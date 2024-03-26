package shop.mtcoding.blog.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.board.BoardJAPRepository;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final BoardJAPRepository boardJAPRepository;
    private final ReplyJPARepository replyJPARepository;

    // 댓글쓰기
    @Transactional
    public void save(ReplyRequest.SaveDTO requestDTO, User sessionUser) {
        Board board = boardJAPRepository.findById(requestDTO.getBoardId())
                .orElseThrow(() -> new Exception404("없는 게시글에 댓글을 작성할 수 없습니다."));

        Reply reply = requestDTO.toEntity(sessionUser, board);

        replyJPARepository.save(reply);
    }

    // 댓글삭제
    @Transactional
    public void delete(Integer replyId, Integer sessionUserId) {
        Reply reply = replyJPARepository.findById(replyId)
                .orElseThrow(() -> new Exception404("없는 댓글을 삭제할 수 없습니다."));

        if (sessionUserId != reply.getUser().getId()) {
            throw new Exception403("댓글을 삭제할 권한이 없습니다.");
        }

        replyJPARepository.deleteById(replyId);
    }
}
