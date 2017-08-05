package it.uniroma3.icr;




import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;



@SpringBootApplication
public class IcrApplication extends SpringBootServletInitializer{

	public static void main(String[] args) throws Exception{
		SpringApplication.run(IcrApplication.class, args);
	
	}
	
}
