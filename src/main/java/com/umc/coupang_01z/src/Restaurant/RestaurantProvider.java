package com.umc.coupang_01z.src.Restaurant;

import com.umc.coupang_01z.src.Restaurant.model.*;
import com.umc.coupang_01z.config.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umc.coupang_01z.utils.JwtService;

import java.util.List;

import static com.umc.coupang_01z.config.BaseResponseStatus.*;


@Service
public class RestaurantProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestaurantDao restaurantDao;
    private final JwtService jwtService;

    @Autowired
    public RestaurantProvider(RestaurantDao restaurantDao, JwtService jwtService) {
        this.restaurantDao = restaurantDao;
        this.jwtService = jwtService;
    }

    // 카테고리 조회
    public List<GetCategoryRes> getCategory() throws BaseException {
        try {
            List<GetCategoryRes> result = restaurantDao.getCategory();
            return result;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 조회
    public List<GetRestRes> getRest() throws BaseException {
        try {
            List<GetRestRes> result = restaurantDao.getRest();
            return result;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

//    public List<GetRestRes> getRestByRate() throws BaseException {
//        try {
//            List<GetRestRes> result = restaurantDao.getRestByRate();
//            return result;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
//        }
//    }

}
