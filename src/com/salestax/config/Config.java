package com.salestax.config;

public class Config {
    private String inputDir = "./src/com/salestax/input/";
    private String outputDir = "./src/com/salestax/output/";

    // Change the input from here
    // TODO: Make this configurable from a file selector in the GUI
    private String goodsFile = "input3.txt";

    public String filePath() {
        return inputDir.concat(goodsFile);
    }

    public String returnFilePathWithString(String filename) {
        return inputDir.concat(filename);
    }

    public String returnOutputFilePath(String filename) {
        return  outputDir.concat(filename);
    }
}
