package com.home.service.homeservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFilterReqDTO {

    private String customerUsername;
    private String expertUsername;
    private Timestamp startOrderTime;
    private Timestamp endOrderTime;
    private String request_status;
    private String serviceName;
    private String subServiceName;
}


