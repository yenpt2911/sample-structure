package dataTest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.GlobalConstants;

import java.io.File;
import java.io.IOException;

public class EmployeeData {

    public static EmployeeData getEmployeeData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(new File(GlobalConstants.PROJECT_PATH + "\\src\\test\\java\\dataTest\\Employee.json"), EmployeeData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @JsonProperty("firstName")
    String firstName;

    @JsonProperty("lastName")
    String lastName;

    @JsonProperty("dayOfBirth")
    String dayOfBirth;

    @JsonProperty("monthOfBirth")
    String monthOfBirth;

    @JsonProperty("yearOfBirth")
    String yearOfBirth;

    @JsonProperty("email")
    String email;

    @JsonProperty("companyName")
    String companyName;

    @JsonProperty("passWord")
    String passWord;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public String getMonthOfBirth() {
        return monthOfBirth;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPassWord() {
        return passWord;
    }





}
