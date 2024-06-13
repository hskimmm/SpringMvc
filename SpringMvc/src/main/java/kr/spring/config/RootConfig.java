package kr.spring.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

//root-context.xml 설정 파일을 자바 파일로 변경
@Configuration //환경설정파일 명시
@MapperScan(basePackages = {"kr.spring.mapper"}) //mapper 파일을 메모리에 올리는 설정
@PropertySource({"classpath:persistence-mysql.properties"}) //데이터 베이스 연결 정보 파일 불러온다.
public class RootConfig {
	@Autowired
	private Environment env; //DB 설정 파일을 불러오기 위해 사용
	
	//데이터베이스설정
	@Bean
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(env.getProperty("jdbc.driver"));
		driverManagerDataSource.setUrl(env.getProperty("jdbc.url"));
		driverManagerDataSource.setUsername(env.getProperty("jdbc.user"));
		driverManagerDataSource.setPassword(env.getProperty("jdbc.password"));
		
		return driverManagerDataSource;
	}
	
	//SqlSessionFactoryBean설정(커넥션 풀 하는 부분)
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		return (SqlSessionFactory)sqlSessionFactory.getObject();
	}
	
}
