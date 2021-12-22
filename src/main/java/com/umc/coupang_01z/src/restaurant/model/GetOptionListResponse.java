package com.umc.coupang_01z.src.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOptionListResponse {
    private GetMenuRes menu;
    private List<GetOptionResponse> optionList;

    public GetOptionListResponse() {
    }

}
