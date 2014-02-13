package me.zjor.cxf;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author: Sergey Royz
 * Date: 12.02.2014
 */
@WebService
public interface MyService {

	String sayHello(@WebParam(name = "name") String name);

}
