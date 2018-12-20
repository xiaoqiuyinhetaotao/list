package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.dao.LogDao;
import com.itheima.domain.SysLog;
import com.itheima.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LogServiceImpl implements LogService {


    @Autowired
    private LogDao logDao;

    @Override
    public void saveLog(SysLog log) throws Exception {
        logDao.save(log);
    }

    @Override
    public List<SysLog> findAll(Integer page, Integer size) throws Exception {
        PageHelper.startPage(page,size);
        return logDao.findAll();
    }
}
