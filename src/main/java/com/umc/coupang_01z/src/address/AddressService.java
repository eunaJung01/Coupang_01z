package com.umc.coupang_01z.src.address;

import com.umc.coupang_01z.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressDao addressDao;
    private final AddressProvider addressProvider;
    private final JwtService jwtService;

    @Autowired
    public AddressService(AddressDao addressDao, AddressProvider addressProvider, JwtService jwtService) {
        this.addressDao = addressDao;
        this.addressProvider = addressProvider;
        this.jwtService = jwtService;

    }
}
