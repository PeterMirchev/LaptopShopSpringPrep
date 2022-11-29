package exam.service.impl;

import exam.model.dto.ShopSeedDTO;
import exam.model.dto.ShopSeedRootDTO;
import exam.model.entity.Shop;
import exam.model.entity.Town;
import exam.repository.ShopRepository;
import exam.service.ShopService;
import exam.service.TownService;
import exam.util.ValidationUtil;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {
    private TownService townService;
    private ValidationUtil validationUtil;
    private ShopRepository shopRepository;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;
    private final String READ_SHOP_SEED_XML_FILE_PATH = "src/main/resources/files/xml/shops.xml";

    public ShopServiceImpl(TownService townService,
                           ValidationUtil validationUtil,
                           ShopRepository shopRepository,
                           XmlParser xmlParser,
                           ModelMapper modelMapper) {
        this.townService = townService;
        this.validationUtil = validationUtil;
        this.shopRepository = shopRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;

    }

    @Override
    public boolean areImported() {
        return shopRepository.count() >0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(READ_SHOP_SEED_XML_FILE_PATH));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();


        ShopSeedRootDTO seedRootDTO = xmlParser.fromFile(READ_SHOP_SEED_XML_FILE_PATH, ShopSeedRootDTO.class);
        seedRootDTO.getShopsSeedDTO().stream().filter(dto -> validateShopDto(dto, sb))
                .map(dto -> {
                    Shop shop = modelMapper.map(dto, Shop.class);
                    shop.setTown(townService.findTownByName(dto.getTownNameDTO().getName()));
                    return shop;
                }).forEach(shopRepository::save);
        return sb.toString().trim();
    }

    @Override
    public Shop findShopByName(String s) {
        return shopRepository.findShopByName(s).orElse(null);
    }

    private boolean validateShopDto(ShopSeedDTO dto, StringBuilder sb) {
        boolean isValid = true;

        if (shopRepository.existsByName(dto.getName())) {
            isValid = false;
        } else {
            isValid = validationUtil.isValid(dto);
        }

        sb.append(isValid ? String.format("Successfully imported Shop %s - %s",dto.getName(),dto.getIncome()) : "Invalid shop").append(System.lineSeparator());

        return isValid;
    }


}
