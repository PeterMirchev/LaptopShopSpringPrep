package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.CustomerSeedDTO;
import exam.model.dto.TownNameJsonDTO;
import exam.model.entity.Customer;
import exam.repository.CustomerRepository;
import exam.service.CustomerService;
import exam.service.TownService;
import exam.util.ValidationUtil;
import exam.util.impl.DateAdapter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {
    private Gson gson;
    private TownService townService;
    private ModelMapper modelMapper;
    private CustomerRepository customerRepository;
    private ValidationUtil validationUtil;
    private final String READ_JSON_FILE_PATH = "src/main/resources/files/json/customers.json";

    public CustomerServiceImpl(Gson gson, TownService townService, ModelMapper modelMapper, CustomerRepository customerRepository, ValidationUtil validationUtil) {
        this.gson = gson;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(READ_JSON_FILE_PATH));
    }

    @Override
    public String importCustomers() throws IOException {
        StringBuilder sb = new StringBuilder();

        CustomerSeedDTO[] customerDTO = gson.fromJson(new FileReader(READ_JSON_FILE_PATH), CustomerSeedDTO[].class);
        Arrays.stream(customerDTO).filter(dto -> validateCustomersSeedDto(dto, sb))
                .map(dto -> {
                    Customer customer = modelMapper.map(dto, Customer.class);
                    customer.setTown(townService.findTownByName(dto.getTown().getName()));

                    return customer;
                }).forEach(customerRepository::save);
        return sb.toString().trim();
    }

    private boolean validateCustomersSeedDto(CustomerSeedDTO dto, StringBuilder sb) {
        boolean isValid;

        if(customerRepository.existsByEmail(dto.getEmail())) {
            isValid = false;
        } else {
            isValid = validationUtil.isValid(dto);
        }

        sb.append(isValid ? String.format("Successfully imported Customer %s %s - %s",
                dto.getFirstName(), dto.getLastName(), dto.getEmail()) : "Invalid Customer").append(System.lineSeparator());

        return isValid;
    }
}
