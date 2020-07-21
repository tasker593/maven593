package com.epam.lab.view;


import org.apache.log4j.Logger;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Application application = new Application();
            application.start();
        } catch (Exception e) {
            LOG.error("Error happen", e);
            e.printStackTrace();
        }
    }
}
