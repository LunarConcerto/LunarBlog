package cafe.lunarconcerto.config;

import cafe.lunarconcerto.filter.JwtAuthenticationTokenFilter;
import cafe.lunarconcerto.handler.security.AccessDeniedHandlerImpl;
import cafe.lunarconcerto.handler.security.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author LunarConcerto
 * @time 2023/12/21
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/login").anonymous()
                .antMatchers("/logout").authenticated()
                .antMatchers("/comment").authenticated()
                .antMatchers("/user/userInfo").authenticated()
                // 除上面外的所有请求全部不需要认证即可访问
                .anyRequest().permitAll();

        // 配置异常处理器
        http.exceptionHandling()
                // 认证失败处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                // 授权失败处理器
                .accessDeniedHandler(accessDeniedHandler);

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout().disable();
        //允许跨域
        http.cors();
    }

}
