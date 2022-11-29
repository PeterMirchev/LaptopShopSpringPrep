package exam.model.dto;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "shop")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopSeedDTO {
    @Size(min = 4)
    @XmlElement(name = "address")
    private String address;

    @Min(1)
    @Max(50)
    @XmlElement(name = "employee-count")
    private Integer employeeCount;

    @Min(20000)
    @XmlElement(name = "income")
    private BigDecimal income;

    @Size(min = 4)
    @XmlElement(name = "name")
    private String name;

    @Min(150)
    @XmlElement(name = "shop-area")
    private Integer shopArea;
    @XmlElement(name = "town")
    private TownNameDTO townNameDTO;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getShopArea() {
        return shopArea;
    }

    public void setShopArea(Integer shopArea) {
        this.shopArea = shopArea;
    }

    public TownNameDTO getTownNameDTO() {
        return townNameDTO;
    }

    public void setTownNameDTO(TownNameDTO townNameDTO) {
        this.townNameDTO = townNameDTO;
    }
}
