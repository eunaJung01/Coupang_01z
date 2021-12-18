package com.umc.coupang_01z.src.favorite;

import com.umc.coupang_01z.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteProvider {
    private final FavoriteDao favoriteDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FavoriteProvider(FavoriteDao favoriteDao, JwtService jwtService) {
        this.favoriteDao = favoriteDao;
        this.jwtService = jwtService;
    }
}
