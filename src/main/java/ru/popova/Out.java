package ru.popova;

import org.w3c.dom.Document;
import java.io.*;

public class Out {

    Files f;
    {
        try {
            f = new Files();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Document run(String xmlFilename, String schemaFilename,String schemaFilename2,
                        String xsltFilename, String resultFilename) {
        Document document = f.load(xmlFilename);
        f.validate(document, schemaFilename);
        Document xslt = f.load(xsltFilename);
        Document result = f.transform(document, xslt);
        f.validate(result, schemaFilename2);
        f.output(result,resultFilename );
        return result;
    }
}
