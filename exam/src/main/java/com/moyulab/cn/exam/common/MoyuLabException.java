package com.moyulab.cn.exam.common;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MoyuLabException extends RuntimeException {

    public MoyuLabException(String message) {
        super(message);
    }

    public MoyuLabException(String message, Throwable cause) {
        super(message, cause);
    }

}
