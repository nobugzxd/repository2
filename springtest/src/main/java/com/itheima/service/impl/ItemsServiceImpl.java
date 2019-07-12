package com.itheima.service.impl;

import com.itheima.dao.ItemDao;
import com.itheima.pojo.Items;
import com.itheima.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    private ItemDao itemDao;

    @Override
    public Items findById(Integer id) {
        return itemDao.findById(id);
    }
}
