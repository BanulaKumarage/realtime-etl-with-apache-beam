package com.springbootprojects.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IotEvent implements Serializable {
    private String deviceId;
    private Double temperature;
    private String unit;
}
