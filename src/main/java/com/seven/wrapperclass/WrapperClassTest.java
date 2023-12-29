package com.seven.wrapperclass;

import org.junit.Test;

import java.math.BigInteger;

public class WrapperClassTest {
    @Test
    public void testBigInteger() {
        BigInteger bigInteger = new BigInteger("123456");
        System.out.println(bigInteger.toString(2));
        System.out.println(bigInteger.toString(16));

        Integer integer = new Integer(133);
        System.out.println(integer.toHexString(16));
        System.out.println(Integer.lowestOneBit(133));
    }
}
