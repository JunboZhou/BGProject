package com.changgou.service.goods.service.impl;

import com.changgou.goods.pojo.Brand;
import com.changgou.service.goods.dao.BrandMapper;
import com.changgou.service.goods.service.BrandService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findList() {
        List<Brand> brandList = brandMapper.selectAll();
        return brandList;
    }

    @Override
    public Brand findById(Integer id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        return brand;
    }

    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public void update(Brand brand) {

    }

    @Override
    public void delById(Integer id) {

    }

    @Override
    public List<Brand> list(Map<String, Object> searchMap) {
        return null;
    }

    @Override
    public Page<Brand> findPage(int page, int size) {
        return null;
    }

    @Override
    public Page<Brand> findPage(Map<String, Object> searchMap, int page, int size) {
        return null;
    }
}
