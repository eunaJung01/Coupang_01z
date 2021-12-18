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

    // 음식점 리스트 조회
    public List<GetRestRes> getRest() throws BaseException {
        try {
            return restaurantDao.getRest();
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 리스트 조회, 카테고리 구분
    public List<GetRestRes> getRest(int categoryIdx) throws BaseException {
        try {
            return restaurantDao.getRest();
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 리스트 조회 - 별점 높은 순
    public List<GetRestRes> getRestByRate() throws BaseException {
        try {
            return restaurantDao.getRestByRate();
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 리스트 조회 - 별점 높은 순, 카테고리 구분
    public List<GetRestRes> getRestByRate(int categoryIdx) throws BaseException {
        try {
            return restaurantDao.getRestByRate(categoryIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 리스트 조회 - 치타 배달
    public List<GetRestRes> getRestByCheetah() throws BaseException {
        try {
            List<GetRestRes> result = restaurantDao.getRestByCheetah();
            if (result.isEmpty()) {
                throw new NullPointerException();
            } else {
                return result;
            }
        } catch (NullPointerException exception) {
            throw new BaseException(NO_RESULT); // 검색 결과 없음
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 리스트 조회 - 치타 배달, 카테고리 구분
    public List<GetRestRes> getRestByCheetah(int categoryIdx) throws BaseException {
        try {
            List<GetRestRes> result = restaurantDao.getRestByCheetah(categoryIdx);
            if (result.isEmpty()) {
                throw new NullPointerException();
            } else {
                return result;
            }
        } catch (NullPointerException exception) {
            throw new BaseException(NO_RESULT); // 검색 결과 없음
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 리스트 조회 - 배달비
    public List<GetRestRes> getRestByDeliveryFee(int deliveryFee) throws BaseException {
        try {
            List<GetRestRes> result = restaurantDao.getRestByDeliveryFee(deliveryFee);
            if (result.isEmpty()) {
                throw new NullPointerException();
            } else {
                return result;
            }
        } catch (NullPointerException exception) {
            throw new BaseException(NO_RESULT); // 검색 결과 없음
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 리스트 조회 - 배달비, 카테고리 구분
    public List<GetRestRes> getRestByDeliveryFee(int categoryIdx, int deliveryFee) throws BaseException {
        try {
            List<GetRestRes> result = restaurantDao.getRestByDeliveryFee(categoryIdx, deliveryFee);
            if (result.isEmpty()) {
                throw new NullPointerException();
            } else {
                return result;
            }
        } catch (NullPointerException exception) {
            throw new BaseException(NO_RESULT); // 검색 결과 없음
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 리스트 조회 - 최소 주문비
    public List<GetRestRes> getRestByMinOrderFee(int minOrderFee) throws BaseException {
        try {
            List<GetRestRes> result = restaurantDao.getRestByMinOrderFee(minOrderFee);
            if (result.isEmpty()) {
                throw new NullPointerException();
            } else {
                return result;
            }
        } catch (NullPointerException exception) {
            throw new BaseException(NO_RESULT); // 검색 결과 없음
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 리스트 조회 - 최소 주문비, 카테고리 구분
    public List<GetRestRes> getRestByMinOrderFee(int categoryIdx, int minOrderFee) throws BaseException {
        try {
            List<GetRestRes> result = restaurantDao.getRestByMinOrderFee(categoryIdx, minOrderFee);
            if (result.isEmpty()) {
                throw new NullPointerException();
            } else {
                return result;
            }
        } catch (NullPointerException exception) {
            throw new BaseException(NO_RESULT); // 검색 결과 없음
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

}
