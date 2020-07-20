package com.leyou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.*;
import com.leyou.mapper.*;
import com.leyou.service.BrandService;
import com.leyou.service.CategoryService;
import com.leyou.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: zourong
 * @Date: 2020/6/17 15:24
 * @Description:
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Override
    public PageResult<Spu> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example=new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        if(null != saleable){
            criteria.andEqualTo("saleable",saleable?1:0);
        }
        //查询
        List<Spu> list = spuMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.GOODS_IS_EMPTY);
        }
        //处理分类名称和品牌名称
        getNames(list);

        PageInfo<Spu> pageInfo=new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(),list);
    }

    private void getNames(List<Spu> list) {
        for (Spu spu : list) {
            spu.setCname(StringUtils.join(categoryService.selectByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList()),
                    "/")
                    );
            spu.setBname(brandService.getBrandById(spu.getBrandId()).getName());
        }
    }

    @Override
    @Transactional
    public void addGoods(Spu spu) {
        //保存spu
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setValid(true);
        spu.setSaleable(true);
        int count = spuMapper.insert(spu);
        if(count!=1){
            throw new LyException(ExceptionEnum.ADD_GOODS_FAILED);
        }
        //保存spuDetail
        spu.getSpuDetail().setSpuId(spu.getId());
        count = spuDetailMapper.insert(spu.getSpuDetail());
        if(count != 1){
            throw new LyException(ExceptionEnum.ADD_GOODS_FAILED);
        }
        //添加sku(商品规格)和stock(库存)
        addSkuAndStock(spu);
        //rabbitMQ发送商品新增消息
        amqpTemplate.convertAndSend("item.insert",spu.getId());
    }

    private void addSkuAndStock(Spu spu) {
        int count;//保存sku
        List<Stock> stocks=new ArrayList<>();
        List<Sku> skus = spu.getSkus();
        Date createTime = new Date();
        for (Sku sku : skus) {
            sku.setSpuId(spu.getId());
            sku.setCreateTime(createTime);
            sku.setLastUpdateTime(createTime);
            count = skuMapper.insert(sku);
            if(count != 1){
                throw new LyException(ExceptionEnum.ADD_GOODS_FAILED);
            }
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stocks.add(stock);
        }
        //批量保存stock
        count = stockMapper.insertList(stocks);
        if(count != stocks.size()){
                throw new LyException(ExceptionEnum.ADD_GOODS_FAILED);
        }
    }

    @Override
    public SpuDetail queryDetialBySpuId(Long spuId) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);
        if(null == spuDetail){
            throw new LyException(ExceptionEnum.GOODS_DETAIL_IS_EMPTY);
        }
        return spuDetail;
    }

    @Override
    public List<Sku> querySkuBySpuId(Long spuId) {
        Sku sku=new Sku();
        sku.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku);
        if(CollectionUtils.isEmpty(skuList)){
            throw new LyException(ExceptionEnum.GOODS_SKU_IS_EMPTY);
        }
        //获取skuId集合
        List<Long> skuIds = skuList.stream().map(Sku::getId).collect(Collectors.toList());
        //根据skuId集合查询库存
        List<Stock> stocks = stockMapper.selectByIdList(skuIds);
        if(CollectionUtils.isEmpty(stocks)){
            throw new LyException(ExceptionEnum.GOODS_STOCK_IS_EMPTY);
        }
        //构建skuId-->库存的map
        Map<Long, Integer> stockMap = stocks.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        //将库存信息存入sku集合用于回显
        skuList.forEach(s -> s.setStock(stockMap.get(s.getId())));
        return skuList;
    }

    @Override
    @Transactional
    public void updateGoods(Spu spu) {
        //检查id是否存在
        if(null == spu.getId()){
            throw new LyException(ExceptionEnum.GOODS_ID_CANNOT_BE_NULL);
        }
        //如果原本存在sku和stock则全部删除
        Sku sku=new Sku();
        sku.setSpuId(spu.getId());
        List<Sku> skuList = skuMapper.select(sku);
        int count;
        if(!CollectionUtils.isEmpty(skuList)){
            //删除sku
            count = skuMapper.deleteByIdList(skuList.stream().map(Sku::getId).collect(Collectors.toList()));
            if(count != skuList.size()){
                throw new LyException(ExceptionEnum.UPDATE_GOODS_FAILED);
            }
            //删除stock
            List<Long> skuIds = skuList.stream().map(Sku::getId).collect(Collectors.toList());
            count = stockMapper.deleteByIdList(skuIds);
            if(count != skuIds.size()){
                throw new LyException(ExceptionEnum.UPDATE_GOODS_FAILED);
            }
        }
        //更新spu
        spu.setCreateTime(null);
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setLastUpdateTime(new Date());
        count = spuMapper.updateByPrimaryKeySelective(spu);
        if(count !=1){
            throw new LyException(ExceptionEnum.UPDATE_GOODS_FAILED);
        }
        //更新spuDetail
        count = spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
        if(count !=1){
            throw new LyException(ExceptionEnum.UPDATE_GOODS_FAILED);
        }
        //添加sku和stock
        addSkuAndStock(spu);

        //rabbitMQ发送商品更新消息
        amqpTemplate.convertAndSend("item.update",spu.getId());
    }

    @Override
    public Spu queryGoodsBySpuId(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if(null == spu){
            throw new LyException(ExceptionEnum.GOODS_IS_EMPTY);
        }
        spu.setSkus(querySkuBySpuId(id));
        spu.setSpuDetail(queryDetialBySpuId(id));

        return spu;
    }
}
