package com.umc.coupang_01z.src.address;

import com.umc.coupang_01z.src.address.model.*;
import com.umc.coupang_01z.config.BaseException;
import com.umc.coupang_01z.config.BaseResponse;
import com.umc.coupang_01z.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.umc.coupang_01z.config.BaseResponseStatus.*;

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

    /**
     * 유저의 등록된 주소 조회 API
     * [GET] /coupang-eats/addresses/:userIdx
     **/
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<GetAddressRes> getAddress(@PathVariable("userIdx") int userIdx) {
        try {
            // jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            // jwt으로부터 추출한 userIdx와 접근한 유저의 인덱스가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetAddressRes getAddressRes = addressProvider.getAddress(userIdx);
            return new BaseResponse<>(getAddressRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저의 주소 추가 API
     * [POST] /app/posts
     */
    @PostMapping("")
    public BaseResponse<PostAddressRes> postAddress(@RequestBody PostAddressReq postAddressReq) {
        try {
            int userIdx = postAddressReq.getUserIdx();
            // jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            // jwt으로부터 추출한 userIdx와 접근한 유저의 인덱스가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            return new BaseResponse<>(addressService.postAddress(postAddressReq));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
