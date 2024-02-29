package cafe.lunarconcerto.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author LunarConcerto
 * @time 2023/12/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArticleDto {


    @TableId
    private Long id;

    //标题
    private String title;

    //文章正文
    private String content;

    //文章摘要
    private String summary;

    //所属分类id
    private Long categoryId;

    @TableField(exist = false)
    private String categoryName;

    //缩略图
    private String thumbnail;

    //是否置顶（0否，1是）
    private String isTop;

    //状态（0已发布，1草稿）
    private String status;

    //访问量
    private Long viewCount;

    //是否允许评论 1是，0否
    private String isComment;

    /**
     * 作者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后更新者
     */
    private Long updateBy;

    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

    private List<String > tags;

}
