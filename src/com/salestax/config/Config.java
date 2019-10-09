package com.salestax.config;

public class Config {
    private String inputDir = "./src/com/salestax/input/input1.txt";

    // Change the input from here
    // TODO: Make this configurable from a file selector in the GUI
    private String goodsFile = "input1.txt";

    public String filePath() {
        return inputDir.concat(goodsFile);
    }
}
