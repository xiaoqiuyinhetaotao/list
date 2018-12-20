package com.itheima.dao;

import com.itheima.domain.Traveller;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TravellerDao {



    @Select("select * from traveller where id in (select travellerId from order_traveller where  orderId = #{orderId})")
    public List<Traveller> findByOrdersId(String orderId) throws Exception;
}
