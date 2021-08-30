package com.koreait.day4.controller.api;

import com.koreait.day4.controller.CrudController;
import com.koreait.day4.model.entity.OrderGroup;
import com.koreait.day4.model.network.Header;
import com.koreait.day4.model.network.request.OrderGroupApiRequest;
import com.koreait.day4.model.network.response.OrderGroupApiResponse;
import com.koreait.day4.service.OrderGroupApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordergroup")
@RequiredArgsConstructor
public class OrderGroupApiController extends CrudController<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {

    private final OrderGroupApiLogicService orderGroupApiLogicService;
    @Override
    @PostMapping("")    // /api/item    // http://127.0.0.1:9900/api/item
    public Header<OrderGroupApiResponse> create(@RequestBody Header<OrderGroupApiRequest> request) {
        System.out.println(request);
        return orderGroupApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}") // /api/item/{id}    // http://127.0.0.1:9900/api/item/6
    public Header<OrderGroupApiResponse> read(@PathVariable(name = "id") Long id) {
        return orderGroupApiLogicService.read(id);
    }

    @Override
    @PutMapping("")     // /api/item    // http://127.0.0.1:9900/api/item
    public Header<OrderGroupApiResponse> update(@RequestBody Header<OrderGroupApiRequest> request) {
        return orderGroupApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")  // /api/item/{id}    // http://127.0.0.1:9900/api/item/6
    public Header<OrderGroupApiResponse> delete(@PathVariable(name = "id") Long id) {
        return orderGroupApiLogicService.delete(id);
    }



    @GetMapping("") //api/adminuser
    public Header<List<OrderGroupApiResponse>> findAll(@PageableDefault(sort={"id"}, direction= Sort.Direction.DESC) Pageable pageable){
        return orderGroupApiLogicService.search(pageable);
    }
}
