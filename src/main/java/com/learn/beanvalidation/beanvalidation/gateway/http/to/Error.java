package com.learn.beanvalidation.beanvalidation.gateway.http.to;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Error {
    private String fieldName;
    private Object inputValue;
    private String rejectReason;
}
