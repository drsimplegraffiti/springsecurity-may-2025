package com.drsimple.jwtsecurity.employee;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore unknown properties during deserialization e.g if the JSON contains fields not present in this class
public class EmployeeRequest<T> {
    @JsonProperty(value = "userId", required = true) // Maps the JSON property "userId" to this field
    private Long loggedInUser;
    private String timezone; // Timezone of the logged-in user, can be used for date formatting or other purposes
    private T data;
}
