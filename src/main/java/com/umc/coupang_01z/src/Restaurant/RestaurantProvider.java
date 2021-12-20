package com.umc.coupang_01z.src.Restaurant;

import com.umc.coupang_01z.src.Restaurant.model.*;
import com.umc.coupang_01z.config.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umc.coupang_01z.utils.JwtService;

import java.util.ArrayList;
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

    // 메뉴 전체 조회
    public List<GetMenuRes> getMenuList(int restIdx) throws BaseException {
        try {
            return (restaurantDao.getMenuList(restIdx));

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 특정 메뉴 조회
    public GetMenuRes getMenu(int menuIdx) throws BaseException {
        try {
            return restaurantDao.getMenu(menuIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 옵션 조회
    /*
     * Option & Option Child 조회 : [GET] /menu/:restIdx/:menuIdx
     *
     * in Option table
     *      1. restIdx != null : 모든 메뉴에 공통적으로 뜨는 옵션
     *         menuIdx != null : 해당 메뉴에만 뜨는 옵션
     *      2. hasChild = 1 : OptionChild 존재 O
     *                  = 0 : OptionChild 존재 X
     * Option으로 띄워줘야 하는 값이 없는 경우 : 안드로이드에서 메뉴 가격, 수량 선택만 띄워줌
     */
//    public List<GetOptionRes> getOption(int restIdx, int menuIdx) throws BaseException {
//        try {
//            List<GetOptionRes> getOptionRes = new ArrayList<>();
//
//            // 해당 메뉴에만 뜨는 옵션인가?
//            if (!restaurantDao.isMenuIdxNull(menuIdx)) {
//                if (restaurantDao.hasMenuOptionChild(menuIdx)) { // OptionChild 존재 O
//                    getOptionRes.add(restaurantDao.getMenuOptionChild(menuIdx));
//                } else { // OptionChild 존재 X
//                    getOptionRes.add(restaurantDao.getMenuOption(menuIdx));
//                }
//            }
//            // 모든 메뉴에 공통적으로 뜨는 옵션인가?
//            if (!restaurantDao.isRestIdxNull(restIdx)) {
//                if (restaurantDao.hasRestOptionChild(restIdx)) { // OptionChild 존재 O
//                    getOptionRes.add(restaurantDao.getRestOptionChild(restIdx));
//                } else { // OptionChild 존재 X
//                    getOptionRes.add(restaurantDao.getRestOption(restIdx));
//                }
//            }
//            return getOptionRes;
//
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
//        }
//    }

}
