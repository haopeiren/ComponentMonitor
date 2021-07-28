package com.ffcs.aspect;

import com.ffcs.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志Aspect，打印接口开始和结束日志
 *
 * @author haopeiren
 * @since 2021/7/19
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
    /**
     * 不需要打印的参数类型
     */
    private static final List<Class<?>> EXCLUDE_PARAM_TYPES;

    static {
        List<Class<?>> excludes = new ArrayList<>();
        excludes.add(HttpServletResponse.class);
        excludes.add(MultipartFile.class);
        EXCLUDE_PARAM_TYPES = Collections.unmodifiableList(excludes);
    }

    /**
     * controller切点
     */
    @Pointcut(value = "execution(* com.ffcs.controller.*.*(..))")
    public void executeController() {
    }

    /**
     * 接口运行前后 打印开始和结束日志
     *
     * @param joinPoint joinPoint
     * @throws Throwable Throwable
     */
    @Around("executeController()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] parameters = method.getParameters();
        String methodName = method.getName();
        Object[] args = joinPoint.getArgs();
        StringBuilder startLog = new StringBuilder();
        startLog.append(methodName).append(" start");
        Map<String, Object> paramMap = getParamMap(args, parameters);
        if (!CollectionUtils.isEmpty(paramMap)) {
            startLog.append(", parameters [");
            paramMap.forEach((k, v) -> startLog.append(k).append(":").append(v).append(","));
            startLog.deleteCharAt(startLog.length() - 1);
            startLog.append("]");
        }
        log.info(startLog.toString());
        Object result = joinPoint.proceed();
        log.info(methodName + " end");
        return result;
    }

    private Map<String, Object> getParamMap(Object[] args, Parameter[] parameters) {
        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            Parameter curParam = parameters[i];
            if (!EXCLUDE_PARAM_TYPES.contains(curParam.getType())) {
                Object value = args[i] instanceof String ? args[i] : JsonUtil.objectToString(args[i]);
                paramMap.put(curParam.getName(), value);
            }
        }
        return paramMap;
    }
}
