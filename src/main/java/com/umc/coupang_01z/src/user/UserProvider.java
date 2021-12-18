package com.umc.coupang_01z.src.user;

import com.umc.coupang_01z.config.secret.Secret;
import com.umc.coupang_01z.src.user.model.*;
import com.umc.coupang_01z.config.BaseException;
import com.umc.coupang_01z.config.BaseResponse;
import com.umc.coupang_01z.src.user.model.GetUserRes;
import com.umc.coupang_01z.utils.AES128;
import com.umc.coupang_01z.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.umc.coupang_01z.config.BaseResponseStatus.*;

@Service
public class UserProvider {
    private final UserDao userDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    // Validation : 해당 이메일이 이미 존재하는 이메일인지 확인
    public int checkEmail(String email) throws BaseException {
        try {
            return userDao.checkEmail(email);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // [GET] 회원 조회
    public GetUserRes getUser(int userIdx) throws BaseException {
        try {
            GetUserRes getUserRes = userDao.getUser(userIdx);
            return getUserRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // [POST] 로그인 (password 검사)
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
        User user = userDao.getPw(postLoginReq);
        String password;

        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPw()); // 복호화.
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        // 암호화된 코드를 복호화하여 입력된 비밀번호와 비교한다.
        if (postLoginReq.getPw().equals(password)) { // 비밀번호가 일치한다면 userIdx를 가져온다.
            int userIdx = userDao.getPw(postLoginReq).getUserIdx();

            // jwt 생성.
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(userIdx, jwt);  // access token 만들어줌.
        } else { // 비밀번호가 다르다면 에러메세지를 출력한다.
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }
}
