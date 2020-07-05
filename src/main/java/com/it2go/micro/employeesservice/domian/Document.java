package com.it2go.micro.employeesservice.domian;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {

    private String name;
    private String contentType;
    private byte[] content;
}
