package com.itheima.controller;


import com.github.pagehelper.PageInfo;
import com.itheima.domain.Orders;
import com.itheima.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/orders")
@Controller
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /*@RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        //调用service查询数据
        List<Orders> orders = ordersService.findAll();
        ModelAndView mv = new ModelAndView();
        mv.addObject("ordersList",orders);
        //添加逻辑视图
        mv.setViewName("orders-list");
        return mv;
    }*/

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page" ,required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "4")Integer size) throws Exception {
        //调用service查询数据
        List<Orders> orders = ordersService.findAll(page,size);
        ModelAndView mv = new ModelAndView();
        //pageHelper提供的pageBean
        PageInfo info = new PageInfo(orders);
        mv.addObject("pageInfo",info);
        //添加逻辑视图
        mv.setViewName("orders-page-list");
        return mv;
    }


    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id",required = true)String ordersId) throws Exception {
        ModelAndView mv = new ModelAndView();
        Orders orders = ordersService.findById(ordersId);
//        Orders orders1 = orders.get(0);
        mv.addObject("orders",orders);
        mv.setViewName("orders-show");
        return mv;
    }
}
