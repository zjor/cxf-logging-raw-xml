package me.zjor.cxf;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author: Sergey Royz
 * Date: 12.02.2014
 */
@WebService(endpointInterface = "me.zjor.cxf.MyService")
public class MyServiceImpl implements MyService {

	@Override
	public String sayHello(@WebParam(name = "name") String name) {
		return "Hello " + name;
	}
}
