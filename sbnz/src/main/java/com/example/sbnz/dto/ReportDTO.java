package com.example.sbnz.dto;

public class ReportDTO {

    String firstName;
    String lastName;
    String param;

    public ReportDTO() {
    }

    public ReportDTO(String firstName, String lastName, String param) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.param = param;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisease() {
        return param;
    }

    public void setDisease(String disease) {
        this.param = disease;
    }
}
