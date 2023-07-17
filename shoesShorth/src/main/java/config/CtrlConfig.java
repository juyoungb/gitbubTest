package config;

import org.springframework.context.annotation.Bean;


public class CtrlConfig {
	@Bean
	public MainCtrl mainCtrl() {
		return new MainCtrl();
	}
}
