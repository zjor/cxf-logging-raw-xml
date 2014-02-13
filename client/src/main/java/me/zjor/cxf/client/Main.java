package me.zjor.cxf.client;

import lombok.extern.slf4j.Slf4j;
import me.zjor.cxf.MyService;
import me.zjor.cxf.MyServiceImplService;
import me.zjor.cxf.interceptors.SoapLoggingInterceptor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author: Sergey Royz
 * Date: 13.02.2014
 */
@Slf4j
public class Main {

	public static final String WSDL_URL = "http://localhost:8080/hello-cxf/services/myService?wsdl";

	public static void main(String[] args) throws MalformedURLException {
		MyServiceImplService service = new MyServiceImplService(new URL(WSDL_URL));
		MyService port = service.getPort(MyService.class);


		Client client = ClientProxy.getClient(port);

		client.getInInterceptors().add(new SoapLoggingInterceptor());
		client.getOutInterceptors().add(new SoapLoggingInterceptor());

		log.info("Result: {}", port.sayHello("Mike"));
	}

}
