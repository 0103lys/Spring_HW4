package com.koreait.day4.controller.api;

import com.koreait.day4.controller.CrudController;

import com.koreait.day4.model.entity.Users;
import com.koreait.day4.model.network.Header;
import com.koreait.day4.model.network.request.UserApiRequest;
import com.koreait.day4.model.network.response.UserApiResponse;
import com.koreait.day4.model.network.response.UserOrderInfoApiResponse;
import com.koreait.day4.service.UserApiLogicService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController extends CrudController<UserApiRequest, UserApiResponse, Users> {

    private final UserApiLogicService userApiLogicService;
    /*
        {
            "transaction_time":"2021-08-24",
            "resultCode":"OK",
            "description":"OK",
            "data":{
                "userid":"cherry",
                "userpw":"1234",
                "hp":"010-3333-3333",
                "email":"cherry@cherry.com"
            }
        }
    */
    @Override
    @PostMapping("")  //  /api/user (post방식)    // http://127.0.0.1:9900/api/user
    public Header<UserApiResponse> create(@RequestBody Header<UserApiRequest> request) {
        System.out.println(request);
        return userApiLogicService.create(request);
    }


    @Override
    @GetMapping("{id}") //  /api/user/{id} (get방식) (get) http://127.0.0.1:9900/api/user/14
    public Header<UserApiResponse> read(@PathVariable(name="id") Long id) {

        return userApiLogicService.read(id);    //  userApiLogicService에 read를 만들어서 id를 전달 -> 리턴
    }


    /*
   {
        "transaction_time":"2021-08-24",
        "resultCode":"OK",
        "description":"OK",
        "data":{
            "id":14,
            "userid":"cherry",
            "userpw":"3333",
            "hp":"010-3333-3333",
            "email":"cherry@naver.com"
        }
    }

*/
    @Override
    @PutMapping("") //  /api/user (put방식)
    public Header<UserApiResponse> update(@RequestBody Header<UserApiRequest> request) {

        return userApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")  //  /api/user/{id} (delete방식) http://127.0.0.1:9900/api/user/14
    public Header<UserApiResponse> delete(@PathVariable(name = "id") Long id) {
        return userApiLogicService.delete(id);
    }

/*
    @GetMapping("/{id}/orderInfo")  //  /api/user/1(아이디번호)/orderInfo
    public Header<UserOrderInfoApiResponse> orderInfo(@PathVariable Long id){
        return userApiLogicService.orderInfo(id);
    }*/

    @GetMapping("") //api/user
    public Header<List<UserApiResponse>> findAll(@PageableDefault(sort={"id"}, direction= Sort.Direction.DESC)Pageable pageable){
        return userApiLogicService.search(pageable);
    }
    // sort={"id"} : index 값 / 기본값 : direction= Sort.Direction.DESC
}
