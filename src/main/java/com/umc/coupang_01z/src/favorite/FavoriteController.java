package com.umc.coupang_01z.src.favorite;

import com.umc.coupang_01z.src.favorite.model.*;
import com.umc.coupang_01z.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupang-eats/favorites")
public class FavoriteController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final FavoriteProvider favoriteProvider;
    @Autowired
    private final FavoriteService favoriteService;
    @Autowired
    private final JwtService jwtService;

    public FavoriteController(FavoriteProvider favoriteProvider, FavoriteService favoriteService, JwtService jwtService) {
        this.favoriteProvider = favoriteProvider;
        this.favoriteService = favoriteService;
        this.jwtService = jwtService;
    }
}
