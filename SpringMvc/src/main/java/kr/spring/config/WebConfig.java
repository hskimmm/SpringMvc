package kr.spring.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


//web.xml 설정 파일을 Java 파일로 변경
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{
	
	//web.xml 에서 root-context.xml을 읽어들이는 부분(데이터베이스 설정 및 mapper 설정 부분)
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {RootConfig.class, SecurityConfig.class}; //RootConfig, SecurityConfig 클래스를 읽어들이고 메모리에 올림
	}

	//web.xml 에서 dispatcherServelt이 servlet-context.xml을 읽어들이는 부분(resources 경로 설정 및 viewResolver 설정 및 컨트롤러 스캔 설정 부분) 
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {ServletConfig.class}; //ServletConfig 클래스를 읽어들임
	}

	//web.xml에서 mapping을 설정하는 부분
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"}; //context root 경로를 설정 
	}

	//web.xml에서 한글 인코딩 설정하는 부분
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		return new Filter[] {encodingFilter};
	}
	
	//파일업로드설정
	@Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(5242880); // 5MB
        return resolver;
    }
}
