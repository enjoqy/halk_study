package org.halk.kill.model.mapper;

import org.halk.kill.model.entity.ItemKill;

public interface ItemKillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemKill record);

    int insertSelective(ItemKill record);

    ItemKill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemKill record);

    int updateByPrimaryKey(ItemKill record);
}
