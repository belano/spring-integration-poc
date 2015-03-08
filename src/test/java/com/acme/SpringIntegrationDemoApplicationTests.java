package com.acme;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.integration.ws.SimpleWebServiceInboundGateway;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.context.DefaultMessageContext;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.pox.dom.DomPoxMessage;
import org.springframework.ws.pox.dom.DomPoxMessageFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.acme.SpringIntegrationDemoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringIntegrationDemoApplication.class)
@IntegrationTest
public class SpringIntegrationDemoApplicationTests {
	
	// TODO - this does NOT get injected
	@Autowired(required = false)
	private SimpleWebServiceInboundGateway gateway;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	@Ignore
	public void testGateway() throws Exception {		
		String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\""
				+ " xmlns:gs=\"http://spring.io/guides/gs-producing-web-service\">"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ "<gs:getCountryRequest>"
				+ "<gs:name>Spain</gs:name>"
				+ "</gs:getCountryRequest>"
				+ "</soapenv:Body>"
				+ "</soapenv:Envelope>";
		DomPoxMessageFactory messageFactory = new DomPoxMessageFactory();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		Document document = documentBuilder.parse(new InputSource(
				new StringReader(xml)));
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		DomPoxMessage request = new DomPoxMessage(document, transformer,
				"text/xml");
		MessageContext messageContext = new DefaultMessageContext(request,
				messageFactory);

		gateway.invoke(messageContext);
		Object reply = messageContext.getResponse().getPayloadSource();
		System.out.println(reply);
	}

}
