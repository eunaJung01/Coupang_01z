package com.umc.coupang_01z.src.Restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMenuRes {
    private int menuIdx;
    private int restIdx;
    private String menuDivision;
    private String menuName;
    private String description;
    private int menuPrice;
    private int isLotsOfOrders;
    private int isBestReview;
    private String status;
}
