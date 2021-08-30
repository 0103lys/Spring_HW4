package com.koreait.day4.model.network.response;

import com.koreait.day4.model.enumclass.ItemStatus;
import com.koreait.day4.model.enumclass.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUserApiResponse {
    private Long id;
    private String userid;
    private String userpw;
    private String name;

    private LocalDateTime regDate;
    private String createBy;
    private String status;



}
