package com.wcf.server.dto;

import lombok.Data;

@Data
public class ExcelHeadDTO {
    private String property;
    private String title;

    public ExcelHeadDTO(String property, String title) {
        this.property = property;
        this.title = title;
    }
}
