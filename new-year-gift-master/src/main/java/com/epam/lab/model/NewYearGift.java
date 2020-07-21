package com.epam.lab.model;

import com.epam.lab.model.sweets.Sweet;
import com.epam.lab.model.sweets.SweetsGenerator;

import java.util.ArrayList;
import java.util.Comparator;

public class NewYearGift {

    private SweetsGenerator generator = new SweetsGenerator();
    private ArrayList<Sweet> newYearsList = new ArrayList<>();

    private static SugarLevelComparator sugarComparator = new SugarLevelComparator();
    private static WeightComparator weightComparator = new WeightComparator();

    public SugarLevelComparator getSugarLevelComparator() {
        return sugarComparator;
    }

    public WeightComparator getWeightComparator() {
        return weightComparator;
    }

    public ArrayList<Sweet> generate(int times) {
        for (int i = 0; i < times; i++) {
            newYearsList.add(generator.next());
        }
        return newYearsList;
    }

    public static void generateNewYearGift(int numbers) {
        for (Sweet sweet : new SweetsGenerator(numbers))
            System.out.println(sweet);
    }

    private static class SugarLevelComparator implements Comparator<Sweet> {
        public int compare(Sweet sweetOne, Sweet sweetTwo) {
            return Double.compare(sweetOne.getSugarLevel(), sweetTwo.getSugarLevel());
        }
    }

    private static class WeightComparator implements Comparator<Sweet> {
        public int compare(Sweet sweetOne, Sweet sweetTwo) {
            return Double.compare(sweetOne.getWeight(), sweetTwo.getWeight());
        }
    }
}
