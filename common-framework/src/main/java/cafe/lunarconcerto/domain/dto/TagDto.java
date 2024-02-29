package cafe.lunarconcerto.domain.dto;

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
public class TagDto {

    private Long id;

    private String name;

    private String remark;

}
