package wenjing.xdtic.aop;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 记录 Controller 中方法执行的切面
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Aspect
@Component
public class ControllerLogger {

    @Pointcut("execution(* wenjing.xdtic.controller.FunctionController.*(..))")
    public void advice() {

    }

    @Before("advice()")
    public void doBefore(JoinPoint jp) {
        String methodName = getMethodName(jp);
        System.out.println("call method: " + methodName);
    }

    public void doAround() {
    }

    @After("advice()")
    public void doAfter(JoinPoint jp) {
    }

    public void doReturn() {

    }

    public void doThrowing() {

    }

    private String getMethodName(JoinPoint jp) {
        //    Object proxy = jp.getThis(); // 代理类
        Object target = jp.getTarget();
        String className = target.getClass().getSimpleName();
        MethodSignature signature = (MethodSignature) jp.getSignature();

        String returnTypeName = signature.getReturnType().getSimpleName();
        String methodName = signature.getName();

        Class<?>[] paramTypes = signature.getParameterTypes();
        String[] paramNames = signature.getParameterNames();
        String params = IntStream.range(0, paramNames.length)
                .mapToObj(i -> paramTypes[i].getSimpleName() + " " + paramNames[i])
                .collect(Collectors.joining(", ", "(", ")"));

        Object[] args = jp.getArgs();
        String argValues = IntStream.range(0, args.length)
                .mapToObj(i -> paramNames[i] + ": " + args[i])
                .collect(Collectors.joining("\n"));

        StringBuilder sb = new StringBuilder();
        sb.append(className).append('.')
                .append(methodName).append(params).append("\n")
                .append(argValues).append("\n");

        return sb.toString();
    }
}
