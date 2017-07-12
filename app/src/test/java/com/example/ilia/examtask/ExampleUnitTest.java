package com.example.ilia.examtask;


import com.example.ilia.examtask.controller.Calculator;
import com.example.ilia.examtask.view.MainActivity;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void calculate_isCorrect() throws Exception {
        double correct = (double) 40;
        double delta = 0.001;
        String amount = "10";
        double fromNominal = 20;
        double fromValue = 20;
        double toNominal = 10;
        double toValue = 10;

        Calculator calculator = new Calculator(amount, fromNominal, fromValue, toNominal, toValue);
        assertEquals(correct, calculator.performCalculation(), delta);
    }
}