package com.example.test_project.Models.Common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
public class UserInformationModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * User first name
     */
    @JsonProperty("firstName")
    private String firstName;

    /**
     * User last name
     */
    @JsonProperty("lastName")
    private String lastName;

    /**
     * displayName = firstName + lastName;
     */
    @JsonProperty("displayName")
    private String displayName;

    /**
     * User email
     */
    @JsonProperty("email")
    private String email;

    public UserInformationModel(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = firstName + lastName;
        this.email = email;
    }
}
