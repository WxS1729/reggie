package com.wxs.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxs.reggie.common.BaseContext;
import com.wxs.reggie.entity.AddressBook;
import com.wxs.reggie.mapper.AddressBookMapper;
import com.wxs.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

    /**
     * 设置默认地址
     */
    @Override
    @Transactional
    public AddressBook setDefault(AddressBook addressBook) {

        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        wrapper.set(AddressBook::getIsDefault,0);
        //SQL:update address_book set is_default = 0 where user_id = ?
        this.update(wrapper);

        addressBook.setIsDefault(1);
        //SQL:update address_book set is_default = 1 where id = ?
        this.updateById(addressBook);
        return addressBook;
    }
}
