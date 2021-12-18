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

//    public List<GetRestRes> getRestByRate() {
//
//    }
}
