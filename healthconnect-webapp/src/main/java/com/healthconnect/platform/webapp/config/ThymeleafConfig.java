/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthconnect.platform.webapp.config;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
public class ThymeleafConfig {

	
	
	@Bean(name = "viewResolver") 
	public ViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setCharacterEncoding("UTF-8");
		resolver.setTemplateEngine(templateEngine()); 
		return resolver; 
	}
	 
	 

    @Bean(name = "templateEngine")
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        //templateEngine.addTemplateResolver(templateResolver());
        templateEngine.addTemplateResolver(emailTemplate());
        return templateEngine;
    }

	
	
	/*
	 * @Bean(name = "templateResolver") public ServletContextTemplateResolver
	 * templateResolver(ServletContext servletContext) {
	 * ServletContextTemplateResolver resolver = new
	 * ServletContextTemplateResolver(servletContext);
	 * resolver.setPrefix("/WEB-INF/html/"); resolver.setSuffix(".html");
	 * resolver.setTemplateMode("HTML5"); resolver.setCacheable(true);
	 * resolver.setOrder(2); return resolver; }
	 */
	 
	 

	
	
	  @Bean 
	  public ClassLoaderTemplateResolver emailTemplate() { 
		  ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
		  resolver.setPrefix("template/"); resolver.setSuffix(".html");
		  resolver.setTemplateMode("HTML5"); resolver.setCacheable(true);
		  resolver.setOrder(1); 
		  return resolver; 
	  }

	 
}
