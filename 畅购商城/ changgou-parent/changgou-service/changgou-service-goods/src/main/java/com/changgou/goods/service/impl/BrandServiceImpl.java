package com.changgou.goods.service.impl;

import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.sound.midi.Soundbank;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        List<Brand> brands = brandMapper.selectAll();
        return brands;
    }

    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Brand> findList(Brand brand) {
        //1.创建条件对象( 设置字节码对象 标识 查询哪一个表)
        Example example = createExample(brand);
        // 根绝条件执行查询
        List<Brand> brands = brandMapper.selectByExample(example);
        return brands;
    }

    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {
        //1.开始分页 紧跟着的[第一个查询 才会被分页]
        PageHelper.startPage(page, size);
        // 执行查询
        List<Brand> brands = brandMapper.selectAll();
        List<Brand> brands1111 = brandMapper.selectAll();
        System.out.println(brands.size() + "::::::::brands1111:" + brands1111.size());

        return new PageInfo<Brand>(brands);
    }

    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size, Brand brand) {
        //1.开始分页
        PageHelper.startPage(page, size);
        // 构建查询条件
        Example example = createExample(brand);
        // 执行查询
        List<Brand> brands = brandMapper.selectByExample(example);

        return new PageInfo<Brand>(brands);
    }

    private Example createExample(Brand brand) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        //2.判断 拼接条件
        if (brand != null) {
            if (!StringUtils.isEmpty(brand.getName())) {
                criteria.andLike("name", "%" + brand.getName() + "%");
            }

            if (!StringUtils.isEmpty(brand.getLetter())) {
                criteria.andEqualTo("letter", brand.getLetter());
            }
        }
        return example;
    }
}
