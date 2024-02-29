package cafe.lunarconcerto.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LunarConcerto
 * @time 2023/12/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {

    private Long id;

    //标签名
    private String name;

}
