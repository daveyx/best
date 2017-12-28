package com.example.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.persistence.model.PUserAccount;
import com.example.persistence.repo.PUserAccountRepository;

/*
 * based on 
 * http://user.tynamo.codehaus.narkive.com/Jat72oCQ/tapestry-security-database-authentication
 */
public class ShiroRealm extends AuthorizingRealm {

	@Inject
	private PUserAccountRepository userAccountRepository;

	public ShiroRealm(final CredentialsMatcher credentialsMatcher) {
		setName(ShiroRealm.class.getCanonicalName());
		setAuthenticationTokenClass(UsernamePasswordToken.class);
		setCredentialsMatcher(credentialsMatcher);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		final UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		String username = upToken.getUsername();

		// Null username is invalid
		if (username == null) {
			throw new AccountException("Null usernamesare not allowed by this realm.");
		}

		final PUserAccount pUserAccount = userAccountRepository.findByEmail(username);
		// if (user.isFederatedAccount()) { throw new AccountException("Account [" +
		// username + "] is federated and cannot be locally authenticated."); }

		// if (user.isAccountLocked()) {
		// throw new LockedAccountException("Account [" + username + "] is locked.");
		// }
		// if (user.isCredentialsExpired()) {
		// String msg = "The credentials for account [" + username + "] are expired";
		// throw new ExpiredCredentialsException(msg);
		// }

		if (pUserAccount == null) {
			return null;
		}

		return new SimpleAuthenticationInfo(pUserAccount.getUuid(), pUserAccount.getPassword(), getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection was null, which should not happen");
		}

		if (principals.isEmpty()) {
			return null;
		}

		if (principals.fromRealm(getName()).size() <= 0) {
			return null;
		}

		String uuid = (String) principals.fromRealm(getName()).iterator().next();
		if (uuid == null) {
			return null;
		}
		final PUserAccount pUserAccount = userAccountRepository.findByUuid(uuid);

		if (pUserAccount == null) {
			return null;
		}

		final Set<String> roles = new HashSet<String>();
		if (StringUtils.isBlank(pUserAccount.getRoles())) {
			return new SimpleAuthorizationInfo(roles);
		}
		final String[] accountRoles = StringUtils.split(pUserAccount.getRoles(), ',');
		for (final String role : accountRoles) {
			roles.add(role);
		}

		return new SimpleAuthorizationInfo(roles);
	}

}
