package com.lxisoft.redalert.config;

import com.lxisoft.redalert.security.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
//@EnableOAuth2Sso
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource datasource;
	

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	/*
	 * private final CorsFilter corsFilter;
	 * 
	 * private final SecurityProblemSupport problemSupport;
	 * 
	 * public SecurityConfiguration(CorsFilter corsFilter, SecurityProblemSupport
	 * problemSupport) { this.corsFilter = corsFilter; this.problemSupport =
	 * problemSupport; }
	 */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
       // .and()
//            .addFilterBefore(corsFilter, CsrfFilter.class)
//            .exceptionHandling()
//            .authenticationEntryPoint(problemSupport)
//            .accessDeniedHandler(problemSupport)
				/*
				 * .and() .headers() .frameOptions() .disable() .and()
				 */
            .authorizeRequests()

           .antMatchers("/redAlertUiProfile/profile","/redAlertUiHome/home").authenticated()

           .antMatchers("/redAlertUiProfile/profile").authenticated()

//            .antMatchers("/management/health").permitAll()
//            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .and().formLogin()
            .loginPage("/redAlertUiLogin/login").permitAll().and().logout().permitAll().logoutSuccessUrl("/");

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
      throws Exception {

        
       /* auth.inMemoryAuthentication()      
        .withUser("abhina").password("{noop}pass1").roles("USER"); 
      */
        auth.jdbcAuthentication().dataSource(datasource).passwordEncoder(new BCryptPasswordEncoder()).
	      
	      usersByUsernameQuery("select email,jhi_password,activated from user_domain where email=?")
	      
         .authoritiesByUsernameQuery("select u.email, a.name from user_domain u inner join user_domain_roles ua on(u.id= user_domain_id) inner join role a on(ua.roles_id=a.id) where u.email=?");
        
    }

    
//    /**
//     * This OAuth2RestTemplate is only used by AuthorizationHeaderUtil that is currently used by TokenRelayRequestInterceptor
//     */
//    @Bean
//    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails,
//        OAuth2ClientContext oAuth2ClientContext) {
//        return new OAuth2RestTemplate(oAuth2ProtectedResourceDetails, oAuth2ClientContext);
//    }

}
