package com.drsimple.jwtsecurity.excelreader;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Terminal {

    @Id
    private String terminalId;

    private String serialNumber;
    private Boolean isEnabled;
    private Boolean isMapped;
    private String routeCode;
}