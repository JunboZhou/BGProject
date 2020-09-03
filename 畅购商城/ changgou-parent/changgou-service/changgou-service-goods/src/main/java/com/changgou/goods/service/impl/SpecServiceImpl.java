package com.changgou.goods.service.impl;

import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.SpecMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Spec;
import com.changgou.goods.service.SpecService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpecMapper specMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * Spec条件+分页查询
     *
     * @param spec 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Spec> findPage(Spec spec, int page, int size) {
        PageHelper.startPage(page, size);
        // 搜索条件构建
        Example example = createExample(spec);
        return new PageInfo<Spec>(specMapper.selectByExample(example));
    }

    /**
     * Spec分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Spec> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        return new PageInfo<Spec>(specMapper.selectAll());
    }

    /**
     * Spec条件查询
     *
     * @param spec
     * @return
     */
    @Override
    public List<Spec> findList(Spec spec) {
        Example example = createExample(spec);
        return specMapper.selectByExample(example);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        specMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Spec
     *
     * @param spec
     */
    @Override
    public void update(Spec spec) {
        specMapper.updateByPrimaryKey(spec);
    }

    /**
     * 增加Spec
     *
     * @param spec
     */
    @Override
    public void add(Spec spec) {
        specMapper.insert(spec);
    }

    /**
     * 根据ID查询Spec
     *
     * @param id
     * @return
     */
    @Override
    public Spec findById(Integer id) {
        return specMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Spec全部数据
     *
     * @return
     */
    @Override
    public List<Spec> findAll() {
        return specMapper.selectAll();
    }

    @Override
    public List<Spec> findByCategoryId(Integer id) {
        //1.先根据商品分类的ID 获取模板的ID
        Category category = categoryMapper.selectByPrimaryKey(id);
        // 2.再根据模板的ID 获取模板对应的规格的列表
        Spec condition = new Spec();
        condition.setTemplateId(category.getTemplateId());
        return specMapper.select(condition);
    }


    /**
     * Spec构建查询对象
     *
     * @param spec
     * @return
     */
    public Example createExample(Spec spec) {
        Example example = new Example(Spec.class);
        Example.Criteria criteria = example.createCriteria();
        if (spec != null) {
            // ID
            if (!StringUtils.isEmpty(spec.getId())) {
                criteria.andEqualTo("id", spec.getId());
            }
            // 名称
            if (!StringUtils.isEmpty(spec.getName())) {
                criteria.andLike("name", "%" + spec.getName() + "%");
            }
            // 规格选项
            if (!StringUtils.isEmpty(spec.getOptions())) {
                criteria.andEqualTo("options", spec.getOptions());
            }
            // 排序
            if (!StringUtils.isEmpty(spec.getSeq())) {
                criteria.andEqualTo("seq", spec.getSeq());
            }
            // 模板ID
            if (!StringUtils.isEmpty(spec.getTemplateId())) {
                criteria.andEqualTo("templateId", spec.getTemplateId());
            }
        }
        return example;
    }

}
