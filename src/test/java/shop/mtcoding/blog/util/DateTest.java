package shop.mtcoding.blog.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

public class DateTest {

    @Test
    public void format_test(){
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        // Timestamp를 Date 객체로 변환
        Date currentDate = new Date(currentTimestamp.getTime());

        // 원하는 포맷으로 날짜를 변환
        String formattedDate = DateFormatUtils.format(currentDate, "yyyy-MM-dd HH:mm");

        // 포맷된 날짜 출력
        System.out.println("Formatted Date: " + formattedDate);
    }

    @Test
    public void timestampFormat_test() {
        // given
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        // when
        String createAt = MyDateUtil.timestampFormat(currentTimestamp);

        // then
        System.out.println("timestampFormat_test : " + createAt);
    }
}
