package com.itheima.service;

import com.itheima.domain.SysLog;

import java.util.List;

public interface LogService {
    void saveLog(SysLog log) throws Exception;

    List<SysLog> findAll(Integer page, Integer size)throws Exception;
}
