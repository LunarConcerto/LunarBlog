package cafe.lunarconcerto.controller;

import cafe.lunarconcerto.domain.entity.Category;
import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.domain.vo.CategoryVo;
import cafe.lunarconcerto.domain.vo.ExcelCategoryVo;
import cafe.lunarconcerto.enums.AppHttpCodeEnum;
import cafe.lunarconcerto.service.CategoryService;
import cafe.lunarconcerto.utils.BeanCopyUtils;
import cafe.lunarconcerto.utils.WebUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author LunarConcerto
 * @time 2023/12/30
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult<?> listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response){
        try {
            // 设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);

            // 获取需要导出的数据
            List<Category> categoryList = categoryService.list();

            List<ExcelCategoryVo> data = BeanCopyUtils.copyBeanList(categoryList, ExcelCategoryVo.class);

            // 数据写入Excel
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("分类导出")
                    .doWrite(data);

        } catch (Exception e) {
            // 如果出现异常则响应json
            response.reset();
            ResponseResult<?> result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result), 500);
        }
    }

}
