package com.example.t5.services;

import org.apache.shiro.realm.Realm;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.ComponentOverride;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.messages.ComponentMessagesSource;
import org.tynamo.security.SecuritySymbols;
import org.tynamo.security.services.SecurityFilterChainFactory;
import org.tynamo.security.services.impl.SecurityFilterChain;

import com.example.security.ShiroRealm;
import com.example.t5.pages.Index2;
import com.example.t5.pages.Login;
import com.example.t5.services.impl.T5Service;
import com.example.t5.services.impl.T5TestDataService;

import info.code8.tapestry.TapestryApplication;

@TapestryApplication
public class AppModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(IT5Service.class, T5Service.class);
		binder.bind(IT5TestDataService.class, T5TestDataService.class);
		binder.bind(ShiroRealm.class);
	}

	public static void contributeIgnoredPathsFilter(Configuration<String> configuration) {
		configuration.add("/playground/.*");
		configuration.add("/playground");
	}

	public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration) {
		// The values defined here (as factory default overrides) are themselves
		// overridden with application defaults by DevelopmentModule and QaModule.

		// The application version is primarily useful as it appears in
		// any exception reports (HTML or textual).
		configuration.override(SymbolConstants.APPLICATION_VERSION, "0.0.1-SNAPSHOT");

		// This is something that should be removed when going to production, but is
		// useful
		// in the early stages of development.
		configuration.override(SymbolConstants.PRODUCTION_MODE, false);
	}

	public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
		// Contributions to ApplicationDefaults will override any contributions to
		// FactoryDefaults (with the same key). Here we're restricting the supported
		// locales to just "en" (English). As you add localised message catalogs and
		// other assets,
		// you can extend this list of locales (it's a comma separated series of locale
		// names;
		// the first locale name is the default when there's no reasonable match).
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "de");

		// You should change the passphrase immediately; the HMAC passphrase is used to
		// secure
		// the hidden field data stored in forms to encrypt and digitally sign
		// client-side data.
		configuration.add(SymbolConstants.HMAC_PASSPHRASE, "com.example.t5.services.TapestryMainModule");
	}

	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void setupEnvironment(MappedConfiguration<String, Object> configuration) {
		// Support for jQuery is new in Tapestry 5.4 and will become the only supported
		// option in 5.5.
		configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
		configuration.add(SymbolConstants.BOOTSTRAP_ROOT, "context:mybootstrap");
//		configuration.add(SymbolConstants.START_PAGE_NAME, "index2");
		configuration.add(SecuritySymbols.SUCCESS_URL,"/" + Index2.class.getSimpleName().toLowerCase());
		configuration.add(SecuritySymbols.LOGIN_URL,"/" + Login.class.getSimpleName().toLowerCase());
	}

	public static void contributeWebSecurityManager(final Configuration<Realm> configuration, final ShiroRealm sr) {
		configuration.add(sr);
	}

	public static void contributeSecurityConfiguration(OrderedConfiguration<SecurityFilterChain> configuration,
			SecurityFilterChainFactory factory) {
		// OrderedConfiguration must be named, so they can be overridden later
		configuration.add("signup-anon", factory.createChain("/authc/signup").add(factory.anon()).build());

		configuration.add("authc_signup", factory.createChain("/authc/signup").add(factory.anon()).build());
		configuration.add("authc", factory.createChain("/authc/**").add(factory.authc()).build());
		configuration.add("partlyauthc", factory.createChain("/partlyauthc/**").add(factory.anon()).build());
		configuration.add("contributed", factory.createChain("/contributed/**").add(factory.authc()).build());
		configuration.add("user_signup", factory.createChain("/user/signup").add(factory.anon()).build());
		configuration.add("user", factory.createChain("/user/**").add(factory.user()).build());
		configuration.add("roles_user", factory.createChain("/roles/user/**").add(factory.roles(), "user").build());
		configuration.add("roles_manager",
				factory.createChain("/roles/manager/**").add(factory.roles(), "manager").build());
		configuration.add("perms_view",
				factory.createChain("/perms/view/**").add(factory.perms(), "news:view").build());
		configuration.add("perms_edit",
				factory.createChain("/perms/edit/**").add(factory.perms(), "news:edit").build());

		configuration.add("ports_ssl", factory.createChain("/ports/ssl").add(factory.ssl()).build());
		configuration.add("ports_9000", factory.createChain("/ports/port9090").add(factory.port(), "9090").build());

		configuration.add("hidden", factory.createChain("/hidden/**").add(factory.notfound()).build());
	}

	@Contribute(ComponentMessagesSource.class)
	public static void provideMessages(final @Value("messages.properties") Resource resource,
			final OrderedConfiguration<Resource> configuration) {
		configuration.add(SymbolConstants.APPLICATION_CATALOG + ".BASE", resource);
	}

	public static void contributeComponentClassResolver(final Configuration<LibraryMapping> configuration) {
		configuration.add(new LibraryMapping("admin", "com.example.t5.admin"));
	}

	@Contribute(ComponentOverride.class)
	public static void overrideComponent(final MappedConfiguration<Class<?>, Class<?>> configuration) {

		// components
		
		// pages
		configuration.add(org.tynamo.security.pages.Login.class, Login.class);
	}
}
