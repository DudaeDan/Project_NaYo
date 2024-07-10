package com.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.web.util.AdminLoginInterceptor;
import com.web.util.NayoLoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private AdminLoginInterceptor adminLoginInterceptor;
	@Autowired
	private NayoLoginInterceptor nayoLoginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(adminLoginInterceptor).addPathPatterns("/admin/**");
		registry.addInterceptor(nayoLoginInterceptor).addPathPatterns("/**");
	}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:src/main/resources/static/Images/");
    }
	
}
