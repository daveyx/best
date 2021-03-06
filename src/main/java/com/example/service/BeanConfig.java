package com.example.service;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.example.service.account.AccountService;
import com.example.service.account.IAccountService;

@Configuration
@ComponentScan("com.example.service")
@EnableAsync
public class BeanConfig {

	private static final String PRIVATE_SALT = "aPrivateSalt";

	private PasswordService passwordService;

	private PasswordMatcher credentialsMatcher = new PasswordMatcher();

	@Bean
	public PasswordService passwordService() {
		if (passwordService == null) {
			DefaultPasswordService defaultPasswordService = new DefaultPasswordService();
			passwordService = defaultPasswordService;
			DefaultHashService defaultHashService = (DefaultHashService) defaultPasswordService.getHashService();
			defaultHashService.setPrivateSalt(new SimpleByteSource(PRIVATE_SALT));
		}
		return passwordService;
	}

	@Bean
	public CredentialsMatcher credentialsMatcher() {
		credentialsMatcher.setPasswordService(passwordService());
		return credentialsMatcher;
	}

	@Bean
	public IAccountService accountService() {
		return new AccountService();
	}

	@Bean
	public IPArticleAccessService articleAccessService() {
		return new PArticleAccessService();
	}

	@Bean
	public IEditUserAccountService editUserAccountService() {
		return new EditUserAccountService();
	}
}
