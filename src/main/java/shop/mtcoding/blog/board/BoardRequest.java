package shop.mtcoding.blog.board;

import lombok.Data;
import shop.mtcoding.blog.user.User;

public class BoardRequest {

    @Data
    public static class SaveDTO {
        private String title;
        private String content;

        // dto 를 클라이언트로 부터 받아서 pc(퍼시스트 컨텍스트)에 전달하기 위해 사용
        public Board toEntity(User user) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user).build();
        }
    }

    @Data
    public static class UpdateDTO {
        private String title;
        private String content;
    }
}
