package com.itheima.controller;


import com.itheima.domain.SysLog;
import com.itheima.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    private Date startTime;//访问时间
    private Class executionClass;//访问类
    private Method executionMethod;//访问的方法


    @Autowired
    private HttpServletRequest request;
    @Autowired
    private LogService logService;


    // 主要获取访问时间、访问的类、访问的方法
    @Before(value = "execution(* com.itheima.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        //当前访问时间
        startTime = new Date();
        //获取当前访问的类
        executionClass = jp.getTarget().getClass();
        //获取当前访问的方法
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        if (args == null || args.length == 0){

            executionMethod = executionClass.getMethod(methodName);
        }else {
            //有参数,遍历取出参数
            Class[] methodArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                methodArgs[i] = args[i].getClass();
            }
            executionMethod = executionClass.getMethod(methodName,methodArgs);
        }

    }


    // 主要获取日志中其它信息，时长、ip、url...
    @After(value = "execution(* com.itheima.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {
        //获取访问总时长
        long time = new Date().getTime() - startTime.getTime();
        //获取访问url,获取类上的注解
        String url = "";
        if ( executionClass != LogAop.class){
            //不是日志类的访问路径,获取注解上的value
            RequestMapping classAnnotation = (RequestMapping) executionClass.getAnnotation(RequestMapping.class);
            if (classAnnotation != null){
                RequestMapping methodAnnotation = executionMethod.getAnnotation(RequestMapping.class);
                url = classAnnotation.value()[0]+methodAnnotation.value()[0];


                //获取用户名
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String username = user.getUsername();

                //获取访问ip
                String add = request.getRemoteAddr();
                SysLog sysLog = new SysLog();
                sysLog.setIp(add);
                sysLog.setExecutionTime(time);
                sysLog.setMethod("[类名] "+executionClass.getName()+"[方法名] " +executionMethod.getName());
                sysLog.setUsername(username);
                sysLog.setVisitTime(startTime);
                sysLog.setUrl(url);

                //保存日志
                logService.saveLog(sysLog);
            }
        }



    }
}
