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
            return restaurantDao.getCategory();

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 리스트 조회
    public List<GetRestListRes> getRest() throws BaseException {
        try {
            return restaurantDao.getRest();

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 리스트 조회, 카테고리 구분
    public List<GetRestListRes> getRest(int categoryIdx) throws BaseException {
        try {
            return restaurantDao.getRest();

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 리스트 조회 - 별점 높은 순
    public List<GetRestListRes> getRestByRate() throws BaseException {
        try {
            return restaurantDao.getRestByRate();

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 리스트 조회 - 별점 높은 순, 카테고리 구분
    public List<GetRestListRes> getRestByRate(int categoryIdx) throws BaseException {
        try {
            return restaurantDao.getRestByRate(categoryIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 리스트 조회 - 치타 배달
    public List<GetRestListRes> getRestByCheetah() throws BaseException {
        try {
            List<GetRestListRes> result = restaurantDao.getRestByCheetah();
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
    public List<GetRestListRes> getRestByCheetah(int categoryIdx) throws BaseException {
        try {
            List<GetRestListRes> result = restaurantDao.getRestByCheetah(categoryIdx);
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
    public List<GetRestListRes> getRestByDeliveryFee(int deliveryFee) throws BaseException {
        try {
            List<GetRestListRes> result = restaurantDao.getRestByDeliveryFee(deliveryFee);
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
    public List<GetRestListRes> getRestByDeliveryFee(int categoryIdx, int deliveryFee) throws BaseException {
        try {
            List<GetRestListRes> result = restaurantDao.getRestByDeliveryFee(categoryIdx, deliveryFee);
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
    public List<GetRestListRes> getRestByMinOrderFee(int minOrderFee) throws BaseException {
        try {
            List<GetRestListRes> result = restaurantDao.getRestByMinOrderFee(minOrderFee);
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
    public List<GetRestListRes> getRestByMinOrderFee(int categoryIdx, int minOrderFee) throws BaseException {
        try {
            List<GetRestListRes> result = restaurantDao.getRestByMinOrderFee(categoryIdx, minOrderFee);
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

    // 음식점 조회
    public GetRestRes getRestaurant(int restIdx) throws BaseException {
        try {
            return (restaurantDao.getRestaurant(restIdx));

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 메뉴 조회
//    public GetRestRes getMenu(int restIdx) throws BaseException {
//        try {
//            return (restaurantDao.getMenu(restIdx));
//
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
//        }
//    }
}
