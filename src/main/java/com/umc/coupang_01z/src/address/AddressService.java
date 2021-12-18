package com.umc.coupang_01z.src.address;

import com.umc.coupang_01z.config.BaseException;
import com.umc.coupang_01z.src.address.model.*;
import com.umc.coupang_01z.src.user.model.GetUserRes;
import com.umc.coupang_01z.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.umc.coupang_01z.config.BaseResponseStatus.DATABASE_ERROR;

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

    // [POST] 유저의 주소 추가
    public PostAddressRes postAddress(PostAddressReq postAddressReq) throws BaseException {
        try {
            return new PostAddressRes(addressDao.createAddress(postAddressReq));
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
