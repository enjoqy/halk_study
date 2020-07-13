package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/2/29 14:34
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * @return com.leyou.common.pojo.PageResult<com.leyou.item.pojo.Brand>
     * @Author halk
     * @Description 根据查询条件分页并排序查询品牌信息
     * @Date 2020/2/29 15:07
     * @Param [key, page, rows, sortBy, desc]
     **/
    public PageResult<Brand> queryBrandByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {

        //初始化Example
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        //根据name模糊查询，或者根据首字母查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }

        //添加分页条件
        PageHelper.startPage(page, rows);

        //添加排序条件
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        List<Brand> brands = this.brandMapper.selectByExample(example);

        //包装成pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);

        List<Brand> list = pageInfo.getList();
        //包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(), list);
    }

    /**
     * @return void
     * @Author halk
     * @Description 新增品牌
     * @Date 2020/3/12 14:13
     * @Param [brand, cids]
     **/
    @Transactional(rollbackFor = Exception.class)
    public void saveBrand(Brand brand, List<Long> cids) {

        //先新增brand
        this.brandMapper.insertSelective(brand);

        //在新增中间表
        saveCategoryAndBrand(brand, cids);

    }

    /**
     * @Author halk
     * @Description 在新增中间表
     * @Date 2020/5/3 0003 10:40
     * @Param [brand, cids]
     * @return void
     **/
    private void saveCategoryAndBrand(Brand brand, List<Long> cids) {
        cids.forEach((cid) -> {
            this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        });
    }

    /**
     * @Author halk
     * @Description 根据分类cid查询品牌集合
     * @Date 2020/4/22 17:33
     * @Param [cid]
     * @return java.util.List<com.leyou.item.pojo.Brand>
     **/
    public List<Brand> queryBrandsById(Long cid) {
        return this.brandMapper.selectBrandsByCid(cid);
    }

    /**
     * @Author halk
     * @Description 更新品牌
     * @Date 2020/5/3 0003 10:29
     * @Param [brand, cids]
     * @return void
     **/
    @Transactional(rollbackFor = Exception.class)
    public void updateBrand(Brand brand, List<Long> cids) {
        //先新增品牌表，在根据id删除中间表，最后新增中间表
        this.brandMapper.updateByPrimaryKey(brand);

        this.brandMapper.deleteBrandAndCategoryByBid(brand.getId());

        saveCategoryAndBrand(brand, cids);
    }

    /**
     * @Author halk
     * @Description 根据bid删除品牌和品牌与分类对应的关系
     * @Date 2020/5/3 0003 19:14
     * @Param [bid]
     * @return void
     **/
    @Transactional(rollbackFor = Exception.class)
    public void deleteBrandByBid(Long bid) {
        //先删品牌，在处理中间表
        this.brandMapper.deleteByPrimaryKey(bid);

        this.brandMapper.deleteBrandAndCategoryByBid(bid);
    }

    /**
     * @Author halk
     * @Description 根据id查询品牌
     * @Date 2020/5/13 0013 10:53
     * @Param [id]
     * @return com.leyou.item.pojo.Brand
     **/
    public Brand queryBrandById(Long id) {
        return this.brandMapper.selectByPrimaryKey(id);
    }
}
