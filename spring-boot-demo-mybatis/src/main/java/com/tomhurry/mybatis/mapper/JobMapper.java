package com.tomhurry.mybatis.mapper;

import com.tomhurry.mybatis.model.Job;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author taozhi
 * @date 2021/4/15
 * @since 1.0.0
 */
@Mapper
public interface JobMapper {

    @Insert("insert into job values(#{id},#{name},#{status},now())")
    int insert(Job job);

    @Select("select * from job where id = #{id}")
    Job selectById(String id);
}
