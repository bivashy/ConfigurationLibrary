package com.ubivashka.configuration.resolver.scalar;

import java.util.Locale;
import java.util.function.BiFunction;

/*
 * Some modificated version from github
 *
 * @see <a href="https://github.com/SpongePowered/Configurate/blob/master/core/src/main/java/org/spongepowered/configurate/serialize/NumericSerializers.java">Source code</a>
 */
public class NumberResolvers {
    private static final float EPSILON = Float.MIN_NORMAL;

    private NumberResolvers() {
    }

    public static final ScalarObjectResolver<Float> FLOAT = (value) -> {
        if (value == null)
            return null;
        if (value instanceof Number) {
            final double d = ((Number) value).doubleValue();
            final int exponent = Math.getExponent(d);
            if (d != 0d && Double.isFinite(d) && exponent >= Float.MIN_EXPONENT && exponent <= Float.MAX_EXPONENT)
                throw new ResolveException("Value " + d + " cannot be represented as a float without significant loss of precision");
            return (float) d;
        } else if (value instanceof CharSequence) {
            String stringValue = value.toString();
            if (stringValue.endsWith("f") || stringValue.endsWith("F")) {
                stringValue = stringValue.substring(0, stringValue.length() - 1);
            }
            try {
                return Float.parseFloat(stringValue);
            } catch(final NumberFormatException ex) {
                throw new ResolveException(ex);
            }
        } else {
            throw new CannotResolveTypeException(value, "float");
        }
    };
    public static final ScalarObjectResolver<Double> DOUBLE = (value) -> {
        if (value == null)
            return null;
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof CharSequence) {
            String stringValue = value.toString();
            if (stringValue.endsWith("d") || stringValue.endsWith("D")) {
                stringValue = stringValue.substring(0, stringValue.length() - 1);
            }
            try {
                return Double.parseDouble(stringValue);
            } catch(final NumberFormatException ex) {
                throw new ResolveException(ex);
            }
        } else {
            throw new CannotResolveTypeException(value, "double");
        }
    };
    public static final ScalarObjectResolver<Byte> BYTE = (value) -> {
        if (value == null)
            return null;
        if (value instanceof Float || value instanceof Double) {
            final double absVal = Math.abs(((Number) value).doubleValue());
            if ((absVal - Math.floor(absVal)) < EPSILON && absVal <= Byte.MAX_VALUE) {
                return (byte) absVal;
            } else {
                throw new CannotResolveTypeException(value, "byte");
            }
        }

        if (value instanceof Number) {
            final long full = ((Number) value).longValue();
            if (full > Byte.MAX_VALUE || full < Byte.MIN_VALUE) {
                throw new ResolveException("Value " + full + " is out of range for a byte ([" + Byte.MIN_VALUE + "," + Byte.MAX_VALUE + "])");
            }
            return (byte) full;
        }

        if (value instanceof CharSequence) {
            return parseNumber(value.toString(), Byte::parseByte, Byte::parseByte, "b");
        }
        throw new CannotResolveTypeException(value, "byte");
    };
    public static final ScalarObjectResolver<Short> SHORT = (value) -> {
        if (value == null)
            return null;
        if (value instanceof Float || value instanceof Double) {
            final double absVal = Math.abs(((Number) value).doubleValue());
            if ((absVal - Math.floor(absVal)) < EPSILON && absVal <= Short.MAX_VALUE) {
                return (short) absVal;
            } else {
                throw new CannotResolveTypeException(value, "short");
            }
        }

        if (value instanceof Number) {
            final long full = ((Number) value).longValue();
            if (full > Short.MAX_VALUE || full < Short.MIN_VALUE) {
                throw new ResolveException("Value " + full + " is out of range for a short ([" + Short.MIN_VALUE + "," + Short.MAX_VALUE + "])");
            }
            return (short) full;
        }

        if (value instanceof CharSequence) {
            return parseNumber(value.toString(), Short::parseShort, Short::parseShort, "s");
        }
        throw new CannotResolveTypeException(value, "short");
    };
    public static final ScalarObjectResolver<Integer> INTEGER = (value) -> {
        if (value == null)
            return null;
        if (value instanceof Float || value instanceof Double) {
            final double absVal = Math.abs(((Number) value).doubleValue());
            if ((absVal - Math.floor(absVal)) < EPSILON && absVal <= Integer.MAX_VALUE) {
                return (int) absVal;
            } else {
                throw new CannotResolveTypeException(value, "int");
            }
        }

        if (value instanceof Number) {
            final long full = ((Number) value).longValue();
            if (full > Integer.MAX_VALUE || full < Integer.MIN_VALUE) {
                throw new ResolveException("Value " + full + " is out of range for an integer ([" + Integer.MIN_VALUE + "," + Integer.MAX_VALUE + "])");
            }
            return (int) full;
        }

        if (value instanceof CharSequence) {
            return parseNumber(value.toString(), Integer::parseInt, Integer::parseUnsignedInt, "i");
        }
        throw new CannotResolveTypeException(value, "int");
    };
    public static final ScalarObjectResolver<Long> LONG = (value) -> {
        if (value == null)
            return null;
        if (value instanceof Float || value instanceof Double) {
            final double absVal = Math.abs(((Number) value).doubleValue());
            if ((absVal - Math.floor(absVal)) < EPSILON && absVal <= Long.MAX_VALUE) {
                return (long) absVal;
            } else {
                throw new CannotResolveTypeException(value, "long");
            }
        }

        if (value instanceof Number) {
            return ((Number) value).longValue();
        }

        if (value instanceof CharSequence) {
            return parseNumber(value.toString(), Long::parseLong, Long::parseUnsignedLong, "l");
        }
        throw new CannotResolveTypeException(value, "long");
    };

    private static <T extends Number> T parseNumber(String input, final BiFunction<String, Integer, T> parseFunc,
                                                    final BiFunction<String, Integer, T> unsignedParseFunc, final String suffix) throws ResolveException {
        boolean unsigned = false;
        boolean negative = false;

        int startIdx = 0;
        int endIdx = input.length();

        // type suffix
        if (input.endsWith(suffix) || input.endsWith(suffix.toUpperCase(Locale.ROOT))) {
            --endIdx;
        }

        // unsigned
        if (endIdx > 0 && input.charAt(endIdx - 1) == 'u') {
            unsigned = true;
            --endIdx;
        }

        if (endIdx > startIdx && input.charAt(startIdx) == '-') {
            if (unsigned) {
                throw new ResolveException("Negative numbers cannot be unsigned! (both - prefix and u suffix were used)");
            }
            negative = true;
            ++startIdx;
        } else if (endIdx > startIdx && input.charAt(startIdx) == '+') { // skip, positive is the default
            ++startIdx;
        }

        // bases
        int radix = 10;
        if (input.startsWith("0x", startIdx)) { // hex
            radix = 16;
            startIdx += 2;
        } else if (input.length() > startIdx && input.charAt(startIdx) == '#') { // hex
            radix = 16;
            ++startIdx;
        } else if (input.startsWith("0b", startIdx)) { // binary
            radix = 2;
            startIdx += 2;
        }

        input = input.substring(startIdx, endIdx);

        if (negative) { // ugly but not super avoidable without knowing the number type
            input = "-" + input;
        }
        try {
            return (unsigned ? unsignedParseFunc : parseFunc).apply(input, radix);
        } catch(final IllegalArgumentException ex) {
            throw new ResolveException(ex);
        }
    }

}
