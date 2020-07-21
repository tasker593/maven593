package com.epam.lab.controller;

import com.epam.lab.model.ItemGiftBuilder;
import com.epam.lab.model.ItemGiftParser;
import com.epam.lab.model.NewYearGift;
import com.epam.lab.model.exceptions.CreateDocumentConfigurationException;
import com.epam.lab.model.sweets.Sweet;
import org.apache.log4j.Logger;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Formatter;

public class GiftController {
    private final static int START_OF_COUNT = 1;
    private static final Logger LOG = Logger.getLogger(GiftController.class);

    private NewYearGift newYearGift;
    private ArrayList<Sweet> items;
    private Formatter formatter;

    private ItemGiftParser giftParser;
    private double totalWeight;
    private int counter = 1;

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public GiftController() throws CreateDocumentConfigurationException {
        items = new ArrayList<>();
        newYearGift = new NewYearGift();
        formatter = new Formatter(System.out);
        giftParser = new ItemGiftParser();
        totalWeight = 0;
    }

    private void printTitle(String msg) {
        formatter.format("%-38s\n\n", msg);
        formatter.format("%-3s%-20s %5s %10s\n", "#", "Name", "Sugar", "Weight");
        formatter.format("%-3s%-20s %5s %10s\n", "-", "----", "-----", "------");
    }

    private void print(Sweet item) {
        formatter.format("%-3d%-20.15s %5.2f %10.2f\n", counter++,
                item.getClass().getSimpleName(),
                item.getSugarLevel(),
                item.getWeight());
        totalWeight += item.getWeight();
    }

    private void printTotalWeight() {
        formatter.format("%-23s %5s %10s\n", "", "", "------");
        formatter.format("%-3s%-20s %5s %10.2f\n", "", "Total Weight", "", totalWeight);
    }

    private void printSpace() {
        formatter.format("\n%-38s\n\n\n", "========================================");
    }

    private void generateGift(int nTimes) {
        items = newYearGift.generate(nTimes);

        for (Sweet sweet : items) {
            print(sweet);
        }
    }

    private void printGift() {
        for (Sweet item : items) {
            print(item);
        }
    }

    private void printGift(ArrayList<Sweet> sweets) {
        for (Sweet sweet : sweets) {
            print(sweet);
        }
    }

    private void writeToXmlFile(String xmlContent) {
        File theDir = new File("./output");

        if (!theDir.exists())
            theDir.mkdir();

        String fileName = String.format("./output/%s_%s.xml", "GiftList", Calendar.getInstance().getTimeInMillis());

        try (OutputStream stream = new FileOutputStream(new File(fileName));
             OutputStreamWriter out = new OutputStreamWriter(stream, StandardCharsets.UTF_16)) {
            out.write(xmlContent);
            out.write(System.lineSeparator());
        } catch (IOException ex) {
            LOG.error("Cannot write to file!", ex);
        }
    }

    private String generateXmlContent(ItemGiftBuilder builder) {
        Document doc = builder.build(items);
        DOMImplementation impl = doc.getImplementation();
        DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");

        LSSerializer ser = implLS.createLSSerializer();
        ser.getDomConfig().setParameter("format-pretty-print", true);

        return ser.writeToString(doc);
    }

    private ArrayList<Sweet> extractSugar(double start, double end) {
        ArrayList<Sweet> results = new ArrayList<>();
        Collections.sort(items, newYearGift.getSugarLevelComparator());

        for (Sweet sweet : items) {
            double value = sweet.getSugarLevel();
            if (value >= start && value <= end) {
                results.add(sweet);
            }
        }

        return results;
    }

/*	public static void main(String[] args) throws CreateDocumentConfigurationException {

		GiftController giftController = new GiftController();
		int n = 10;

		// general overwiev 
		giftController.showGiftContent(n);

		// sort by weight
		giftController.showSortedByWeight();

		// sort by sugar level
		giftController.showSortedBySugar();

		// show gift list with sugar limitation
		double start = 30, end = 60;
		giftController.showExtractedSugar(start, end);

		// write to xml file
		//giftController.writeToXmlFile();
	}*/

    public void showExtractedSugar(double lowLimit, double higherLimit) {
        ArrayList<Sweet> extract = extractSugar(lowLimit, higherLimit);
        printTitle(String.format("New Year's Gift with extracted sugar%n(from %s to %s):", lowLimit, higherLimit));
        printGift(extract);
        printTotalWeight();
        printSpace();
        setCountersToStart();
    }

    public void writeToXmlFile() throws CreateDocumentConfigurationException {
        ItemGiftBuilder builder = new ItemGiftBuilder();
        String xmlContent = generateXmlContent(builder);
        writeToXmlFile(xmlContent);
    }

    public void showSortedBySugar() {
        Collections.sort(items, newYearGift.getSugarLevelComparator());
        printTitle("New Year's Gift by sorted Sugar level:");
        printGift();
        printTotalWeight();
        printSpace();
        setCountersToStart();
    }

    public void showSortedByWeight() {
        Collections.sort(items, newYearGift.getWeightComparator());
        printTitle("New Year's Gift by sorted Weigh:");
        printGift();
        printTotalWeight();
        printSpace();
        setCountersToStart();
    }

    public void showGiftContent(int nTimes) {
        printTitle("New Year's Gift (list of contents):");
        generateGift(nTimes);
        printTotalWeight();
        printSpace();
        setCountersToStart();
    }

    public void parseXmlFile(File selectedFile) {
        try {
            items = giftParser.parse(selectedFile);
        } catch (Exception e) {
            LOG.error("Error parsing file!", e);
        }
        showParsedData();
    }

    public void showParsedData() {
        printTitle("New Year's Gift (list of contents):");
        for (Sweet sweet : items) {
            print(sweet);
        }
        printTotalWeight();
        printSpace();

        // set to start
        setCountersToStart();
        // output by sorted parameters
        showSortedBySugar();
        showSortedByWeight();
    }

    private void setCountersToStart() {
        setCounter(START_OF_COUNT);
        setTotalWeight(0);
    }

}
