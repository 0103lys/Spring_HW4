package com.koreait.day4.controller.api;

import com.koreait.day4.controller.CrudController;
import com.koreait.day4.model.entity.AdminUser;
import com.koreait.day4.model.network.Header;
import com.koreait.day4.model.network.request.AdminUserApiRequest;
import com.koreait.day4.model.network.response.AdminUserApiResponse;
import com.koreait.day4.service.AdminUserApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adminuser")
@RequiredArgsConstructor
public class AdminUserApiController extends CrudController<AdminUserApiRequest, AdminUserApiResponse, AdminUser> {
    private final AdminUserApiLogicService adminUserApiLogicService;

    @Override
    @PostMapping("")    // /api/item    // http://127.0.0.1:9900/api/item
    public Header<AdminUserApiResponse> create(@RequestBody Header<AdminUserApiRequest> request) {
        System.out.println(request);
        return adminUserApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}") // /api/item/{id}    // http://127.0.0.1:9900/api/item/6
    public Header<AdminUserApiResponse> read(@PathVariable(name = "id") Long id) {
        return adminUserApiLogicService.read(id);
    }

    @Override
    @PutMapping("")     // /api/item    // http://127.0.0.1:9900/api/item
    public Header<AdminUserApiResponse> update(@RequestBody Header<AdminUserApiRequest> request) {
        return adminUserApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")  // /api/item/{id}    // http://127.0.0.1:9900/api/item/6
    public Header<AdminUserApiResponse> delete(@PathVariable(name = "id") Long id) {
        return adminUserApiLogicService.delete(id);
    }



    @GetMapping("") //api/adminuser
    public Header<List<AdminUserApiResponse>> findAll(@PageableDefault(sort={"id"}, direction= Sort.Direction.DESC) Pageable pageable){
        return adminUserApiLogicService.search(pageable);
    }
}
