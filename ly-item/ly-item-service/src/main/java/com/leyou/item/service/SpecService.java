package com.leyou.item.service;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SpecService {
    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecGroup> querySpecGroups(Long cid) {

        SpecGroup record = new SpecGroup();
        record.setCid(cid);

        //查询规格组，为了方便同时查询组内的参数
        List<SpecGroup> specGroups = this.specGroupMapper.select(record);
        //商品详情
        specGroups.forEach(specGroup -> {

            SpecParam paramRecord = new SpecParam();
            //根据规格组的id查询规格参数
            paramRecord.setGroupId(specGroup.getId());
            specGroup.setSpecParams(this.specParamMapper.select(paramRecord));
        });

        return specGroups;
    }

    public List<SpecParam> querySpecParam(Long gid, Long cid, Boolean searching, Boolean generic) {

        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        specParam.setGeneric(generic);
        return this.specParamMapper.select(specParam);
    }
    @Transactional
    public void addSpecGroup(SpecGroup specGroup) {
        this.specGroupMapper.insertSelective(specGroup);
    }
    @Transactional
    public void deleteSpecGroup(Long id) {
        this.specGroupMapper.deleteByPrimaryKey(id);
    }

    public void updateSpecGroup(SpecGroup specGroup) {
        this.specGroupMapper.updateByPrimaryKey(specGroup);
    }
}
