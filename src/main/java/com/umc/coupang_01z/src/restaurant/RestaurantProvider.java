package com.umc.coupang_01z.src.restaurant;

import com.umc.coupang_01z.src.restaurant.model.*;
import com.umc.coupang_01z.config.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umc.coupang_01z.utils.JwtService;

import java.util.*;

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
    public List<GetRestListRes> filterRestaurants(String[] filtering, int sortIdx, int userIdx) throws BaseException {
        try {
            List<GetRestListRes> filteredRestaurants = restaurantDao.getRestByFiltering(filtering, sortIdx);

            // set distance field
            List<Location> location = new ArrayList<>();
            location.add(restaurantDao.getLocation(userIdx)); // location.get(0) : userIdx, latitude, longitude (사용자 위치정보 - status = 'C')
            for (GetRestListRes restaurant : filteredRestaurants) {
                location.add(restaurantDao.getRestLocation(restaurant.getRestIdx())); // restIdx, restLatitude, restLongitude (음식점 위치정보)
            }
            getDistances(location, filteredRestaurants); // 사용자 위치와 음식점 간의 거리 계산

            // set rateNum field
            for (GetRestListRes filteredRestaurant : filteredRestaurants) {
                filteredRestaurant.setRateNum(restaurantDao.getRateNum(filteredRestaurant.getRestIdx())); // 리뷰 개수 반환
            }

            return filteredRestaurants;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 사용자 위치와 음식점 간의 거리 계산
    private void getDistances(List<Location> location, List<GetRestListRes> filteredRestaurants) {
        double userLat = location.get(0).getLat(); // 사용자 latitude
        double userLng = location.get(0).getLng(); // 사용자 longitude

        for (int i = 1; i < location.size(); i++) {
            double distance = distanceInKilometerByHaversine(userLat, userLng, location.get(i).getLat(), location.get(i).getLat()); // 최단 거리 구하기
            filteredRestaurants.get(i - 1).setDistance(distance); // 각각의 distance 필드에 저장
        }
    }

    // Haversine Formula : 최단 거리 구하기 (https://kayuse88.github.io/haversine/)
    public double distanceInKilometerByHaversine(double x1, double y1, double x2, double y2) {
        double distance;
        double radius = 6371; // 지구 반지름(km)
        double toRadian = Math.PI / 180;

        double deltaLatitude = Math.abs(x1 - x2) * toRadian;
        double deltaLongitude = Math.abs(y1 - y2) * toRadian;

        double sinDeltaLat = Math.sin(deltaLatitude / 2);
        double sinDeltaLng = Math.sin(deltaLongitude / 2);
        double squareRoot = Math.sqrt(sinDeltaLat * sinDeltaLat + Math.cos(x1 * toRadian) * Math.cos(x2 * toRadian) * sinDeltaLng * sinDeltaLng);

        distance = 2 * radius * Math.asin(squareRoot);

        return distance;
    }

//    // 매장 정렬 : 추천순
//    public List<GetRestListRes> sortByRecommend(List<GetRestListRes> getRestListRes) {
//    }

    // 매장 정렬 : 주문 많은 순
    public List<GetRestListRes> sortByOrderNum(List<GetRestListRes> getRestListRes) throws BaseException {
        Set<Integer> findRestIdx = new HashSet<>(); // getRestListRes에 있는 restIdx 목록
        for (GetRestListRes restaurants : getRestListRes) {
            findRestIdx.add(restaurants.getRestIdx());
        }
        List<Integer> restIdxList = new ArrayList<>(findRestIdx); // Set -> List

        Map<Integer, Integer> filteredRestaurants = new HashMap<>(); // restIdx - COUNT(Order.restIdx)
        for (Integer restIdx : restIdxList) {
            filteredRestaurants.put(restIdx, restaurantDao.countRestIdx(restIdx)); // 음식점별 주문 횟수
        }

        // sort filteredRestaurants
        List<Map.Entry<Integer, Integer>> filteredRestaurants_sorted = new LinkedList<>(filteredRestaurants.entrySet());
        filteredRestaurants_sorted.sort((o1, o2) -> (int) (o2.getValue()) - o1.getValue()); // 내림차순 정렬 (COUNT(Order.restIdx) 기준)

        // sort getRestListRes
        List<GetRestListRes> getRestListRes_sorted = new ArrayList<>();
        for (Map.Entry<Integer, Integer> sortOrder : filteredRestaurants_sorted) {
            for (GetRestListRes getRestList : getRestListRes) {
                if (sortOrder.getKey() == getRestList.getRestIdx()) {
                    getRestListRes_sorted.add(getRestList);
                }
            }
        }

        return getRestListRes_sorted;
    }

    // 매장 정렬 : 가까운 순
    public void sortByDistance(List<GetRestListRes> getRestListRes) throws BaseException {
        try {
            Collections.sort(getRestListRes); // distance 기준 내림차순 정렬

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

    // 메뉴 리스트 조회
    public List<GetMenuRes> getMenuList(int restIdx) throws BaseException {
        try {
            return (restaurantDao.getMenuList(restIdx));

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 메뉴 조회
    public GetMenuRes getMenu(int menuIdx) throws BaseException {
        try {
            return restaurantDao.getMenu(menuIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 옵션 & 세부 옵션 리스트 조회
    public List<GetOptionResponse> getOptionResponseList(int restIdx, int menuIdx) throws BaseException {
        try {
            List<GetOptionResponse> getOptionResponseList = new ArrayList<>();

            // 해당 메뉴에만 뜨는 옵션인가?
            if (restaurantDao.hasMenuIdx(menuIdx) == 1) {
                List<Integer> menuOptionIdxList = restaurantDao.getMenuOptionIdx(menuIdx); // 해당 메뉴에 해당하는 옵션 인덱스들 (List)
                for (Integer optionIdx : menuOptionIdxList) {
                    if (restaurantDao.hasOptionChild(optionIdx) == 1) { // OptionChild 존재 O
                        getOptionResponseList.add(new GetOptionResponse(restaurantDao.getOption(optionIdx), restaurantDao.getOptionChild(optionIdx)));
                    } else { // OptionChild 존재 X
                        getOptionResponseList.add(new GetOptionResponse(restaurantDao.getOption(optionIdx)));
                    }
                }
            }

            // 모든 메뉴에 공통적으로 뜨는 옵션인가?
            if (restaurantDao.hasRestIdx(restIdx) == 1) {
                List<Integer> restOptionIdxList = restaurantDao.getRestOptionIdx(restIdx); // 해당 음식점에 해당하는 옵션 인덱스들 (List)
                for (Integer optionIdx : restOptionIdxList) {
                    if (restaurantDao.hasOptionChild(optionIdx) == 1) { // OptionChild 존재 O
                        getOptionResponseList.add(new GetOptionResponse(restaurantDao.getOption(optionIdx), restaurantDao.getOptionChild(optionIdx)));
                    } else { // OptionChild 존재 X
                        getOptionResponseList.add(new GetOptionResponse(restaurantDao.getOption(optionIdx)));
                    }
                }
            }

            // 메뉴에 옵션이 없는 경우 -> 클라이언트에서 메뉴 가격, 수량 선택만 화면에 띄워주기
            if (getOptionResponseList.isEmpty()) {
                throw new NullPointerException();
            }

            return getOptionResponseList;

        } catch (NullPointerException exception) {
            throw new BaseException(NO_OPTION); // 해당 메뉴에 옵션 없음
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

}
