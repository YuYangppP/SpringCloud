package com.yu.springcloud.service.impl;

import com.yu.springcloud.dao.OrderDao;
import com.yu.springcloud.domain.Order;
import com.yu.springcloud.service.AccountService;
import com.yu.springcloud.service.OrderService;
import com.yu.springcloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: yy
 * @Date: 2020/11/18 23:38
 * @Version: 1.0.0
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private AccountService accountService;
    @Resource
    private StorageService storageService;


    /**
     * 创建订单->调用库存服务扣减库存->调用账户服务扣减账户余额->修改订单状态
     * 简单来说,下订单->减库存->减账户余额->改状态
     *
     * @param order
     */
    @Override
    @GlobalTransactional(name = "yy-create-order", rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("------------->开始新建订单");
        orderDao.create(order);

        log.info("------------->订单微服务开始调用库存,做扣减 数量");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("------------->订单微服务开始调用库存,做扣减end");

        log.info("------------->订单微服务开始调用账户,做扣减 账户余额");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("------------->订单微服务开始调用账户,做扣减end");

        log.info("------------->修改订单状态开始");
        orderDao.update(order.getUserId(), 0);
        log.info("------------->修改订单状态结束");

        log.info("------------->下订单结束了,O(∩_∩)O哈哈~");
    }
}
