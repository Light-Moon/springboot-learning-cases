package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/login").permitAll()//1.根路径和/login路径不拦截
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") //2.登陆页面路径为/login
                .defaultSuccessUrl("/chat") //3.登陆成功转向chat页面
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    //4.在内存中配置两个用户 admin 和 user ,密码和用户名一致,角色是USER
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        .withUser("admin").password("admin").roles("USER")
        .and()
        .withUser("user").password("user").roles("USER")
        .and()
        .passwordEncoder(new CustomPasswordEncoder());
        //参考 https://blog.csdn.net/Canon_in_D_Major/article/details/79675033?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase
        //或者直接用下面的方式设置密码，就不用.and().passwordEncoder(new CustomPasswordEncoder());啦。。
        //auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("user1").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");

    }
    //5忽略静态资源的拦截
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/static/**");
    }

}