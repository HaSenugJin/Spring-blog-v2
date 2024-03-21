package shop.mtcoding.blog.board;

import lombok.Data;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;

import java.util.ArrayList;
import java.util.List;

public class BoardResponse {

    // 게시글 상세보기 화면
    @Data
    public static class DetailDTO {
        private int id;
        private String title;
        private String content;
        private int userId; // 게시글 작성자 아이디
        private String username; // 게시글 작성자 이름
        private boolean isOwner;

        // 못찾으면 초기화만 시켜서 빈 배열로 만들어야 오류가 안난다.
        private List<ReplyDTO> replies = new ArrayList<>();


        public DetailDTO(Board board, User sessionUser) {
            this.id = board.getId();
            this.title = board.getTitle();;
            this.content = board.getContent();
            this.userId = board.getUser().getId();
            this.username = board.getUser().getUsername();
            this.isOwner = false;
            if (sessionUser != null) {
                if (sessionUser.getId() == userId) {
                    this.isOwner = true;
                }
            }

            this.replies = board.getReplies().stream().map(reply -> new ReplyDTO(reply, sessionUser)).toList();
        }

        @Data
        public class ReplyDTO {
            private int id;
            private String comment;
            private int userId; // 댓글 작성자 아이디
            private String username; // 댓글 작성자 이름
            private boolean isOwner;

            public ReplyDTO(Reply reply, User sessionUser) {
                this.id = reply.getId(); // 레이지 로딩 발동
                this.comment = reply.getComment();
                this.userId = reply.getUser().getId();
                this.username = reply.getUser().getUsername();
                this.isOwner = false;
                if (sessionUser != null) {
                    if (sessionUser.getId() == userId) {
                        this.isOwner = true;
                    }
                }
            }
        }
    }

    // 게시글 목록보기 화면
    @Data
    public static class MainDTO {
        private int id;
        private String title;

        public MainDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
        }
    }
}