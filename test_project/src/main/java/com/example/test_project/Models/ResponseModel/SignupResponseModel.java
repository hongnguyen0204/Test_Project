package com.example.test_project.Models.ResponseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
public class SignupResponseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID of the user in the database
     */
    @JsonProperty("id")
    private int id;

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

    public SignupResponseModel(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = firstName + lastName;
        this.email = email;
    }
}
