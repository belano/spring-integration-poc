package com.acme.ws;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.server.SoapEndpointInterceptor;

public class AuditInterceptor implements SoapEndpointInterceptor {

	final static Logger logger = LoggerFactory
			.getLogger(AuditInterceptor.class);

	@Override
	public boolean handleRequest(MessageContext messageContext, Object endpoint)
			throws Exception {
		Source payloadSource = messageContext.getRequest().getPayloadSource();
		logger.debug("Handling request {}", System.getProperty("line.separator") + xmlDocToString(payloadSource));
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext, Object endpoint)
			throws Exception {
		Source payloadSource = messageContext.getResponse().getPayloadSource();
		logger.debug("Handling response {}", System.getProperty("line.separator") + xmlDocToString(payloadSource));
		return true;
	}

	@Override
	public boolean handleFault(MessageContext messageContext, Object endpoint)
			throws Exception {
		logger.debug("Handling fault");
		return true;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Object endpoint,
			Exception ex) throws Exception {
		logger.debug("After completion");
	}

	@Override
	public boolean understands(SoapHeaderElement header) {
		return true;
	}

	private String xmlDocToString(Source source)
			throws TransformerFactoryConfigurationError, TransformerException {
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "2");
		StreamResult result = new StreamResult(new StringWriter());
		transformer.transform(source, result);
		String xmlString = result.getWriter().toString();
		return xmlString;
	}

}
