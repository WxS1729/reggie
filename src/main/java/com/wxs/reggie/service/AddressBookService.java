package com.wxs.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxs.reggie.entity.AddressBook;

public interface AddressBookService extends IService<AddressBook> {


    /**
     * 设置默认地址
     */
    public AddressBook setDefault(AddressBook addressBook);
}
