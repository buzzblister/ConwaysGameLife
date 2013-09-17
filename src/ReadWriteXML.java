import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadWriteXML implements InputOutputLifeSimulation {

	public LifeSimulation readLifeSim(File selectedFile) {//, LifeSimulation simulation) {
		LifeSimulation simulation = null;
		boolean state = false;
		int maxRow = 0;
		int maxCol = 0;
		int row = 0;
		int col = 0;
		
		if (selectedFile != null) {
			try {
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document document = docBuilder.parse(selectedFile);
				
				document.getDocumentElement().normalize();
				
				maxRow = Integer.parseInt(document.getDocumentElement().getElementsByTagName("row").item(0).getTextContent());
				maxCol = Integer.parseInt(document.getDocumentElement().getElementsByTagName("column").item(0).getTextContent());
				
				simulation = new LifeSimulation(maxRow, maxCol);
				
				NodeList nodeList = document.getElementsByTagName("cell");
				
				for (int i=0; i < nodeList.getLength(); i++) {
					Node nNode = nodeList.item(i);
					state = nNode.getTextContent().contains("true") ? true : false;
					simulation.setLife(row, col, state);
					
					++ col;
					col = col % maxCol;
					if (col == 0 && row < maxRow) {
						row++;
					}
				}
			} catch (ParserConfigurationException parserEx) {
				parserEx.printStackTrace();
			} catch (SAXException saxEx) {
				saxEx.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			return simulation;
		}
		return new LifeSimulation(50, 50);
	}
	
	public void writeLifeSim(String fileName, LifeSimulation simulation) {
		if (fileName != null) {
			fileName += ".xml";
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document document = docBuilder.newDocument();
				
				Element root = document.createElement("Lifesimulation");
				document.appendChild(root);
				
				Element row = document.createElement("row");
				row.appendChild(document.createTextNode(Integer.toString(simulation.getRow())));
				Element column = document.createElement("column");
				column.appendChild(document.createTextNode(Integer.toString(simulation.getColumn())));
				
				root.appendChild(row);
				root.appendChild(column);
				
				for (int i=0; i < simulation.getRow(); i++) {
					for (int j=0; j < simulation.getColumn(); j++) {
						Element cell = document.createElement("cell");
						cell.appendChild(document.createTextNode(Boolean.toString(simulation.getLife(i, j))));
						root.appendChild(cell);
					}
				}
				
				//Saving document to the disk file
				TransformerFactory tranFactory = TransformerFactory.newInstance();
				Transformer transformer = tranFactory.newTransformer();
				
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty("{http://xml.apache.org}indent-amount", "4");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				
				DOMSource source = new DOMSource(document);
	
				FileWriter fileWriter = new FileWriter(fileName);
				StreamResult result = new StreamResult(fileWriter);
				
				transformer.transform(source, result);
				
			} catch (ParserConfigurationException ex) {
				ex.printStackTrace();
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException exTransformer) {
				exTransformer.printStackTrace();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}
}
