package invoiceApp.util;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Service
public class CreatePdf {

    public static byte[] generatePDF(File xml_file, StreamSource xsl_source) throws Exception {
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        // Setup a buffer to obtain the content length
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // Setup FOP
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(xsl_source);
        // Make sure the XSL transformation's result is piped through to FOP
        Result res = new SAXResult(fop.getDefaultHandler());

        // Setup input
        Source src = new StreamSource(xml_file);
        // Start the transformation and rendering process
        transformer.transform(src, res);
        byte[] bytes = out.toByteArray();

        out.close();
        out.flush();
        return bytes;

    }

}
