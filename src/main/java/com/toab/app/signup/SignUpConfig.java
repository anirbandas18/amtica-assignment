package com.toab.app.signup;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@ConditionalOnProperty(value = "app.security.basic.enabled", havingValue = "false")
public class SignUpConfig /*extends WebSecurityConfigurerAdapter*/ {

	@Bean
	public JedisConnectionFactory connectionFactory() {
		return new JedisConnectionFactory();
	}

	@Bean
	public CacheManager cacheManager() {
		CacheManager cacheManager = new ConcurrentMapCacheManager();
		return cacheManager;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		return messageSource;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated();
	}*/

}
