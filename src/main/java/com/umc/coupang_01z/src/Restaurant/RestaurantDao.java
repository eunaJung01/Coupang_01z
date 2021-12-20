package com.umc.coupang_01z.src.Restaurant;

import com.umc.coupang_01z.src.Restaurant.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class RestaurantDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 카테고리 조회
    public List<GetCategoryRes> getCategory() {
        String query = "SELECT * FROM Category WHERE status = 'A'";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetCategoryRes(
                        rs.getInt("categoryIdx"),
                        rs.getString("categoryName"),
                        rs.getString("categoryImg"))
        );
    }

    // 음식점 리스트 조회
    public List<GetRestListRes> getRest() {
        String query = "SELECT * FROM Restaurant";

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetRestListRes(
                        rs.getInt("restIdx"),
                        rs.getString("restName"),
                        rs.getString("restImg"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("isCheetah"),
                        rs.getDouble("rate"),
                        rs.getInt("deliveryFee"))
        );
    }

    // 음식점 리스트 조회, 카테고리 구분
    public List<GetRestListRes> getRest(int categoryIdx) {
        String query = "SELECT * FROM Restaurant WHERE categoryIdx = ?";

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetRestListRes(
                        rs.getInt("restIdx"),
                        rs.getString("restName"),
                        rs.getString("restImg"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("isCheetah"),
                        rs.getDouble("rate"),
                        rs.getInt("deliveryFee")),
                categoryIdx
        );
    }

    // 음식점 리스트 조회 - 별점 높은 순
    public List<GetRestListRes> getRestByRate() {
        String query = "SELECT * FROM Restaurant ORDER BY rate DESC";

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetRestListRes(
                        rs.getInt("restIdx"),
                        rs.getString("restName"),
                        rs.getString("restImg"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("isCheetah"),
                        rs.getDouble("rate"),
                        rs.getInt("deliveryFee"))
        );
    }

    // 음식점 리스트 조회 - 별점 높은 순, 카테고리 구분
    public List<GetRestListRes> getRestByRate(int categoryIdx) {
        String query = "SELECT * FROM Restaurant WHERE categoryIdx = ? ORDER BY rate DESC";

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetRestListRes(
                        rs.getInt("restIdx"),
                        rs.getString("restName"),
                        rs.getString("restImg"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("isCheetah"),
                        rs.getDouble("rate"),
                        rs.getInt("deliveryFee")),
                categoryIdx
        );
    }

    // 음식점 리스트 조회 - 치타 배달
    public List<GetRestListRes> getRestByCheetah() {
        String query = "SELECT * FROM Restaurant WHERE isCheetah = 1";

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetRestListRes(
                        rs.getInt("restIdx"),
                        rs.getString("restName"),
                        rs.getString("restImg"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("isCheetah"),
                        rs.getDouble("rate"),
                        rs.getInt("deliveryFee"))
        );
    }

    // 음식점 리스트 조회 - 치타 배달, 카테고리 구분
    public List<GetRestListRes> getRestByCheetah(int categoryIdx) {
        String query = "SELECT * FROM Restaurant WHERE categoryIdx = ? AND isCheetah = 1";

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetRestListRes(
                        rs.getInt("restIdx"),
                        rs.getString("restName"),
                        rs.getString("restImg"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("isCheetah"),
                        rs.getDouble("rate"),
                        rs.getInt("deliveryFee")),
                categoryIdx
        );
    }

    // 음식점 리스트 조회 - 배달비
    public List<GetRestListRes> getRestByDeliveryFee(int deliveryFee) {
        String query;
        if (deliveryFee == 0) {
            query = "SELECT * FROM Restaurant WHERE deliveryFee is NULL";
        } else {
            query = "SELECT * FROM Restaurant WHERE deliveryFee <= ?";
        }

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetRestListRes(
                        rs.getInt("restIdx"),
                        rs.getString("restName"),
                        rs.getString("restImg"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("isCheetah"),
                        rs.getDouble("rate"),
                        rs.getInt("deliveryFee")),
                deliveryFee
        );
    }

    // 음식점 리스트 조회 - 배달비, 카테고리 구분
    public List<GetRestListRes> getRestByDeliveryFee(int categoryIdx, int deliveryFee) {
        String query;
        if (deliveryFee == 0) {
            query = "SELECT * FROM Restaurant WHERE categoryIdx = ? AND deliveryFee is NULL";
        } else {
            query = "SELECT * FROM Restaurant WHERE categoryIdx = ? AND deliveryFee <= ?";
        }

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetRestListRes(
                        rs.getInt("restIdx"),
                        rs.getString("restName"),
                        rs.getString("restImg"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("isCheetah"),
                        rs.getDouble("rate"),
                        rs.getInt("deliveryFee")),
                categoryIdx, deliveryFee
        );
    }

    // 음식점 리스트 조회 - 최소 주문비
    public List<GetRestListRes> getRestByMinOrderFee(int minOrderFee) {
        String query = "SELECT * FROM Restaurant WHERE minOrderFee <= ?";

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetRestListRes(
                        rs.getInt("restIdx"),
                        rs.getString("restName"),
                        rs.getString("restImg"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("isCheetah"),
                        rs.getDouble("rate"),
                        rs.getInt("deliveryFee")),
                minOrderFee
        );
    }

    // 음식점 리스트 조회 - 최소 주문비
    public List<GetRestListRes> getRestByMinOrderFee(int categoryIdx, int minOrderFee) {
        String query = "SELECT * FROM Restaurant WHERE categoryIdx = ? AND minOrderFee <= ?";

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetRestListRes(
                        rs.getInt("restIdx"),
                        rs.getString("restName"),
                        rs.getString("restImg"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("isCheetah"),
                        rs.getDouble("rate"),
                        rs.getInt("deliveryFee")),
                categoryIdx, minOrderFee
        );
    }

    // 음식점 조회
    public GetRestRes getRestaurant(int restIdx) {
        String query = "SELECT * FROM Restaurant WHERE restIdx = ?";

        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new GetRestRes(
                        rs.getInt("restIdx"),
                        rs.getString("restName"),
                        rs.getString("restImg"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("isCheetah"),
                        rs.getDouble("rate"),
                        rs.getInt("deliveryFee"),
                        rs.getInt("minOrderFee"),
                        rs.getString("restAddress"),
                        rs.getDouble("restLatitude"),
                        rs.getDouble("restLongitude")),
                restIdx
        );
    }

    // 메뉴 전체 조회
    public List<GetMenuRes> getMenuList(int restIdx) {
        String query = "SELECT * FROM Menu WHERE restIdx = ?";

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetMenuRes(
                        rs.getInt("menuIdx"),
                        rs.getInt("restIdx"),
                        rs.getString("menuDivision"),
                        rs.getString("menuName"),
                        rs.getString("description"),
                        rs.getInt("menuPrice"),
                        rs.getInt("isLotsOfOrders"),
                        rs.getInt("isBestReview"),
                        rs.getString("status")),
                restIdx
        );
    }

    // 특정 메뉴 조회
    public GetMenuRes getMenu(int menuIdx) {
        String query = "SELECT * FROM Menu WHERE menuIdx = ?";

        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new GetMenuRes(
                        rs.getInt("menuIdx"),
                        rs.getInt("restIdx"),
                        rs.getString("menuDivision"),
                        rs.getString("menuName"),
                        rs.getString("description"),
                        rs.getInt("menuPrice"),
                        rs.getInt("isLotsOfOrders"),
                        rs.getInt("isBestReview"),
                        rs.getString("status")),
                menuIdx
        );
    }



}
