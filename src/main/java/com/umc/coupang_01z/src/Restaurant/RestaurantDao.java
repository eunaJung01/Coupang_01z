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

    // 음식점 조회
    public List<GetRestRes> getRest() {
        String query = "SELECT * FROM Restaurant";
        return this.jdbcTemplate.query(query,
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
                        rs.getDouble("restLongitude")
                )
        );
    }

    // 음식점 조회, 카테고리 구분
    public List<GetRestRes> getRest(int categoryIdx) {
        String query = "SELECT * FROM Restaurant WHERE categoryIdx = ?";
        return this.jdbcTemplate.query(query,
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
                categoryIdx
        );
    }

    // 음식점 조회 - 별점 높은 순
    public List<GetRestRes> getRestByRate() {
        String query = "SELECT * FROM Restaurant ORDER BY rate DESC";
        return this.jdbcTemplate.query(query,
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
                        rs.getDouble("restLongitude")
                )
        );
    }

    // 음식점 조회 - 별점 높은 순, 카테고리 구분
    public List<GetRestRes> getRestByRate(int categoryIdx) {
        String query = "SELECT * FROM Restaurant WHERE categoryIdx = ? ORDER BY rate DESC";
        return this.jdbcTemplate.query(query,
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
                categoryIdx
        );
    }

    // 음식점 조회 - 치타 배달
    public List<GetRestRes> getRestByCheetah() {
        String query = "SELECT * FROM Restaurant WHERE isCheetah = 1";
        return this.jdbcTemplate.query(query,
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
                        rs.getDouble("restLongitude")
                )
        );
    }

    // 음식점 조회 - 치타 배달, 카테고리 구분
    public List<GetRestRes> getRestByCheetah(int categoryIdx) {
        String query = "SELECT * FROM Restaurant WHERE categoryIdx = ? AND isCheetah = 1";
        return this.jdbcTemplate.query(query,
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
                categoryIdx
        );
    }

    // 음식점 조회 - 배달비
    public List<GetRestRes> getRestByDeliveryFee(int deliveryFee) {
        String query;
        if (deliveryFee == 0) {
            query = "SELECT * FROM Restaurant WHERE deliveryFee is NULL";
        } else {
            query = "SELECT * FROM Restaurant WHERE deliveryFee <= ?";
        }

        return this.jdbcTemplate.query(query,
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
                deliveryFee
        );
    }

    // 음식점 조회 - 배달비, 카테고리 구분
    public List<GetRestRes> getRestByDeliveryFee(int categoryIdx, int deliveryFee) {
        String query;
        if (deliveryFee == 0) {
            query = "SELECT * FROM Restaurant WHERE categoryIdx = ? AND deliveryFee is NULL";
        } else {
            query = "SELECT * FROM Restaurant WHERE categoryIdx = ? AND deliveryFee <= ?";
        }

        return this.jdbcTemplate.query(query,
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
                categoryIdx, deliveryFee
        );
    }

    // 음식점 조회 - 최소 주문비
    public List<GetRestRes> getRestByMinOrderFee(int minOrderFee) {
        String query = "SELECT * FROM Restaurant WHERE minOrderFee <= ?";

        return this.jdbcTemplate.query(query,
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
                minOrderFee
        );
    }

    // 음식점 조회 - 최소 주문비
    public List<GetRestRes> getRestByMinOrderFee(int categoryIdx, int minOrderFee) {
        String query = "SELECT * FROM Restaurant WHERE categoryIdx = ? AND minOrderFee <= ?";

        return this.jdbcTemplate.query(query,
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
                categoryIdx, minOrderFee
        );
    }

}
