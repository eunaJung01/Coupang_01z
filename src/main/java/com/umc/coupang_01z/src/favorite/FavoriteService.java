package com.umc.coupang_01z.src.favorite;

import com.umc.coupang_01z.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {
    private final FavoriteDao favoriteDao;
    private final FavoriteProvider favoriteProvider;
    private final JwtService jwtService;

    @Autowired
    public FavoriteService(FavoriteDao favoriteDao, FavoriteProvider favoriteProvider, JwtService jwtService) {
        this.favoriteDao = favoriteDao;
        this.favoriteProvider = favoriteProvider;
        this.jwtService = jwtService;
    }
}
