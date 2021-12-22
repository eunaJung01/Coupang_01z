package com.umc.coupang_01z.src.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOptionResponse {
    private GetOptionRes option;
    private List<GetOptionChildRes> optionChildList;

    public GetOptionResponse(GetOptionRes option) {
        this.option = option;
    }

}
