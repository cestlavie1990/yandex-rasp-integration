package com.minakov.yandexraspintegration.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;

@UtilityClass
public class MapperUtils {
    @Named("NullIfBlank")
    public static String setNullIfBlank(final String str) {
        return StringUtils.defaultIfBlank(str, null);
    }
}
