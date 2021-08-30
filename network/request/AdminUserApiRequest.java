package com.koreait.day4.model.network.request;

import com.koreait.day4.model.enumclass.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUserApiRequest {
    private Long id;
    private String userid;
    private String userpw;
    private String name;

    private LocalDateTime regDate;
    private String createBy;
    private String status;

}
