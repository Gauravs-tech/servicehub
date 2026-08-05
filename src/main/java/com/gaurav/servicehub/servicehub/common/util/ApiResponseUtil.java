package com.gaurav.servicehub.servicehub.common.util;

import com.gaurav.servicehub.servicehub.common.dto.ApiResponse;

public final class ApiResponseUtil {

    private ApiResponseUtil() {
    }

    public static <T> ApiResponse<T> success(
            T data,
            String message
    ) {

        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();

    }

}
