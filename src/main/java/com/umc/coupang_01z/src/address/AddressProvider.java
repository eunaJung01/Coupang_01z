package com.umc.coupang_01z.src.address;

import com.umc.coupang_01z.src.address.model.*;
import com.umc.coupang_01z.config.BaseException;
import com.umc.coupang_01z.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.umc.coupang_01z.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class AddressProvider {
    private final AddressDao addressDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AddressProvider(AddressDao addressDao, JwtService jwtService) {
        this.addressDao = addressDao;
        this.jwtService = jwtService;
    }

    // [GET] 주소 조회
    public GetAddressRes getAddress(int userIdx) throws BaseException {
        try {
            GetAddressRes getAddressRes = addressDao.getAddress(userIdx);
            return getAddressRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
