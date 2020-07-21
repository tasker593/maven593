package com.epam.lab.model.sweets;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;
import java.util.Random;

public class SweetsGenerator implements Generator<Sweet>, Iterable<Sweet> {
    private static final Logger LOG = Logger.getLogger(SweetsGenerator.class);
    private static final DecimalFormat PRECISION = new DecimalFormat("#.#");

    private static final Random rand = new Random();

    private static final int SUGAR_MIN = 20;
    private static final int SUGAR_MAX = 80;

    private static final int WEIGHT_MIN = 50;
    private static final int WEIGHT_MAX = 200;


    static {
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
        dfs.setDecimalSeparator('.');
        PRECISION.setDecimalFormatSymbols(dfs);
    }

    // For iteration:
    private int size = 0;

    private Sweet[] instances = {new WhiteChocolate(), new MilkChokolate(),
            new DarkChocolate(), new DesertChocolate(), new PorousChocolate(),
            new Caramel(), new Chewy(), new Waffles(), new Halva(),
    };

    public SweetsGenerator() {
    }

    public SweetsGenerator(int sz) {
        size = sz;
    }

    public Sweet next() {
        Sweet current = instances[rand.nextInt(instances.length)];
        double sugarParam = paramFormatter(randomSugarLevel(), PRECISION);
        double weightParam = paramFormatter(randomWeight(), PRECISION);

        try {
            return current.getClass()
                    .getConstructor(double.class, double.class)
                    .newInstance(sugarParam, weightParam);
            // Report programmer errors at run time:
        } catch (Exception e) {
            LOG.error("RuntimeException", e);
            throw new RuntimeException(e);
        }
    }

    private double paramFormatter(double sugarParam, DecimalFormat df) {
        return Double.parseDouble(df.format(sugarParam));
    }

    private double randomWeight() {
        return WEIGHT_MIN + (Math.random() * ((WEIGHT_MAX - WEIGHT_MIN) + 1));
    }

    private double randomSugarLevel() {
        return SUGAR_MIN + (Math.random() * ((SUGAR_MAX - SUGAR_MIN) + 1));
    }

    private class SweetsIterator implements Iterator<Sweet> {
        int count = size;

        public boolean hasNext() {
            return count > 0;
        }

        public Sweet next() {
            count--;
            return SweetsGenerator.this.next();
        }

        public void remove() { // Not implemented
            LOG.error("UnsupportedOperationException");
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Sweet> iterator() {
        return new SweetsIterator();
    }
}