package com.koreait.day4.controller.api;


import com.koreait.day4.controller.CrudController;
import com.koreait.day4.model.entity.Category;
import com.koreait.day4.model.network.Header;
import com.koreait.day4.model.network.request.AdminUserApiRequest;
import com.koreait.day4.model.network.request.CategoryApiRequest;
import com.koreait.day4.model.network.response.AdminUserApiResponse;
import com.koreait.day4.model.network.response.CategoryApiResponse;
import com.koreait.day4.service.CategoryApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryApiController extends CrudController<CategoryApiRequest, CategoryApiResponse, Category> {
    private final CategoryApiLogicService categoryApiLogicService;

    @Override
    @PostMapping("")    // /api/item    // http://127.0.0.1:9900/api/item
    public Header<CategoryApiResponse> create(@RequestBody Header<CategoryApiRequest> request) {
        System.out.println(request);
        return categoryApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}") // /api/item/{id}    // http://127.0.0.1:9900/api/item/6
    public Header<CategoryApiResponse> read(@PathVariable(name = "id") Long id) {
        return categoryApiLogicService.read(id);
    }

    @Override
    @PutMapping("")     // /api/item    // http://127.0.0.1:9900/api/item
    public Header<CategoryApiResponse> update(@RequestBody Header<CategoryApiRequest> request) {
        return categoryApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")  // /api/item/{id}    // http://127.0.0.1:9900/api/item/6
    public Header<CategoryApiResponse> delete(@PathVariable(name = "id") Long id) {
        return categoryApiLogicService.delete(id);
    }



    @GetMapping("") //api/adminuser
    public Header<List<CategoryApiResponse>> findAll(@PageableDefault(sort={"id"}, direction= Sort.Direction.DESC) Pageable pageable){
        return categoryApiLogicService.search(pageable);
    }
}
