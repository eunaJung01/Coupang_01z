package com.umc.coupang_01z.src.user;

import com.umc.coupang_01z.config.BaseException;
import com.umc.coupang_01z.config.secret.Secret;
import com.umc.coupang_01z.src.user.model.*;
import com.umc.coupang_01z.utils.AES128;
import com.umc.coupang_01z.utils.JwtService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.umc.coupang_01z.config.BaseResponseStatus.*;

@Service
public class UserService {
    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }

    // [POST] 1. 회원가입
    public PostUserRes createUser(@NotNull PostUserReq postUserReq) throws BaseException {
        String pw;
        try {
            // 암호화
            pw = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPw()); // 암호화 코드
            postUserReq.setPw(pw);
        } catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        try {
            int userIdx = userDao.createUser(postUserReq);
            // jwt 발급
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(userIdx, jwt);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
