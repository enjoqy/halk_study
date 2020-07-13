package com.leyou.item.api;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/4/10 21:41
 */
@RequestMapping("spec")
public interface SpecificationApi {

    /**
     * @return org.springframework.http.ResponseEntity<java.util.List < com.leyou.item.pojo.SpecGroup>>
     * @Author halk
     * @Description 根据分类id查询参数组
     * @Date 2020/4/10 22:19
     * @Param [cid]
     **/
    @GetMapping("/groups/{cid}")
    List<SpecGroup> queryGroupByCid(@PathVariable("cid") Long cid);

    /**
     * @return org.springframework.http.ResponseEntity
     * @Author halk
     * @Description 更新规格参数的分组表
     * @Date 2020/4/18 13:27
     * @Param [specGroup]
     **/
    @PutMapping("/group")
    Void updateGroupByIdAndCid(@RequestBody SpecGroup specGroup);

    /**
     * @return org.springframework.http.ResponseEntity
     * @Author halk
     * @Description 增加一个参数分组
     * @Date 2020/4/18 15:45
     * @Param [specGroup]
     **/
    @PostMapping("/group")
    Void addGroup(@RequestBody SpecGroup specGroup);

    /**
     * @return org.springframework.http.ResponseEntity
     * @Author halk
     * @Description 根据id删除一个参数分组
     * @Date 2020/4/18 15:50
     * @Param [id]
     **/
    @DeleteMapping("/group/{id}")
    Void deleteGroup(@PathVariable("id") Long id);

    /**
     * @return org.springframework.http.ResponseEntity<java.util.List < com.leyou.item.pojo.SpecParam>>
     * @Author halk
     * @Description 根据条件查询规格参数
     * @Date 2020/4/10 22:36
     * @Param [gid]
     **/
    @GetMapping("params")
    List<SpecParam> queryParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "searching", required = false) Boolean searching
    );

    /**
     * @return org.springframework.http.ResponseEntity
     * @Author halk
     * @Description 增加规格参数组下的参数
     * @Date 2020/4/18 16:40
     * @Param [specParam]
     **/
    @PostMapping("param")
    Void addParam(@RequestBody SpecParam specParam);

    /**
     * @return org.springframework.http.ResponseEntity
     * @Author halk
     * @Description 更新规格参数组下的参数
     * @Date 2020/4/18 16:43
     * @Param [specParam]
     **/
    @PutMapping("/param")
    Void updateParam(@RequestBody SpecParam specParam);

    /**
     * @return org.springframework.http.ResponseEntity
     * @Author halk
     * @Description 删除规格参数组下的参数
     * @Date 2020/4/18 16:47
     * @Param [id]
     **/
    @DeleteMapping("/param/{id}")
    Void deleteParam(@PathVariable("id") Long id);

    /**
     * @return org.springframework.http.ResponseEntity<java.util.List < com.leyou.item.pojo.SpecGroup>>
     * @Author halk
     * @Description 根据cid查询组与参数
     * @Date 2020/5/24 0024 10:39
     * @Param [cid]
     **/
    @GetMapping("/group/param/{cid}")
    List<SpecGroup> queryGroupWithParam(@PathVariable("cid") Long cid);
}
