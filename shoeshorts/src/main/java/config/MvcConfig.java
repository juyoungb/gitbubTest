package config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer{
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/", ".jsp"); 
		// view파일들의 기본 위치와 확장자를 지정
	}
	
	public void addViewControllers(ViewControllerRegistry registry) {
	// 단순히 이동만 하는 경우 컨트롤러를 따로 생성하지 않고 요청 경로와 뷰 이름을 연결시켜주는 메소드 (보여만 주고 끈나는것들을 부를때 사용)
		registry.addViewController("/").setViewName("index"); 
	}	
}