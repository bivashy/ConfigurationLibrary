package com.ubivashka.configuration.resolver.scalar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class BooleanResolver implements ScalarObjectResolver<Boolean> {
    private static final Set<String> TRUE_VALUES = new HashSet<>(Arrays.asList("true", "t", "yes", "y", "1"));
    private static final Set<String> FALSE_VALUES = new HashSet<>(Arrays.asList("false", "f", "no", "n", "0"));

    @Override
    public Boolean resolveNullable(Object object) {
        if (object == null)
            return null;
        if (object instanceof Number)
            return object.equals(1);
        String stringValue = object.toString().toLowerCase(Locale.ROOT);
        if (TRUE_VALUES.contains(stringValue))
            return true;
        if (FALSE_VALUES.contains(stringValue))
            return false;
        throw new CannotResolveTypeException(object, "boolean");
    }

}
