package com.umc.coupang_01z.src.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOptionRes {
    private int restIdx;
    private int menuIdx;
    private int optionIdx;
    private String optionName;
    private int isRequired;
    private int isRadioButton;
    private int hasChild;
    private String status;
}
