package ru.popova;
/**
 *Обработка файлов
 */
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Files {
    static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    private int count = 1;
    Log log = new Log();
    public Files() throws IOException {
    }
    /**
     * Создание и конфигурация DocumentBuilderFactory
     * awareness - true if the parser produced will provide support for XML namespaces; false otherwise.
     * Note: When the application specifies the schema(s) to use, it overrides any schema declarations in the document.
     * @return
     */
    private DocumentBuilder createDocumentBuilder() {
        DocumentBuilder db = null;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            builderFactory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
            db  = builderFactory.newDocumentBuilder();
            log.addLog(count++ + " createDocumentBuilder - OK!\n");
        } catch (ParserConfigurationException e) {
            log.addLog(count++ + " createDocumentBuilder - FAIL!\n");
        }
        return db;
    }

    /**
     * Захват файла
     * @param filename
     * @return
     */
    private InputStream getInputStream(String filename) {
        InputStream is = null;
        try {
            is = getClass().getClassLoader().getResourceAsStream(filename);
            log.addLog(count++ + "getInputStream - OK!" + filename +"\n");
        } catch (Exception e) {
            log.addLog(count++ + "getInputStream - FAIL!\n");
        }
        return is;
    }

    /**
     * parse the XML into a document object
     * @param filename
     * @return
     */
    Document load(String filename) {
        Document document = null;
        InputStream is = getInputStream(filename);
        try {
            if (!is.equals(null)) {
                DocumentBuilder builder = createDocumentBuilder();
                document = builder.parse(is);
                log.addLog(count++ + " load file - OK! " + filename+ "\n");
            }
        }catch (Exception e) {
            log.addLog(count++ + " file don't load!" + filename +"\n");
        }
        return document;
    }

    /**
     * Проверка на валидность
     * @param document
     * @param xsd
     */
    public boolean validate(Document document, String xsd) {
        boolean flag = false;
        try {
            if (!document.equals(null) & !xsd.equals(null)) {
                SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = factory.newSchema(new StreamSource(getInputStream(xsd)));
                Validator validator = schema.newValidator();

                validator.validate(new DOMSource(document));
                flag = true;
                log.addLog(count++ + "validate - OK! \n");
            }
        } catch (SAXException | IOException e) {
            log.addLog(count++ + "The file is invalid! Reason:");
            log.addLog(e.getMessage()+ "\n");
            System.exit(0);
        }
        return flag;
    }

    /**
     * Трансформация xml по схеме xslt
     * @param xml
     * @param xslt
     * @return resultDoc
     */
    public Document transform(Document xml, Document xslt) {
        Source xmlSource = new DOMSource(xml);
        Source xsltSource = new DOMSource(xslt);
        DOMResult result = new DOMResult();
        Document resultDoc = null;
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(xsltSource);
            transformer.transform(xmlSource, result);
            resultDoc = (Document) result.getNode();
            log.addLog(count++ + "transform - OK!\n");
        } catch (TransformerException e) {
            log.addLog(count++ + "The file is not transform! Reason:" + e.getMessage() + "\n");
            System.exit(0);
        }
        return resultDoc;
    }

    /**
     *  Write DOM to file as XML
     * @param document
     * @param resultFileName
     */
    public void output(Document document, String resultFileName) {
        File xmlFile = new File(resultFileName);
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(xmlFile));
            log.addLog(count++ + "Запись результата в файл - OK!");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            log.addLog(count++ + "Запись результата в файл - FAIL!");
            e.printStackTrace();
        }
    }
}