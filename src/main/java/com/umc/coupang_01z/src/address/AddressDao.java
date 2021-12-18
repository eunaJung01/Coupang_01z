package com.umc.coupang_01z.src.address;

import com.umc.coupang_01z.src.address.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AddressDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // [GET] 주소 조회
    public GetAddressRes getAddress(int userIdx) {
        String getAddressQuery = "select * from Address where userIdx = ?";
        return this.jdbcTemplate.queryForObject(getAddressQuery,
                (rs, rowNum) -> new GetAddressRes(
                        rs.getInt("addressIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("address"),
                        rs.getInt("division"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getString("status")
                ), userIdx);
    }

    // [POST] 유저의 주소 추가
    public int createAddress(PostAddressReq postAddressReq) {
        String createAddressQuery = "INSERT INTO Address (userIdx, address, division, latitude, longitude) VALUES (?,?,?,?,?)";
        Object[] createAddressParams = new Object[] {
                postAddressReq.getUserIdx(),
                postAddressReq.getAddress(),
                postAddressReq.getDivision(),
                postAddressReq.getLatitude(),
                postAddressReq.getLongitude()
        };
        this.jdbcTemplate.update(createAddressQuery, createAddressParams);
        return this.jdbcTemplate.queryForObject("SELECT last_insert_id()", int.class);
    }
}
