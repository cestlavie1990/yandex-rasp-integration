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
import lombok.NonNull;
import org.springframework.lang.Nullable;

public class ObjectNotFoundException extends AbstractException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(@Nullable final Object id) {
        super("Object not found, id={}", id);
    }

    public ObjectNotFoundException(@NonNull final String message, final Object... params) {
        super(message, params);
    }

    public ObjectNotFoundException(@NonNull final Throwable cause, @NonNull final String message,
            final Object... params) {
        super(cause, message, params);
    }

    public ObjectNotFoundException(@NonNull final Throwable cause) {
        super(cause);
    }
}
