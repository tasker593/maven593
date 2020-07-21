package com.epam.lab.model;

import com.epam.lab.model.exceptions.CreateDocumentConfigurationException;
import com.epam.lab.model.sweets.Sweet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

public class ItemGiftBuilder {

    private DocumentBuilder builder;
    private Document doc;

    /**
     * Constructs an item list builder.
     *
     * @throws CreateDocumentConfigurationException exception during document creation.
     */
    public ItemGiftBuilder() throws CreateDocumentConfigurationException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new CreateDocumentConfigurationException("exception create new document", e);
        }
    }

    /**
     * Builds a DOM document for an array list of items.
     *
     * @param items the items
     * @return a DOM document describing the items
     */
    public Document build(ArrayList<Sweet> items) {
        doc = builder.newDocument();
        doc.appendChild(createItems(items));
        return doc;
    }

    /**
     * Builds a DOM element for an array list of items.
     *
     * @param items the items
     * @return a DOM element describing the items
     */
    private Element createItems(ArrayList<Sweet> items) {
        Element e = doc.createElement("gift");

        for (Sweet anItem : items)
            e.appendChild(createItem(anItem));

        return e;
    }

    /**
     * Builds a DOM element for an item.
     *
     * @param anItem the item
     * @return a DOM element describing the item
     */
    private Element createItem(Sweet anItem) {
        Element e = doc.createElement("item");

        e.appendChild(createTextElement("name", anItem.getClass().getSimpleName()));
        // possibly to set sugar as attribute:
        e.setAttribute("sugar", String.valueOf(anItem.getSugarLevel()));
        // or as element:
        e.appendChild(createTextElement("weight", String.valueOf(anItem.getWeight())));

        return e;
    }

    /**
     * Builds the text content for document
     *
     * @param name element
     * @param text content
     * @return text element
     */
    private Element createTextElement(String name, String text) {
        Text t = doc.createTextNode(text);
        Element e = doc.createElement(name);
        e.appendChild(t);
        return e;
    }

}
