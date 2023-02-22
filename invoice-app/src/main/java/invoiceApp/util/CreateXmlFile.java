package invoiceApp.util;

import invoiceApp.model.xml.OrderInvoiceXmlList;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

@Service
public class CreateXmlFile {

    public static void generateXml(File file, Object list, Class<?> class_type) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(class_type);
        Marshaller m = context.createMarshaller();
        // for pretty-print XML in JAXB
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(list, file);
    }
}
