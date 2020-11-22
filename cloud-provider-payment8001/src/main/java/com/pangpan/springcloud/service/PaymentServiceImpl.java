package com.pangpan.springcloud.service;

import com.pangpan.springcloud.dao.PaymentDao;
import com.pangpan.springcloud.entities.Payment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements  PaymentService {

    @Resource//和AutoWire一样
    PaymentDao paymentDao;


    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
