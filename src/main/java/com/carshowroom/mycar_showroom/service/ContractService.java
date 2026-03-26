package com.carshowroom.mycar_showroom.service;

import com.carshowroom.mycar_showroom.entity.Contract;
import com.carshowroom.mycar_showroom.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import other repos

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    // Stub other repos for availability check

    public void createRentalContract(ContractRequest request) {
        // Check car availability, create Contract, update inventory
        Contract contract = new Contract();
        // set fields
        contractRepository.save(contract);
    }
}

class ContractRequest {
    // matching DTO
}
