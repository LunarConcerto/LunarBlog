package cafe.lunarconcerto.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LunarConcerto
 * @time 2023/12/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUserLoginVo {

    private String token;
    private UserInfoVo userInfo;
}