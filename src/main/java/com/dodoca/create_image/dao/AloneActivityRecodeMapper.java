package com.dodoca.create_image.dao;

import com.dodoca.create_image.pojo.AloneActivityRecode;

public interface AloneActivityRecodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AloneActivityRecode record);

    int insertSelective(AloneActivityRecode record);

    AloneActivityRecode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AloneActivityRecode record);

    int updateByPrimaryKey(AloneActivityRecode record);
}