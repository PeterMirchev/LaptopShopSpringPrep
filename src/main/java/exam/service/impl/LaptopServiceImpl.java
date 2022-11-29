package exam.service.impl;

import com.google.gson.Gson;
import exam.model.WarrantyType;
import exam.model.dto.LaptopSeedDTO;
import exam.model.entity.Laptop;
import exam.repository.LaptopRepository;
import exam.service.LaptopService;
import exam.service.ShopService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LaptopServiceImpl implements LaptopService {
    private ShopService shopService;
    private final String READ_JSON_FILE_IMPORT = "src/main/resources/files/json/laptops.json";
    private Gson gson;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private LaptopRepository laptopRepository;

    public LaptopServiceImpl(ShopService shopService,
                             Gson gson,
                             ModelMapper modelMapper,
                             ValidationUtil validationUtil,
                             LaptopRepository laptopRepository) {
        this.shopService = shopService;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.laptopRepository = laptopRepository;
    }

    @Override
    public boolean areImported() {
        return laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(READ_JSON_FILE_IMPORT));
    }

    @Override
    public String importLaptops() throws IOException {
        StringBuilder sb = new StringBuilder();

        LaptopSeedDTO[] laptopDTOS = gson.fromJson(new FileReader(READ_JSON_FILE_IMPORT), LaptopSeedDTO[].class);
        Arrays.stream(laptopDTOS).filter(dto -> validateLaptopImportDto(dto, sb))
                .map(dto -> {
                   Laptop laptop =  modelMapper.map(dto, Laptop.class);
                   laptop.setShop(shopService.findShopByName(dto.getShop().getName()));
                   laptop.setWarrantyType(dto.getWarrantyType());
                   return laptop;

                }).forEach(laptopRepository::save);

        return sb.toString().trim();
    }

    @Override
    public String exportBestLaptops() {
        return Objects.requireNonNull(laptopRepository.findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddress()
                        .orElse(null))
                .stream().map(Laptop::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private boolean validateLaptopImportDto(LaptopSeedDTO dto, StringBuilder sb) {
        boolean isValid;

        //BASIC, PREMIUM, LIFETIME
        if (laptopRepository.findLaptopByMacAddress(dto.getMacAddress()).isPresent()) {
            isValid = false;
        } else {
            isValid = validationUtil.isValid(dto);
        }

        sb.append(isValid ? String.format("Successfully imported Laptop %s - %s - %s - %s", dto.getMacAddress()
        , dto.getCpuSpeed(), dto.getRam(), dto.getStorage()) : "Invalid Laptop").append(System.lineSeparator());

        return isValid;
    }



    public Laptop findLaptopByMacAddress(String s) {
        return laptopRepository.findLaptopByMacAddress(s).orElse(null);
    }


}
