package com.umc.coupang_01z.src.restaurant;

import com.umc.coupang_01z.src.restaurant.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public List<GetRestListRes> getRestByFiltering(String[] filtering, int sortIdx) {
        String query = "SELECT * FROM Restaurant WHERE status = 'A'";

        if (filtering[0] != null) { // 카테고리 구분
            query += " AND categoryIdx = " + Integer.parseInt(filtering[0]);
        }
        if (filtering[1] != null) { // 치타 배달
            query += " AND isCheetah = 1";
        }
        if (filtering[2] != null) { // 배달비
            query += " AND deliveryFee <= " + Integer.parseInt(filtering[2]);
        }
        if (filtering[3] != null) { // 최소 주문
            query += " AND minOrderFee <= " + Integer.parseInt(filtering[3]);
        }
        if (filtering[4] != null) { // 포장
            query += " AND packaging = 1";
        }

        if (sortIdx == 4) { // 별점 높은 순
            query += " ORDER BY rate DESC";
        }
        if (sortIdx == 5) { // 신규 매장 순
            query += " ORDER BY createAt DESC";
        }

        query += ";";

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetRestListRes(
                        rs.getInt("restIdx"),
                        rs.getString("restName"),
                        rs.getString("restImg"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("isCheetah"),
                        rs.getDouble("rate"),
                        rs.getInt("deliveryFee"),
                        rs.getInt("packaging"))
        );
    }

    // 사용자 위치정보(latitude, longitude) 반환
    public Location getLocation(int userIdx) throws EmptyResultDataAccessException {
        try {
            String query = "SELECT userIdx AS idx, latitude AS lat, longitude AS lng FROM Address WHERE userIdx = ? AND status = 'C'";
            return this.jdbcTemplate.queryForObject(query,
                    (rs, rowNum) -> new Location(
                            rs.getInt("idx"),
                            rs.getDouble("lat"),
                            rs.getDouble("lng")),
                    userIdx
            );
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    // 음식점 위치정보(restLatitude, restLongitude) 반환
    public Location getRestLocation(int restIdx) {
        String query = "SELECT restIdx AS idx, restLatitude AS lat, restLongitude AS lng FROM Restaurant WHERE restIdx = ?";
        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new Location(
                        rs.getInt("idx"),
                        rs.getDouble("lat"),
                        rs.getDouble("lng")),
                restIdx
        );
    }

    // 리뷰 개수 반환
    public int getRateNum(int restIdx) {
        String query = "SELECT COUNT(restIdx) From Review WHERE restIdx = ?";
        return this.jdbcTemplate.queryForObject(query, int.class, restIdx);
    }

    // 치타 배달 유무 반환
    public int getCheetah(int restIdx) {
        String query = "SELECT isCheetah FROM Restaurant WHERE restIdx = ?";
        return this.jdbcTemplate.queryForObject(query, int.class, restIdx);
    }

    // 포장 유무 반환
    public int getPackaging(int restIdx) {
        String query = "SELECT packaging FROM Restaurant WHERE restIdx = ?";
        return this.jdbcTemplate.queryForObject(query, int.class, restIdx);
    }

    // 음식점별 주문 횟수 반환
    public int countRestIdx(Integer restIdx) {
        String query = "SELECT COUNT(restIdx) FROM Coupang_eats.Order WHERE restIdx = ?";
        return this.jdbcTemplate.queryForObject(query, int.class, restIdx);
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

    // 메뉴 리스트 조회
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

    // is menuIdx != null in Option table?
    public int hasMenuIdx(int menuIdx) {
        String query = "SELECT EXISTS(SELECT menuIdx FROM Coupang_eats.Option WHERE menuIdx = ?)";
        return this.jdbcTemplate.queryForObject(query, int.class, menuIdx);
    }

    // is restIdx != null in Option table?
    public int hasRestIdx(int restIdx) {
        String query = "SELECT EXISTS(SELECT restIdx FROM Coupang_eats.Option WHERE restIdx = ?)";
        return this.jdbcTemplate.queryForObject(query, int.class, restIdx);
    }

    // hasChild 반환
    public int hasOptionChild(int optionIdx) {
        String query = "SELECT hasChild FROM Coupang_eats.Option WHERE optionIdx = ?";
        return this.jdbcTemplate.queryForObject(query, int.class, optionIdx);
    }

    // 메뉴에 해당하는 optionIdx - List로 반환
    public List<Integer> getMenuOptionIdx(int menuIdx) {
        String query = "SELECT optionIdx FROM Coupang_eats.Option WHERE menuIdx = ?";
        return this.jdbcTemplate.queryForList(query, Integer.class, menuIdx);
    }

    public List<Integer> getRestOptionIdx(int restIdx) {
        String query = "SELECT optionIdx FROM Coupang_eats.Option WHERE restIdx = ?";
        return this.jdbcTemplate.queryForList(query, Integer.class, restIdx);
    }

    // 옵션 정보 반환
    public GetOptionRes getOption(int optionIdx) {
        String query = "SELECT * FROM Coupang_eats.Option WHERE optionIdx = ?";

        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new GetOptionRes(
                        rs.getInt("restIdx"),
                        rs.getInt("menuIdx"),
                        rs.getInt("optionIdx"),
                        rs.getString("optionName"),
                        rs.getInt("isRequired"),
                        rs.getInt("isRadioButton"),
                        rs.getInt("hasChild"),
                        rs.getString("status")),
                optionIdx
        );
    }

    // 세부 옵션 리스트 반환
    public List<GetOptionChildRes> getOptionChild(int optionIdx) {
        String query = "SELECT * FROM OptionChild WHERE optionIdx = ?";

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetOptionChildRes(
                        rs.getInt("optCldIdx"),
                        rs.getString("optCldName"),
                        rs.getInt("optCldPrice"),
                        rs.getString("status")),
                optionIdx
        );
    }

}
