package cafe.lunarconcerto.handler.exception;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.enums.AppHttpCodeEnum;
import cafe.lunarconcerto.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author LunarConcerto
 * @time 2023/12/24
 */
//@ControllerAdvice
//@ResponseBody


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult<?> systemExceptionHandler(SystemException e){
        // 打印异常信息
        log.error("异常: {}", e.toString());

        // 从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult<?> defaultExceptionHandler(Exception e){
        // 打印异常信息
        log.error("异常: {}", e.toString());

        // 从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, e.getMessage());
    }

}
