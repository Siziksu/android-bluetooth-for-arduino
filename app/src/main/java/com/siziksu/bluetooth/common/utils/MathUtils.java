package com.siziksu.bluetooth.common.utils;

/**
 * Class with some util methods for maths.
 */
public class MathUtils {

    private static final int COMMAND_MIN_VALUE = 0;
    private static final int COMMAND_MAX_VALUE = 253;

    private MathUtils() {}

    /**
     * Transforms an unsigned int value into signed byte.
     * If the value is greater than COMMAND_MAX_VALUE will return -1
     *
     * @param string string integer value
     *
     * @return byte
     */
    public static byte getByteFromUnsignedStringNumber(String string) {
        int value = Integer.parseInt(string);
        return (value >= COMMAND_MIN_VALUE && value <= COMMAND_MAX_VALUE) ? (byte) value : -1;
    }
}
