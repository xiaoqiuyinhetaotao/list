package com.itheima.dao;

import com.itheima.domain.Member;
import org.apache.ibatis.annotations.Select;

public interface MemberDao {


    @Select("select * from member where id = #{memberid}")
    public Member findById(String  memberid ) throws Exception;
}
