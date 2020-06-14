# spring-aop
> 本模块演示AOP技术的使用并手写spring事务框架。
##一、什么是AOP编程
AOP: Aspect Oriented Programming 面向切面编程。旨在让关注点代码与业务代码分离！

主要的功能是：日志记录，性能统计，安全控制，事务处理，异常处理等等。

主要的意图是：将日志记录，性能统计，安全控制，事务处理，异常处理等代码从业务逻辑代码中划分出来，通过对这些行为的分离，我们希望可以将它们独立到非指导业务逻辑的方法中，进而改变这些行为的时候不影响业务逻辑的代码。

###1、关注点
关注点,重复代码就叫做关注点；
###2、切面
关注点形成的类，就叫切面(类)！

面向切面编程，就是指对很多功能都有的重复的代码抽取，再在运行的时候网业务方法上动态植入“切面类代码”。
###3、切入点
执行目标对象方法，动态植入切面代码。

可以通过切入点表达式，指定拦截哪些类的哪些方法；给指定的类在运行的时候植入切面类代码。
##二、AOP底层实现原理
底层实现原理是利用的代理设计模式。

代理设计模式又分为：静态代理和动态代理。动态代理又包括JDK动态代理和CGLIB动态代理。
##三、AOP的编程使用
两种方式：注解方式实现AOP和XML方式实现AOP。
###1、注解实现
常用注解：

<aop:aspectj-autoproxy></aop:aspectj-autoproxy>  开启事物注解权限

@Aspect							指定一个类为切面类		

@Pointcut("execution(* com.itmayiedu.service.UserService.add(..))")  指定切入点表达式

@Before("pointCut_()")				前置通知: 目标方法之前执行

@After("pointCut_()")				后置通知：目标方法之后执行（始终执行）

@AfterReturning("pointCut_()")		 返回后通知： 执行方法结束前执行(异常不执行)

@AfterThrowing("pointCut_()")			异常通知:  出现异常时候执行

@Around("pointCut_()")				环绕通知： 环绕目标方法执行

定义切面类如下，然后调用add方法的时候就会自动执行相关的通知，实际可按需对不同方法设置不同的前置/后置/环绕等通知。
```
@Component
@Aspect
public class AopLog {

	// 前置通知
	@Before("execution(* com.example.service.UserService.add(..))")
	public void begin() {
		System.out.println("前置通知");
	}

	//
	// 后置通知
	@After("execution(* com.example.service.UserService.add(..))")
	public void commit() {
		System.out.println("后置通知");
	}

	// 运行通知
	@AfterReturning("execution(* com.example.service.UserService.add(..))")
	public void returning() {
		System.out.println("运行通知");
	}

	// 异常通知
	@AfterThrowing("execution(* com.example.service.UserService.add(..))")
	public void afterThrowing() {
		System.out.println("异常通知");
	}

	// 环绕通知
	@Around("execution(* com.example.service.UserService.add(..))")
	public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		System.out.println("环绕通知开始");
		proceedingJoinPoint.proceed();
		System.out.println("环绕通知结束");
	}
}
```
###2、XML配置实现
Xml实现aop编程：

1） 引入jar文件  【aop 相关jar， 4个】

2） 引入aop名称空间

3）aop 配置：配置切面类（重复执行代码形成的类）和aop配置拦截哪些方法/拦截到方法后应用通知代码
```
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

	<!-- dao 实例 -->

	<bean id="userService" class="com.itmayiedu.service.UserService"></bean>
	<!-- 切面类 -->
	<bean id="aop" class="com.itmayiedu.aop2.AopLog2"></bean>
	<!-- Aop配置 -->
	<aop:config>
		<!-- 定义一个切入点表达式： 拦截哪些方法 -->
		<aop:pointcut expression="execution(* com.itmayiedu.service.UserService.*(..))"
			id="pt" />
		<!-- 切面 -->
		<aop:aspect ref="aop">
			<!-- 环绕通知 -->
			<aop:around method="around" pointcut-ref="pt" />
			<!-- 前置通知： 在目标方法调用前执行 -->
			<aop:before method="begin" pointcut-ref="pt" />
			<!-- 后置通知： -->
			<aop:after method="after" pointcut-ref="pt" />
			<!-- 返回后通知 -->
			<aop:after-returning method="afterReturning"
				pointcut-ref="pt" />
			<!-- 异常通知 -->
			<aop:after-throwing method="afterThrowing"
				pointcut-ref="pt" />
		</aop:aspect>
	</aop:config>

</beans>

```


