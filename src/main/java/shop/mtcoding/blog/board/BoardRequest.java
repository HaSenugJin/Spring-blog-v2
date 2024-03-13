package shop.mtcoding.blog.board;

import lombok.Data;

public class BoardRequest {

    @Data
    public static class SaveDTO {
        private String title;
        private String content;
        private String username;

        // DTO를 엔티티로 만들어준다
        // entity로 바꿔서 인설트 할때만 쓸것
        public Board toEntity() {
            return new Board(title, content, username);
        }
    }
}
