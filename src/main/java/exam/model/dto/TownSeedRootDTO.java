package exam.model.dto;

import exam.model.entity.Town;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "towns")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownSeedRootDTO {
    @XmlElement(name = "town")
    private List<TownSeedDTO> townsSeedDTO;


    public List<TownSeedDTO> getTownsSeedDTO() {
        return townsSeedDTO;
    }

    public void setTownsSeedDTO(List<TownSeedDTO> townsSeedDTO) {
        this.townsSeedDTO = townsSeedDTO;
    }
}
