package me.zjor.cxf.util;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * @author: Sergey Royz
 * Date: 13.02.2014
 */
@Slf4j
public class XMLUtils {

	public static final int INDENT_NUMBER = 2;

	/**
	 * Formats given XML string using INDENT_NUMBER
	 *
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static String prettyPrint(String xml) {
		try {
			Document document = org.apache.cxf.helpers.XMLUtils.parse(xml);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setAttribute("indent-number", 2);
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(document);
			transformer.transform(source, result);
			return result.getWriter().toString();
		} catch (Exception e) {
			log.error("Error during pretty printing", e);
			return null;
		}
	}

}
