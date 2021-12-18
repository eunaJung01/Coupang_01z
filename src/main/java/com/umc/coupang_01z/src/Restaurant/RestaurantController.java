package com.umc.coupang_01z.src.Restaurant;

import com.umc.coupang_01z.src.Restaurant.model.GetRestRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.umc.coupang_01z.config.*;
import com.umc.coupang_01z.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/coupang-eats")
public class RestaurantController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final RestaurantProvider restaurantProvider;
    @Autowired
    private final RestaurantService restaurantService;
    @Autowired
    private final JwtService jwtService; // JWT 사용 시

    public RestaurantController(RestaurantProvider restaurantProvider, RestaurantService restaurantService, JwtService jwtService) {
        this.restaurantProvider = restaurantProvider;
        this.restaurantService = restaurantService;
        this.jwtService = jwtService;
    }

    /*
     * 카테고리 조회 : [GET] /category
     */
    @ResponseBody
    @GetMapping("/category")
    public BaseResponse<List<GetRestRes>> getCategory() throws BaseException {
        try {
            // TODO: 형식적 validation

            // Service(GET) or Provider 호출
            // Service - Select(R)
            // Provider - Insert, Update, Delete(CUD)

            List<GetRestRes> getRestRes = restaurantProvider.getCategory();
            return new BaseResponse<>(getRestRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

}
