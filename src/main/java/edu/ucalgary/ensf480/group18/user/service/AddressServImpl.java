package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.Address;
import edu.ucalgary.ensf480.group18.user.repository.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServImpl implements AddressServ{

    @Autowired
    private AddressRepo addressRepo;

    @Override
    public Address createAddress(Address address) {
        return addressRepo.save(address);
    }

    @Override
    public Address getAddress(Long addressId) {
        return addressRepo.findById(addressId).orElse(null);
    }
}
