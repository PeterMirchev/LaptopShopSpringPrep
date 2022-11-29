package exam.model.entity;


import exam.model.WarrantyType;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "laptops")
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mac_address",nullable = false)
    private String macAddress;
    @Column(name = "cpu_speed", nullable = false)
    private Double cpuSpeed;
    @Column(nullable = false)
    private Integer ram;
    @Column(nullable = false)
    private Integer storage;
    @Column(columnDefinition="TEXT",nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WarrantyType warrantyType;
    @ManyToOne(targetEntity = Shop.class)
    private Shop shop;


    public Long getId() {
        return id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Double getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(Double cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public WarrantyType getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(WarrantyType warrantyType) {
        this.warrantyType = warrantyType;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        //•	"Laptop - {mac address}
        //*Cpu speed - {cpu speed}
        //**Ram - {ram}
        //***Storage - {storage}
        //****Price - {price}
        //#Shop name - {name of the shop}
        //##Town - {the name of the town of shop}
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Laptop - %s", macAddress)).append(System.lineSeparator());
        sb.append(String.format("*Cpu speed - %.2f", cpuSpeed)).append(System.lineSeparator());
        sb.append(String.format("**Ram - %d", ram)).append(System.lineSeparator());
        sb.append(String.format("***Storage - %d", storage)).append(System.lineSeparator());
        sb.append(String.format("****Price - %.2f", price)).append(System.lineSeparator());
        sb.append(String.format("#Shop name - %s", shop.getName())).append(System.lineSeparator());
        sb.append(String.format("##Town - %s", shop.getTown().getName())).append(System.lineSeparator());

        return sb.toString();
    }
}
