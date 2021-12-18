package com.umc.coupang_01z.src.user;

import com.umc.coupang_01z.config.BaseException;
import com.umc.coupang_01z.config.BaseResponse;
import com.umc.coupang_01z.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // [POST] 회원가입
    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into User (email, pw, name, phoneNum) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[] {
                postUserReq.getEmail(), postUserReq.getPw(), postUserReq.getName(), postUserReq.getPhoneNum()
        };

        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // Validation : 이미 가입된 이메일인지 확인
    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery, int.class, checkEmailParams);
    }

    // [GET] 회원 조회
    public GetUserRes getUser(int userIdx) {
        String getUserQuery = "select name, phoneNum from User where userIdx = ?"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getString("name"),
                        rs.getString("phoneNum")
                ), userIdx);
    }

    public User getPw(PostLoginReq postLoginReq) {
        String getPwdQuery = "select * from User where email = ?"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        String getPwdParams = postLoginReq.getEmail(); // 주입될 email값을 클라이언트의 요청에서 주어진 정보를 통해 가져온다.

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("pw"),
                        rs.getString("name"),
                        rs.getString("phoneNum")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getPwdParams
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }
}
