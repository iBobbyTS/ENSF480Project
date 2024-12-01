package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.Address;

import java.util.List;

public interface AddressServ {
    Address createAddress(Address address);
    Address getAddress(Long addressId);
}