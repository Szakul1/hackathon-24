package pl.envelo.academy.app.company.structure.model;

import java.util.List;

public class EmployeeModel {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String description;
    private List<EmployeeModel> subordinates;
    private String url;

    public EmployeeModel() {
    }

    public EmployeeModel(Long id, String name, String lastName, String email, String phoneNumber, String description, List<EmployeeModel> subordinates, String url) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.subordinates = subordinates;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<EmployeeModel> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<EmployeeModel> subordinates) {
        this.subordinates = subordinates;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "EmployeeModel{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", description='" + description + '\'' +
                ", subordinates=" + subordinates +
                ", url='" + url + '\'' +
                '}';
    }
}
