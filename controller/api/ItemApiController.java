package com.koreait.day4.controller.api;


import com.koreait.day4.controller.CrudController;
import com.koreait.day4.model.entity.Item;
import com.koreait.day4.model.network.Header;
import com.koreait.day4.model.network.request.ItemApiRequest;
import com.koreait.day4.model.network.response.ItemApiResponse;
import com.koreait.day4.service.ItemApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemApiController extends CrudController<ItemApiRequest, ItemApiResponse, Item> {
    private final ItemApiLogicService itemApiLogicService;

    /*
        "transaction_time":"2021-08-24",
        "description":"OK",
        "data":{
            "name":"삼성 냉장고",
            "title":"냉장고",
            "content":"엄청 시원해요",
            "price":2000000,
            "createBy":"",

     */
    @Override
    @PostMapping("")    // /api/item    // http://127.0.0.1:9900/api/item
    public Header<ItemApiResponse> create(@RequestBody Header<ItemApiRequest> request) {
        System.out.println(request);
        return itemApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}") // /api/item/{id}    // http://127.0.0.1:9900/api/item/6
    public Header<ItemApiResponse> read(@PathVariable(name = "id") Long id) {
        return itemApiLogicService.read(id);
    }

    @Override
    @PutMapping("")     // /api/item    // http://127.0.0.1:9900/api/item
    public Header<ItemApiResponse> update(@RequestBody Header<ItemApiRequest> request) {
        return itemApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")  // /api/item/{id}    // http://127.0.0.1:9900/api/item/6
    public Header<ItemApiResponse> delete(@PathVariable(name = "id") Long id) {
        return itemApiLogicService.delete(id);
    }


    @GetMapping("") //api/item
    public Header<List<ItemApiResponse>> findAll(@PageableDefault(sort={"id"}, direction= Sort.Direction.DESC) Pageable pageable){
        return itemApiLogicService.search(pageable);
    }
}
