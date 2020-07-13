package com.leyou.item.service;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/4/10 21:38
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * @Author halk
     * @Description 根据分类id查询参数组
     * @Date 2020/4/10 21:48
     * @Param [cid]
     * @return java.util.List<com.leyou.item.pojo.SpecGroup>
     **/
    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return this.specGroupMapper.select(specGroup);
    }

    /**
     * @Author halk
     * @Description 根据条件查询规格参数
     * @Date 2020/4/10 22:37
     * @Param [gid]
     * @return java.util.List<com.leyou.item.pojo.SpecParam>
     **/
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setCid(cid);
        specParam.setGeneric(generic);
        specParam.setGroupId(gid);
        specParam.setSearching(searching);
        return this.specParamMapper.select(specParam);
    }

    /**
     * @Author halk
     * @Description 更新规格参数的分组表
     * @Date 2020/4/18 13:27
     * @Param [specGroup]
     * @return java.lang.Boolean
     **/
    public Boolean updateGroupByIdAndCid(SpecGroup specGroup) {
        return this.specGroupMapper.updateByPrimaryKey(specGroup) != -1;
    }

    /**
     * @Author halk
     * @Description 增加一个参数分组
     * @Date 2020/4/18 15:45
     * @Param [specGroup]
     * @return java.lang.Boolean
     **/
    public Boolean addGroup(SpecGroup specGroup) {
        return this.specGroupMapper.insert(specGroup) != -1;
    }

    /**
     * @Author halk
     * @Description 根据id删除一个参数分组
     * @Date 2020/4/18 15:50
     * @Param [id]
     * @return java.lang.Boolean
     **/
    public Boolean deleteGroup(Long id) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
        return this.specGroupMapper.delete(specGroup) != -1;
    }

    /**
     * @Author halk
     * @Description 增加规格参数组下的参数
     * @Date 2020/4/18 16:40
     * @Param [specParam]
     * @return java.lang.Boolean
     **/
    public Boolean addParam(SpecParam specParam) {
        return this.specParamMapper.insert(specParam) != -1;
    }

    /**
     * @Author halk
     * @Description 更新规格参数组下的参数
     * @Date 2020/4/18 16:44
     * @Param [specParam]
     * @return java.lang.Boolean
     **/
    public Boolean updateParam(SpecParam specParam) {
        return this.specParamMapper.updateByPrimaryKey(specParam) != -1;
    }

    /**
     * @Author halk
     * @Description 删除规格参数组下的参数
     * @Date 2020/4/18 16:47
     * @Param [id]
     * @return java.lang.Boolean
     **/
    public Boolean deleteParam(Long id) {
        SpecParam specParam = new SpecParam();
        specParam.setId(id);
        return this.specParamMapper.delete(specParam) != -1;
    }

    /**
     * @Author halk
     * @Description 根据cid查询组与参数
     * @Date 2020/5/24 0024 10:40
     * @Param [cid]
     * @return java.util.List<com.leyou.item.pojo.SpecGroup>
     **/
    public List<SpecGroup> queryGroupWithParam(Long cid) {
        List<SpecGroup> specGroupList = this.queryGroupByCid(cid);
        specGroupList.forEach(specGroup -> {
            List<SpecParam> specParams = this.queryParams(specGroup.getId(), null, null, null);
            specGroup.setParams(specParams);
        });
        return specGroupList;
    }
}
