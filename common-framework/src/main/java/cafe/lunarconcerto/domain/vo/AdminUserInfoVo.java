package cafe.lunarconcerto.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author LunarConcerto
 * @time 2023/12/29
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVo {

    private List<String> permissions;

    private List<String> roles;

    private UserInfoVo user;

}
