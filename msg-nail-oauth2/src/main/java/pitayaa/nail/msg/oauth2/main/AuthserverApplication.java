package pitayaa.nail.msg.oauth2.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import pitayaa.nail.msg.oauth2.config.AuthorizationServerConfiguration;
import pitayaa.nail.msg.oauth2.config.SecurityConfiguration;

@SpringBootApplication
@EnableResourceServer
@EntityScan(basePackages = { "pitayaa.nail.msg.oauth2.domain" })
@Import({ AuthorizationServerConfiguration.class, SecurityConfiguration.class })
public class AuthserverApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthserverApplication.class, args);
	}

}