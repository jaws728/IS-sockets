/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import org.netbeans.xml.schema.updateschema.ObjectFactory;
import org.netbeans.xml.schema.updateschema.TMyPlace;

/**
 *
 * @author André & Ricardo
 */
public class MessageManagement {

    public static String createPlaceStateContent(TMyPlace myPlace) throws JAXBException {
        //TODO Lab 2:
        //Serealize TMyPlace object to String using JAXB
        
        String result = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(TMyPlace.class);  
            Marshaller marshaller = context.createMarshaller();  
            //marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            //marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  
            //marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
            
            // To format XML
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            StringWriter writer = new StringWriter();  
            marshaller.marshal(myPlace, writer);  
            result = writer.toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return result;  
    }

    public static TMyPlace retrievePlaceStateObject(String content) throws JAXBException {
        //TODO Lab 2:
        //Deserealize a String to a TMyPlace object using JAXB

        TMyPlace t = null;
        try {  
            JAXBContext context = JAXBContext.newInstance(TMyPlace.class);  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            //t = (TMyPlace) unmarshaller.unmarshal(new StringReader(content));  
            //InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
            
            t = (TMyPlace)unmarshaller.unmarshal(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return t; 
    }
}
