package com.wcf.server.base.log;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     * web层切点
     * 1. @Pointcut("execution(public * com.sd.erp.controller.*.*(..))")  web层的所有方法
     * 2. @Pointcut("@annotation(com.sd.erp.log.Log)")      Log注解标注的方法
     */
    @Pointcut("@annotation(com.wcf.server.base.log.Log)")
    public void webLog() {
    }

    /**
     * 环绕通知
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取请求对象
        HttpServletRequest request = getRequest();
        WebLog webLog = new WebLog();
        Object result;
        try {
            // 执行任务前
            long start = System.currentTimeMillis();
            // 获取Log注解
            Log logAnnotation = getAnnotation(joinPoint);
            // 封装webLog对象
            webLog.setOperation(logAnnotation.value());
            webLog.setIpAddress(request.getRemoteAddr());
            webLog.setUri(request.getRequestURI());
            webLog.setHttpMethod(request.getMethod());
            webLog.setParams("hide");
            if (logAnnotation.saveParams()) {
                webLog.setParams(getParams(joinPoint));
            }
            webLog.setStartTime(start);
            webLog.setSuccess(false);
            webLog.setUrl(request.getRequestURL().toString());
            // 执行任务
            result = joinPoint.proceed();
            // 任务执行完毕
            webLog.setResult("hide");
            if (logAnnotation.saveResult()) {
                webLog.setResult(result);
            }
            long timeCost = System.currentTimeMillis() - start;
            webLog.setTimeCost((int) timeCost);
            webLog.setSuccess(true);
        } catch (Throwable e) {
            log.info("==================异常通知=====================");
            log.error(e.getMessage());
            throw new Throwable(e);
        } finally {
            log.info("{}", webLog);
        }
        return result;
    }

    /**
     * 获取方法上的注解
     */
    private Log getAnnotation(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        return method.getAnnotation(Log.class);
    }

    /**
     * 获取参数
     */
    private Object getParams(ProceedingJoinPoint joinPoint) {
        // 参数名
        String[] paramNames = getMethodSignature(joinPoint).getParameterNames();
        // 参数值
        Object[] paramValues = joinPoint.getArgs();
        // 存储参数
        Map<String, Object> params = new LinkedHashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];
            // MultipartFile对象以文件名作为参数值
            if (value instanceof MultipartFile file) {
                value = file.getOriginalFilename();
            }
            params.put(paramNames[i], value);
        }
        return params;
    }

    private MethodSignature getMethodSignature(ProceedingJoinPoint joinPoint) {
        return (MethodSignature) joinPoint.getSignature();
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        return requestAttributes.getRequest();
    }
}
