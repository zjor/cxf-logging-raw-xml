package me.zjor.cxf.interceptors;

import lombok.extern.slf4j.Slf4j;
import me.zjor.cxf.util.XMLUtils;

/**
 * @author: Sergey Royz
 * Date: 14.02.2014
 */
@Slf4j
public class SoapLoggingInterceptor extends RawXmlStreamInterceptor {

	@Override
	protected void handleInboutXmlMessage(String xml) {
		log.info(XMLUtils.prettyPrint(xml));

	}

	@Override
	protected void handleOutboundXmlMessage(String xml) {
		log.info(XMLUtils.prettyPrint(xml));
	}
}
