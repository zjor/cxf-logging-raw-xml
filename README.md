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


