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

package com.minakov.yandexraspintegration.exception;

import java.io.Serial;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public abstract class AbstractException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AbstractException(@NonNull String message, Object... params) {
        super(getMessage(message, params));
    }

    public AbstractException(@NonNull Throwable cause, @NonNull String message, Object... params) {
        super(getMessage(message, params), cause);
    }

    public AbstractException(@NonNull Throwable cause) {
        super(cause);
    }

    @NonNull
    private static String getMessage(@NonNull String message, Object... params) {
        return String.format(message.replaceAll("\\{}", "%s"), params);
    }
}
