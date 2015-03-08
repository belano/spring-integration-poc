package com.acme;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	/**
	 * This exposes a standard WSDL 1.1 using XsdSchema.
	 * Note that bean name determine the URL under which web service and the generated WSDL file is available. 
	 * In this case, the WSDL will be available under http://<host>:<port>/ws/countries.wsdl  
	 */
	@Bean(name = "countries")
	public DefaultWsdl11Definition defaultWsdl11Definition(
			XsdSchema countriesSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("CountriesPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition
				.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
		wsdl11Definition.setSchema(countriesSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema countriesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
	}
	
	@Bean
	public ServletRegistrationBean wsServletRegistrationBean() {
		ServletRegistrationBean bean = new ServletRegistrationBean();
		bean.setServlet(wsdispatcherServlet());		
		bean.addUrlMappings("/ws/*");
		bean.setLoadOnStartup(1);;
		return bean;
	}

	public MessageDispatcherServlet wsdispatcherServlet() {
		XmlWebApplicationContext ctx = new XmlWebApplicationContext();
		ctx.setConfigLocation("classpath:/ws-dispatcher-servlet.xml");
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet(
				ctx);
		messageDispatcherServlet.setTransformWsdlLocations(true);		
		return messageDispatcherServlet;
	}
	
	// Switch off the Spring MVC DispatcherServlet
	@Bean
	public Void dispatcherServlet() {
		return null;
	}

}
