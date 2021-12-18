package com.umc.coupang_01z.src.Restaurant;

import com.umc.coupang_01z.src.Restaurant.model.GetRestRes;
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
    public List<GetRestRes> getCategory() {
        String query = "SELECT * FROM Category";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetRestRes(
                        rs.getInt("categoryIdx"),
                        rs.getString("categoryName"),
                        rs.getString("categoryImg"),
                        rs.getString("status"))
        );
    }

}
