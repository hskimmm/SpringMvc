package kr.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//servlet-context.xml 설정 파일을 자바 파일로 변경(resources 경로 매핑 설정, viewResolver 설정, 어노테이션 활성화 설정 부분, pojo를 메모리에 올리는 설정)
@Configuration //환경설정파일 명시
@EnableWebMvc // web.xml 설정 파일에 <annotation-driven/> 설정 부분(어노테이션 활성화 설정)
@ComponentScan(basePackages = {"kr.spring.controller"}) // web.xml 설정 파일에 pojo를 메모리에 올리는 설정 부분
public class ServletConfig implements WebMvcConfigurer{
	// WebMvcConfigurer 인터페이스를 상속 받아 viewResolver, resources 부분을 설정한다.
	
	//resources 설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	//viewResolver 설정
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");
		registry.viewResolver(bean);
	}
	
}
