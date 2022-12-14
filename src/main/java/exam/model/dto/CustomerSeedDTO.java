package exam.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import exam.util.impl.DateAdapter;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Date;

public class CustomerSeedDTO {
    @Expose
    @Size(min = 2)
    private String firstName;
    @Expose
    @Size(min = 2)
    private String lastName;
    @Expose
    @Email
    private String email;
    @Expose
    private String registeredOn;
    @Expose
    private TownNameJsonDTO town;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public TownNameJsonDTO getTown() {
        return town;
    }

    public void setTown(TownNameJsonDTO town) {
        this.town = town;
    }


    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }
}
