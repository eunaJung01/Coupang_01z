package com.umc.coupang_01z.src.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCategoryRes {
    private int categoryIdx;
    private String categoryName;
    private String categoryImg;
}
