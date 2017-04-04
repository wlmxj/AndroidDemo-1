package com.dingmouren.androiddemo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dingmouren on 2017/4/4.
 */
public class CaculatorTest {
    private Caculator mCalculator;
    @Before
    public void setUp() throws Exception {
        mCalculator = new Caculator();
    }

    @Test
    public void sum() throws Exception {
        //expected:6,sum of 1 and 5
        assertEquals(6d,mCalculator.sum(1d,5d),0);
    }

    @Test
    public void substract() throws Exception {
        assertEquals(1d,mCalculator.substract(5d,4d),0);
    }

    @Test
    public void divide() throws Exception {
        assertEquals(4d,mCalculator.divide(20d,5d),0);
    }

    @Test
    public void multiply() throws Exception {
        assertEquals(10d,mCalculator.multiply(2d,5d),0);
    }

}