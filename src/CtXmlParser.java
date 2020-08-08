import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class CtXmlParser {

    String fileName = "/home/k1/Documents/990516/ct.xml";
    String txIds = "/home/k1/Documents/990516/tx-ids.txt";
    String successResult = "/home/k1/Documents/990516/success.xml";
    String failedResult = "/home/k1/Documents/990516/failed.xml";
    HashSet<String> txSet = new HashSet<>();
    Document successDoc;
    Document failedDoc;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        CtXmlParser ctXmlParser = new CtXmlParser();
        try {
            ctXmlParser.readTxIds();
            ctXmlParser.initResultXml();
            ctXmlParser.parse();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    private void initResultXml() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        successDoc = dBuilder.newDocument();
        Element rootElement = successDoc.createElement("rootElement");//here, I create a new, over-arching element.
        successDoc.appendChild(rootElement);
        failedDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element failedRootElement = failedDoc.createElement("rootElement");//here, I create a new, over-arching element.
        failedDoc.appendChild(failedRootElement);
    }

    private void readTxIds() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(txIds));
            String str;

            while ((str = in.readLine()) != null) {
                txSet.addAll(Arrays.asList(str.split(",")));
            }
            in.close();
        } catch (IOException e) {
            System.out.println("File Read Error");
        }
    }

    private void parse() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileName));
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());


        NodeList nList = root.getElementsByTagName("CdtTrfTxInf");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                //Create new Employee Object
                if (txSet.contains(eElement.getElementsByTagName("TxId").item(0).getFirstChild().getNodeValue())) {
                    Node newNode = successDoc.adoptNode(eElement.cloneNode(true));
                    Node successNode = successDoc.getDocumentElement();
                    successNode.appendChild(newNode);
//                    System.out.println("    found:" + eElement.getElementsByTagName("TxId").item(0).getFirstChild().getNodeValue());
                } else {
                    System.out.println("not found:" + eElement.getElementsByTagName("TxId").item(0).getFirstChild().getNodeValue());
                    Node newNode = failedDoc.adoptNode(eElement.cloneNode(true));
                    Node failedNode = failedDoc.getDocumentElement();
                    failedNode.appendChild(newNode);
//                    failedDoc.appendChild(eElement);
                }
            }
        }
//        successDoc.appendChild(rootElement);

        finalyzeResultXml(successDoc, successResult);
        finalyzeResultXml(failedDoc, failedResult);
    }

    private void finalyzeResultXml(Document successDoc, String successResult) throws TransformerException {
// write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(successDoc);
        StreamResult result = new StreamResult(new File(successResult));

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);
    }

}