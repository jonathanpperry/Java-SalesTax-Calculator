package com.salestax.test;

import com.salestax.Calculator;
import com.salestax.config.Config;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestOne {
    Calculator calc = new Calculator();
    Config config = new Config();

    public TestOne() throws IOException {
        calc.readData();
    }

    String readFile(String filename, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(this.config.returnFilePathWithString(filename)));
        return new String(encoded, encoding);
    }

    @Test
    public void firstTest() {
        Assert.assertTrue(true);
    }

    @Test
    public void secondTest() throws IOException {
        Assert.assertTrue(calc.setGoodsAfterTax().equals(readFile("input3.txt", Charset.defaultCharset())));
    }
}
