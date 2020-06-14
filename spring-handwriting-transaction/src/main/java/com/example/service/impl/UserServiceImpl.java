package com.example.service.impl;

import com.example.dao.UserDao;
import com.example.service.UserService;
import com.example.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private TransactionUtils transactionUtils;

    // spring 事务封装 aop技术
    // public void add() {
    // TransactionStatus transactionStatus = null;
    // try {
    // // 开启事务
    // transactionStatus = transactionUtils.begin();
    // userDao.add("test001", 20);
    // System.out.println("开始报错啦!!");
    // // int i = 1 / 0;
    // System.out.println("################");
    // userDao.add("test002", 21);
    // // 提交事务
    // if (transactionStatus != null)
    // transactionUtils.commit(transactionStatus);
    // } catch (Exception e) {
    // e.getMessage();
    // // 回滚事务
    // if (transactionStatus != null)
    // transactionUtils.rollback(transactionStatus);
    // }
    // }


    // @Transactional
    // public void add() {
    //// 注意事项： 在使用spring事务的时候，service 不要try 最好将异常抛出给外层aop 异常通知接受回滚
    ////若必须try的话就要在catch里面调用TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();进行回滚。
    ////否则不会抛出异常，userDao.add("wangmazi", 27);就会录入数据库而userDao.add("zhangsan", 16);不会录入，从而不能保证事务机制啦。
    // try {
    // userDao.add("wangmazi", 27);
    // int i = 1 / 0;
    // System.out.println("我是add方法");
    // userDao.add("zhangsan", 16);
    // } catch (Exception e) {
    // e.printStackTrace();
    // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    // }
    // }

    @Transactional
    public void add() {
        userDao.add("xiaoming", 27);
        int i = 1 / 0;
        System.out.println("我是add方法");
        userDao.add("zhoumin", 16);
    }

}

