package exam.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "shops")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopSeedRootDTO {
    @XmlElement(name = "shop")
    private List<ShopSeedDTO> shopsSeedDTO;

    public List<ShopSeedDTO> getShopsSeedDTO() {
        return shopsSeedDTO;
    }

    public void setShopsSeedDTO(List<ShopSeedDTO> shopsSeedDTO) {
        this.shopsSeedDTO = shopsSeedDTO;
    }
}
