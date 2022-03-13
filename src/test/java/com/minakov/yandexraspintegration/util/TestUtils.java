/*
 *  =======================================================================
 *
 *  Copyright (c) 2020 Northern Capital Gateway, LLC. All rights reserved.
 *
 *  This software is the confidential and proprietary information of
 *  Northern Capital Gateway, LLC.
 *  You shall not disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with
 *  Northern Capital Gateway, LLC
 *
 *  =======================================================================
 */

package com.minakov.yandexraspintegration.util;

import java.io.IOException;
import java.nio.file.Files;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.Resource;

@UtilityClass
public class TestUtils {
    @NonNull
    public static String readResource(@NonNull Resource resource) {
        try {
            return Files.readString(resource.getFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException("Filed to read resource", e);
        }
    }
}
