package com.umc.coupang_01z.src.restaurant;

import com.umc.coupang_01z.src.restaurant.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.umc.coupang_01z.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.umc.coupang_01z.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/coupang-eats")
public class RestaurantController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final RestaurantProvider restaurantProvider;
    @Autowired
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantProvider restaurantProvider, RestaurantService restaurantService) {
        this.restaurantProvider = restaurantProvider;
        this.restaurantService = restaurantService;
    }

    /*
     * 카테고리 조회 : [GET] /category
     */
    @ResponseBody
    @GetMapping("/category")
    public BaseResponse<List<GetCategoryRes>> getCategory() throws BaseException {
        try {
            List<GetCategoryRes> getCategoryRes = restaurantProvider.getCategory();
            return new BaseResponse<>(getCategoryRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
     * 음식점 리스트 조회 : [GET] /restaurant
     * sorting (매장 정렬)
     *      - 1. 추천순 : 기준이 뭘까??
     *      - 2. 주문 많은 순 : COUNT(Order.restIdx)
     *      - 3. 가까운 순 : Restaurant.latitude, Restaurant.longitude 거리 계산, userIdx 필요
     *      - 4. 별점 높은 순 : Restaurant.rate
     *      - 5. 신규 매장 순 : Restaurant.createAt
     * filtering
     *      - 1. 카테고리 구분 categoryIdx
     *      - 2. 치타 배달 isCheetah = true
     *      - 3. 배달비 deliveryFee
     *      - 4. 최소 주문 minOrderFee
     *      - 5. 포장 packaging = true
     */
    @ResponseBody
    @GetMapping("/restaurant")
    public BaseResponse<List<GetRestListRes>> getRestaurants(@RequestParam(required = false) String sortIdx, @RequestParam(required = false) String userIdx, @RequestParam(required = false) String categoryIdx, @RequestParam(required = false) String isCheetah, @RequestParam(required = false) String deliveryFee, @RequestParam(required = false) String minOrderFee, @RequestParam(required = false) String packaging) throws BaseException {
        try {
            // TODO: 형식적 validation - null 확인
            if (userIdx == null) {
                throw new BaseException(NO_USERIDX); // userIdx를 입력해주세요.
            }
            int userIdx_int = Integer.parseInt(userIdx);

            if (sortIdx == null) {
                sortIdx = "1"; // sorting default : 추천순
            }
            int sortIdx_int = Integer.parseInt(sortIdx);

            // filtering
            String[] filtering = new String[]{categoryIdx, isCheetah, deliveryFee, minOrderFee, packaging};
            List<GetRestListRes> getRestListRes = restaurantProvider.filterRestaurants(filtering, sortIdx_int, userIdx_int);

            // sorting
            switch (sortIdx_int) {
                case (1): // 추천순
                    getRestListRes = restaurantProvider.sortByRecommend(getRestListRes);
                    break;
                case (2): // 주문 많은 순
                    getRestListRes = restaurantProvider.sortByOrderNum(getRestListRes);
                    break;
                case (3): // 가까운 순
                    restaurantProvider.sortByDistance(getRestListRes);
                    break;
            }

            return new BaseResponse<>(getRestListRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
     * 음식점 조회 : [GET] /restaurant/:restIdx
     */
    @ResponseBody
    @GetMapping("/restaurant/{restIdx}")
    public BaseResponse<GetRestRes> getRestaurant(@PathVariable("restIdx") int restIdx) {
        try {
            return new BaseResponse<>(restaurantProvider.getRestaurant(restIdx));

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
     * 메뉴 리스트 조회 : [GET] /menu/:restIdx
     */
    @ResponseBody
    @GetMapping("/menu/{restIdx}")
    public BaseResponse<List<GetMenuRes>> getMenu(@PathVariable("restIdx") int restIdx) {
        try {
            return new BaseResponse<>(restaurantProvider.getMenuList(restIdx));

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
     * 옵션 & 세부 옵션 조회 : [GET] /menu/:restIdx/:menuIdx
     *
     * in Option table
     *      1. restIdx != null : 모든 메뉴에 공통적으로 뜨는 옵션
     *         menuIdx != null : 해당 메뉴에만 뜨는 옵션
     *      2. hasChild == 1 : OptionChild 존재 O
     *                  == 0 : OptionChild 존재 X
     * 해당 메뉴에 옵션이 없는 경우 : 클라이언트에서 메뉴 사진, 가격, 수량 선택만 화면에 띄워주기
     *
     * < Response >
     *  GetOptionListResponse
     *      1. GetMenuRes : 메뉴 정보 (menu)
     *      2. List<GetOptionResponse> : 옵션 리스트 (optionList)
     *              (1) GetOptionRes : 옵션 정보 (option)
     *              (2) List<GetOptionChildRes> : 세부 옵션 리스트 (optionChildList)
     */
    @ResponseBody
    @GetMapping("/menu/{restIdx}/{menuIdx}")
    public BaseResponse<GetOptionListResponse> getOption(@PathVariable("restIdx") int restIdx, @PathVariable("menuIdx") int menuIdx) {
        try {
            GetOptionListResponse getOptionListResponse = new GetOptionListResponse();

            GetMenuRes getMenuRes = restaurantProvider.getMenu(menuIdx); // 메뉴 정보
            getOptionListResponse.setMenu(getMenuRes);

            List<GetOptionResponse> getOptionResponseList = restaurantProvider.getOptionResponseList(restIdx, menuIdx); // 옵션 정보 & 세부 옵션 리스트
            getOptionListResponse.setOptionList(getOptionResponseList);

            return new BaseResponse<>(getOptionListResponse);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
