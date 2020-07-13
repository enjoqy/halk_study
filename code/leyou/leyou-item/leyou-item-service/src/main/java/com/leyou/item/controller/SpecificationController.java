package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/4/10 21:41
 */
@Controller
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * @Author halk
     * @Description 根据分类id查询参数组
     * @Date 2020/4/10 22:19
     * @Param [cid]
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.SpecGroup>>
     **/
    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> specGroupList = this.specificationService.queryGroupByCid(cid);
        if (CollectionUtils.isEmpty(specGroupList)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specGroupList);
    }

    /**
     * @Author halk
     * @Description 更新规格参数的分组表
     * @Date 2020/4/18 13:27
     * @Param [specGroup]
     * @return org.springframework.http.ResponseEntity
     **/
    @PutMapping("/group")
    public ResponseEntity updateGroupByIdAndCid(@RequestBody SpecGroup specGroup){
        Boolean result = this.specificationService.updateGroupByIdAndCid(specGroup);
        if (result == true){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * @Author halk
     * @Description 增加一个参数分组
     * @Date 2020/4/18 15:45
     * @Param [specGroup]
     * @return org.springframework.http.ResponseEntity
     **/
    @PostMapping("/group")
    public ResponseEntity addGroup(@RequestBody SpecGroup specGroup){
        Boolean result = this.specificationService.addGroup(specGroup);
        if (result == true){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * @Author halk
     * @Description 根据id删除一个参数分组
     * @Date 2020/4/18 15:50
     * @Param [id]
     * @return org.springframework.http.ResponseEntity
     **/
    @DeleteMapping("/group/{id}")
    public ResponseEntity deleteGroup(@PathVariable("id")Long id){
        Boolean result = this.specificationService.deleteGroup(id);
        if (result == true){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * @Author halk
     * @Description  根据条件查询规格参数
     * @Date 2020/4/10 22:36
     * @Param [gid]
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.SpecParam>>
     **/
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParams(
            @RequestParam(value = "gid", required = false)Long gid,
            @RequestParam(value = "cid", required = false)Long cid,
            @RequestParam(value = "generic", required = false)Boolean generic,
            @RequestParam(value = "searching", required = false)Boolean searching
    ){
        List<SpecParam> specParamList = this.specificationService.queryParams(gid, cid, generic, searching);
        if (CollectionUtils.isEmpty(specParamList)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(specParamList);
    }

    /**
     * @Author halk
     * @Description 增加规格参数组下的参数
     * @Date 2020/4/18 16:40
     * @Param [specParam]
     * @return org.springframework.http.ResponseEntity
     **/
    @PostMapping("param")
    public ResponseEntity addParam(@RequestBody SpecParam specParam){
        Boolean result = this.specificationService.addParam(specParam);
        if (result == true){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * @Author halk
     * @Description 更新规格参数组下的参数
     * @Date 2020/4/18 16:43
     * @Param [specParam]
     * @return org.springframework.http.ResponseEntity
     **/
    @PutMapping("/param")
    public ResponseEntity updateParam(@RequestBody SpecParam specParam){
        Boolean result = this.specificationService.updateParam(specParam);
        if (result == true){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * @Author halk
     * @Description 删除规格参数组下的参数
     * @Date 2020/4/18 16:47
     * @Param [id]
     * @return org.springframework.http.ResponseEntity
     **/
    @DeleteMapping("/param/{id}")
    public ResponseEntity deleteParam(@PathVariable("id")Long id){
        Boolean result = this.specificationService.deleteParam(id);
        if (result == true){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * @Author halk
     * @Description 根据cid查询组与参数
     * @Date 2020/5/24 0024 10:39
     * @Param [cid]
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.SpecGroup>>
     **/
    @GetMapping("/group/param/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupWithParam(@PathVariable("cid")Long cid){
        List<SpecGroup> specGroupList = this.specificationService.queryGroupWithParam(cid);
        if (CollectionUtils.isEmpty(specGroupList)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(specGroupList);
    }
}
