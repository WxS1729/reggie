package com.wxs.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wxs.reggie.common.BaseContext;
import com.wxs.reggie.common.R;
import com.wxs.reggie.entity.AddressBook;
import com.wxs.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Slf4j
@RequestMapping("/addressBook")
@RestController
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增
     * @param addressBook
     * @return
     */
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}",addressBook);
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        log.info("AddressBook：{}",addressBook);
        AddressBook addressBook1 = addressBookService.setDefault(addressBook);
        return R.success(addressBook1);
    }

    @GetMapping("/{id}")
    public R get(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null){
            return R.success(addressBook);
        }else {
            return R.error("没有找到该对象");
        }
    }


    /**
     * 查询默认地址
     * @return
     */
    @GetMapping("default")
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        queryWrapper.eq(AddressBook::getIsDefault,1);

        //SQL:select * from address_book where user_id = ? and is_default = 1
        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        if (addressBook == null){
            return R.error("没有找到该对象");
        }else {
            return R.success(addressBook);
        }
    }

    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook：{}",addressBook);

        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(addressBook.getUserId() != null,AddressBook::getUserId,addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);

        //SQL:select * from address_book where user_id = ? order by update_time desc
        return R.success(addressBookService.list(queryWrapper));

    }

    @DeleteMapping
    public R<String> delete(@RequestParam("ids") Long id){
        if (id != null){
            addressBookService.removeById(id);
            return R.success("删除成功");
        }else {
            return R.error("未知错误");
        }

    }



}
