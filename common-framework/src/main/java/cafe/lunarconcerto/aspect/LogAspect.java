package cafe.lunarconcerto.aspect;

import cafe.lunarconcerto.annotation.SystemLog;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LunarConcerto
 * @time 2023/12/29
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(cafe.lunarconcerto.annotation.SystemLog)")
    public void pt(){

    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;

        try {
            handleBefore(joinPoint);
            result = joinPoint.proceed();
            handleAfter(result);
        } finally {

        }

        return result;
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取被增强方法上的注解对象

        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}", request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), ((MethodSignature) joinPoint.getSignature()).getMethod().getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private void handleAfter(Object result) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(result));
        // 结束后换行
        log.info("=======End=======" + System.lineSeparator());
    }

    private SystemLog getSystemLog(@NotNull ProceedingJoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(SystemLog.class);
    }

}
