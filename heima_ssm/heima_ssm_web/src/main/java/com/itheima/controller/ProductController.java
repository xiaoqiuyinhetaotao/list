package com.itheima.controller;


import com.github.pagehelper.PageInfo;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;


    @RequestMapping("/findAllTest.do")
    @RolesAllowed("ADMIN")
    public ModelAndView findAllTest(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,
                                    @RequestParam(name = "size",required = true,defaultValue = "4")Integer size) throws Exception {
        List<Product> product = productService.findAll(page,size);
        ModelAndView mv = new ModelAndView();
        PageInfo pageInfo = new PageInfo(product);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("product-list1");
        return mv;
    }


    @RequestMapping("/save.do")
    public String save(Product product) throws Exception {
        productService.save(product);
        return "redirect:findAllTest.do";
    }
}
