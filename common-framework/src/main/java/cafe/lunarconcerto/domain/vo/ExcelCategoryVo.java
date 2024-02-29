package cafe.lunarconcerto.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelCategoryVo {

    @ExcelProperty("字符串标题")
    private String name ;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("状态0：正常, 1：禁用")
    private String status;

}
