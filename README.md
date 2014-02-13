cxf-logging-raw-xml
===================
This example shows how to add logging of a raw XML into CXF-based web-service. 
The example shows how to do it on client generated from WSDL and server side.
Server-side
-----------
Simply add lines shown below to your service definition:
```
<jaxws:features>
	<bean class="org.apache.cxf.feature.LoggingFeature" />
</jaxws:features>
```
Full configuration file can be found here: server/src/main/webapp/WEB-INF/applicationContext.xml
Client-side
-----------
Your have to define your own interceptor. The key idea is implemented in `RawXmlStreamInterceptor` class.
```
package me.zjor.cxf.interceptors;

import org.apache.cxf.binding.soap.interceptor.SoapPreProtocolOutInterceptor;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RawXmlStreamInterceptor extends AbstractPhaseInterceptor<Message> {

	public RawXmlStreamInterceptor() {
		super(Phase.PRE_STREAM);
		addBefore(SoapPreProtocolOutInterceptor.class.getName());
	}

	public void handleMessage(Message message) {
		boolean isOutbound;
		isOutbound = message == message.getExchange().getOutMessage()
				|| message == message.getExchange().getOutFaultMessage();

		if (isOutbound) {
			OutputStream os = message.getContent(OutputStream.class);
			CachedStream cs = new CachedStream();
			message.setContent(OutputStream.class, cs);

			message.getInterceptorChain().doIntercept(message);

			try {

				cs.flush();
				CachedOutputStream csnew = (CachedOutputStream) message
						.getContent(OutputStream.class);
				String soapMessage = IOUtils.toString(csnew.getInputStream());

				handleOutboundXmlMessage(soapMessage);

				cs.close();

				IOUtils.copy(new ByteArrayInputStream(soapMessage.getBytes("UTF-8")), os, 1024);

				os.flush();

				message.setContent(OutputStream.class, os);
				os.close();

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} else {
			try {
				InputStream is = message.getContent(InputStream.class);
				CachedOutputStream bos = new CachedOutputStream();
				IOUtils.copy(is, bos);

				bos.flush();
				is.close();

				handleInboutXmlMessage(new String(bos.getBytes()));

				message.setContent(InputStream.class,
						bos.getInputStream());

			} catch (Exception ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public void handleFault(Message message) {
	}

	protected void handleInboutXmlMessage(String xml) {

	}

	protected void handleOutboundXmlMessage(String xml) {

	}

	private class CachedStream extends CachedOutputStream {
		public CachedStream() {
			super();
		}

		protected void doFlush() throws IOException {
			currentStream.flush();
		}

		protected void doClose() throws IOException {
		}

		protected void onWrite() throws IOException {
		}
	}

}
```
Since interceptors are working in a chain you have to take care of putting everything back if you read something from the stream.


