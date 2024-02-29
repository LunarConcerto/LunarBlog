package cafe.lunarconcerto.domain.vo;

import cafe.lunarconcerto.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LunarConcerto
 * @time 2023/12/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {

    private List<Menu> menus;

}
