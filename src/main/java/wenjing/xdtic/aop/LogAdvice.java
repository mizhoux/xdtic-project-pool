package wenjing.xdtic.aop;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 日志记录的切面
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Aspect
@Component
public class LogAdvice {

    // 被 Log 标记的类或者方法会被该切面拦截
    @Pointcut("@within(wenjing.xdtic.aop.Log)")
    public void advice() {

    }

    @Around("advice()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        System.out.println("call   -> " + getMethodName(pjp));
        System.out.println("args   -> " + getMethodArgValues(pjp));

        Object result = pjp.proceed();

        System.out.println("return -> " + result + "\n");

        return result;
    }

    private String getMethodName(JoinPoint jp) {
        Object target = jp.getTarget();
        String className = target.getClass().getSimpleName();

        MethodSignature signature = (MethodSignature) jp.getSignature();
        String methodName = signature.getName();
        //String returnTypeName = signature.getReturnType().getSimpleName();

        Class<?>[] paramTypes = signature.getParameterTypes();
        String[] paramNames = signature.getParameterNames();
        String params = IntStream.range(0, paramNames.length)
                .mapToObj(i -> paramTypes[i].getSimpleName() + " " + paramNames[i])
                .collect(Collectors.joining(", ", "(", ")"));

        return className + "." + methodName + params;
    }

    private String getMethodArgValues(JoinPoint jp) {

        MethodSignature signature = (MethodSignature) jp.getSignature();
        String[] paramNames = signature.getParameterNames();

        Object[] args = jp.getArgs();
        String argValues = IntStream.range(0, args.length)
                .mapToObj(i -> paramNames[i] + " = " + args[i])
                .collect(Collectors.joining("; "));

        return argValues;
    }

}
