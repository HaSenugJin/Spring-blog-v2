package shop.mtcoding.blog.user;

import lombok.Data;

public class UserResponse {

    @Data
    public static class DTO {
        private int id;
        private String username;
        private String email;

        // 응답 DTO 는 생성자를 만들어야 한다.
        // DTO 의 this 가 아니라 Entity 를 DTO 로 바꿔야 한다.
        public DTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }
}
