package cafe.lunarconcerto.service;

import cafe.lunarconcerto.domain.vo.CategoryVo;
import cafe.lunarconcerto.domain.entity.Category;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-04-26 11:01:59
 */
public interface CategoryService extends IService<Category> {

    ResponseResult<List<CategoryVo>> getCategoryList();

    List<CategoryVo> listAllCategory();

}

