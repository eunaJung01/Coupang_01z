package com.umc.coupang_01z.src.address;

import com.umc.coupang_01z.src.address.model.*;
import com.umc.coupang_01z.config.BaseException;
import com.umc.coupang_01z.config.BaseResponse;
import com.umc.coupang_01z.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupang-eats/addresses")
public class AddressController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AddressProvider addressProvider;
    @Autowired
    private final AddressService addressService;
    @Autowired
    private final JwtService jwtService;

    public AddressController(AddressProvider addressProvider, AddressService addressService, JwtService jwtService) {
        this.addressProvider = addressProvider;
        this.addressService = addressService;
        this.jwtService = jwtService;
    }
}
