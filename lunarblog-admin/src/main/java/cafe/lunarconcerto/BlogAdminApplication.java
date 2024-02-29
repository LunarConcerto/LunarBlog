package cafe.lunarconcerto;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LunarConcerto
 * @time 2023/12/29
 */
@SpringBootApplication
@MapperScan("cafe.lunarconcerto.mapper")
public class BlogAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }

}
