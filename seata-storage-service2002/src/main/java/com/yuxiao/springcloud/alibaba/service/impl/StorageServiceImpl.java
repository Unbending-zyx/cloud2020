package com.yuxiao.springcloud.alibaba.service.impl;

import com.yuxiao.springcloud.alibaba.dao.StorageDao;
import com.yuxiao.springcloud.alibaba.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {

    private static final Logger logger =LoggerFactory.getLogger(StorageServiceImpl.class);

    @Autowired
    private StorageDao storageDao;

    /**
     * 扣减库存
     * @param productId
     * @param count
     */
    @Override
    public void decrease(Long productId, Integer count) {
        logger.info("--------> storage-service 中扣减库存开始");
        storageDao.decrease(productId,count);
        logger.info("--------> storage-service 中扣减库存结束");
    }
}
