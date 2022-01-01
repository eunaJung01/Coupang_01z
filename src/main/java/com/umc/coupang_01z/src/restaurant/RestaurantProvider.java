package com.umc.coupang_01z.src.restaurant;

import com.umc.coupang_01z.src.restaurant.model.*;
import com.umc.coupang_01z.config.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.umc.coupang_01z.config.BaseResponseStatus.*;

@Service
public class RestaurantProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestaurantDao restaurantDao;

    @Autowired
    public RestaurantProvider(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
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
    public List<GetRestListRes> filterRestaurants(String[] filtering, int sortIdx, int userIdx) throws EmptyResultDataAccessException, BaseException {
        try {
            List<GetRestListRes> filteredRestaurants = restaurantDao.getRestByFiltering(filtering, sortIdx);
            if (filteredRestaurants.isEmpty()) {
                throw new NullPointerException();
            }

            // set distance field
            List<Location> location = new ArrayList<>();
            Location userLocation = restaurantDao.getLocation(userIdx); // location.get(0) : userIdx, latitude, longitude (사용자 위치정보 - status = 'C')
            if (userLocation != null) { // Address.status = 'C'가 없을 경우 (주소 설정 X)
                location.add(userLocation);
            } else {
                throw new EmptyResultDataAccessException(0);
            }

            for (GetRestListRes restaurant : filteredRestaurants) {
                location.add(restaurantDao.getRestLocation(restaurant.getRestIdx())); // restIdx, restLatitude, restLongitude (음식점 위치정보)
            }
            getDistances(location, filteredRestaurants); // 사용자 위치와 음식점 간의 거리 계산

            // set rateNum field
            for (GetRestListRes filteredRestaurant : filteredRestaurants) {
                filteredRestaurant.setRateNum(restaurantDao.getRateNum(filteredRestaurant.getRestIdx())); // 리뷰 개수 반환
            }

            return filteredRestaurants;

        } catch (NullPointerException exception) {
            throw new BaseException(NO_RESULT); // 검색 결과 없음
        } catch (EmptyResultDataAccessException exception) {
            throw new BaseException(NO_ADDRESS_STATUS_C); // 주소를 설정해주세요.
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

    // 매장 정렬 : 추천순
    /*
     * 주문 많은 순 순위 : a
     * 가까운 순 순위 : b
     * 별점 높은 순 순위 : c
     * 배달비 적은 순 순위 : d
     * 치타 배달 = 0일 때 n (매장 개수)
     * 포장 = 0일 때 n (매장 개수)
     * => a^2+b^2+c^2+d^2+n+n 오름차순 순위로 조회
     */
    public List<GetRestListRes> sortByRecommend(List<GetRestListRes> getRestListRes) throws BaseException {
        try {
            int restNum = getRestListRes.size();
            int[][] rateArr = new int[restNum][8];
            // rowIdx = restIdx-1
            // columnIdx = [0] : restIdx, [1] : 주문 개수 순위, [2] : 거리 순위, [3] : 별점 순위, [4] : 배달비 순위, [5] : 치타 배달?, [6] : 포장 가능?, [7] : 총점

            for (int i = 0; i < rateArr.length; i++) {
                rateArr[i][0] = i + 1; // restIdx 저장 - rateArr[restIdx-1][0]
            }

            // 주문 많은 순 조회
            List<Map.Entry<Integer, Integer>> orderNum_sorted = sortRestIdxList(getRestListRes); // restIdx - COUNT(Order.restIdx)
            for (int i = 0; i < orderNum_sorted.size(); i++) {
                rateArr[orderNum_sorted.get(i).getKey() - 1][1] = i + 1; // 순위 저장 - rateArr[restIdx-1][1]
            }

            // 가까운 순 조회
            Collections.sort(getRestListRes); // distance 기준 오름차순 정렬
            for (int i = 0; i < getRestListRes.size(); i++) {
                rateArr[getRestListRes.get(i).getRestIdx() - 1][2] = i + 1; // 순위 저장 - rateArr[restIdx-1][2]
            }

            // 별점 높은 순 조회
            class RateComparator implements Comparator<GetRestListRes> {
                @Override
                public int compare(GetRestListRes res1, GetRestListRes res2) { // rate 기준 내림차순 정렬
                    if (res1.getRate() > res2.getRate()) {
                        return -1;
                    } else if (res1.getRate() < res2.getRate()) {
                        return 1;
                    }
                    return 0;
                }
            }
            RateComparator rateComparator = new RateComparator();
            getRestListRes.sort(rateComparator);
            for (int i = 0; i < getRestListRes.size(); i++) {
                rateArr[getRestListRes.get(i).getRestIdx() - 1][3] = i + 1; // 순위 저장 - rateArr[restIdx-1][3]
            }

            // 배달비 적은 순 조회
            class DeliveryFeeComparator implements Comparator<GetRestListRes> {
                @Override
                public int compare(GetRestListRes res1, GetRestListRes res2) { // deliveryFee 기준 오름차순 정렬
                    if (res1.getDeliveryFee() > res2.getDeliveryFee()) {
                        return 1;
                    } else if (res1.getDeliveryFee() < res2.getDeliveryFee()) {
                        return -1;
                    }
                    return 0;
                }
            }
            DeliveryFeeComparator deliveryFeeComparator = new DeliveryFeeComparator();
            getRestListRes.sort(deliveryFeeComparator);
            for (int i = 0; i < getRestListRes.size(); i++) {
                rateArr[getRestListRes.get(i).getRestIdx() - 1][4] = i + 1; // 순위 저장 - rateArr[restIdx-1][4]
            }

            // 치타 배달 유무 확인
            for (int i = 0; i < rateArr.length; i++) {
                if (restaurantDao.getCheetah(i + 1) == 0) { // 치타 배달 X
                    rateArr[i][5] = restNum;
                }
            }

            // 포장 유무 확인
            for (int i = 0; i < rateArr.length; i++) {
                if (restaurantDao.getPackaging(i + 1) == 0) { // 포장 X
                    rateArr[i][6] = restNum;
                }
            }

            // 총점 계산
            for (int i = 0; i < rateArr.length; i++) {
                rateArr[i][7] = (int) (Math.pow(rateArr[i][1], 2) + Math.pow(rateArr[i][2], 2) + Math.pow(rateArr[i][3], 2) + Math.pow(rateArr[i][4], 2) + rateArr[i][5] + rateArr[i][6]);
            }
            Arrays.sort(rateArr, (o1, o2) -> { // rateArr[i][7] 기준 오름차순 정렬
                if (o1[7] > o2[7]) {
                    return 1;
                } else if (o1[7] < o2[7]) {
                    return -1;
                }
                return 0;
            });

            // sort getRestListRes
            List<GetRestListRes> getRestListRes_sorted = new ArrayList<>();
            for (int[] arr : rateArr) {
                for (GetRestListRes getRestList : getRestListRes) {
                    if (arr[0] == getRestList.getRestIdx()) {
                        getRestListRes_sorted.add(getRestList);
                    }
                }
            }

//            for (int[] arr : rateArr) {
//                for (int j = 0; j < rateArr[0].length; j++) {
//                    System.out.print(arr[j] + " ");
//                }
//                System.out.println();
//            }

            return getRestListRes_sorted;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 매장 정렬 : 주문 많은 순
    public List<GetRestListRes> sortByOrderNum(List<GetRestListRes> getRestListRes) throws BaseException {
        try {
            // get restIdxList and sort filteredRestaurants
            List<Map.Entry<Integer, Integer>> filteredRestaurants_sorted = sortRestIdxList(getRestListRes);

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

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 음식점 목록 가져온 후 주문 많은 순으로 내림차순 정렬
    public List<Map.Entry<Integer, Integer>> sortRestIdxList(List<GetRestListRes> getRestListRes) throws
            BaseException {
        try {
            // get restIdxList
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

            return filteredRestaurants_sorted;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR); // 데이터베이스 연결에 실패하였습니다.
        }
    }

    // 매장 정렬 : 가까운 순
    public void sortByDistance(List<GetRestListRes> getRestListRes) throws BaseException {
        try {
            Collections.sort(getRestListRes); // distance 기준 오름차순 정렬

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
