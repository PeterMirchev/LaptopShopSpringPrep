package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.TownSeedDTO;
import exam.model.dto.TownSeedRootDTO;
import exam.model.entity.Town;
import exam.repository.TownRepository;
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
public class TownServiceImpl implements TownService {
    TownServiceImpl townService;
    private XmlParser xmlParser;
    private TownRepository townRepository;
    private Gson gson;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private final String READ_FILE_PATH = "src/main/resources/files/xml/towns.xml";

    public TownServiceImpl(XmlParser xmlParser, TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.xmlParser = xmlParser;
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(READ_FILE_PATH));
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        TownSeedRootDTO rootDTO = xmlParser.fromFile(READ_FILE_PATH, TownSeedRootDTO.class);

        rootDTO.getTownsSeedDTO()
                .stream()
                .filter(dto -> validateTownsSeedDto(dto, sb))
                .map(dto -> {
                    return modelMapper.map(dto, Town.class);
                }).forEach(townRepository::save);


        return sb.toString().trim();
    }

    @Override
    public Town findTownByName(String name) {
        return townRepository.findTownByName(name).orElse(null);
    }

    private boolean validateTownsSeedDto(TownSeedDTO dto, StringBuilder sb) {
        boolean isValid;

        if(townRepository.existsByName(dto.getName())) {
            isValid = false;
        } else {
            isValid = validationUtil.isValid(dto);
        }

        sb.append(isValid ? String.format("Successfully imported Town %s", dto.getName()) : "Invalid town").append(System.lineSeparator());

        return isValid;
    }
}
