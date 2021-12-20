// for 메르

package com.umc.coupang_01z.src.Restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetRestListResponse {
    private List<GetRestListRes> restaurantList;

    public GetRestListResponse() {
    }
}
