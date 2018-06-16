package com.siziksu.bluetooth.common.utils;

/**
 * Class with some util methods for maths.
 */
public class MathUtils {

    private MathUtils() {}

    /**
     * Transforms an unsigned int value into signed byte.
     * If the value is greater than 255 will return -1
     *
     * @param string string integer value
     *
     * @return byte
     */
    public static byte getByteFromUnsignedStringNumber(String string) {
        int value = Integer.parseInt(string);
        return (value >= 0 && value <= 255) ? (byte) value : -1;
    }
}
