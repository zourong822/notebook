package com.leyou.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.client.BrandClient;
import com.leyou.client.CategoryClient;
import com.leyou.client.GoodsClient;
import com.leyou.client.SpecificationClient;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.pojo.*;
import com.leyou.pojo.Goods;
import com.leyou.pojo.SearchRequest;
import com.leyou.pojo.SearchResult;
import com.leyou.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: zourong
 * @Date: 2020/6/28 11:13
 * @Description:
 */
@Service
@Slf4j
public class GoodsService {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;
    
    @Autowired
    private SpecificationClient specClient;

    @Autowired
    private GoodsRepository repository;

    @Autowired
    private ElasticsearchTemplate template;
    /**
     * @Author zourong
     * @Description 构建Goods对象存入ElasticSearch
     * @Date 2020/6/28 11:15
     * @Param [spu]
     * @return com.leyou.pojo.Goods
     **/
    public Goods buildGoods(Spu spu){
        Goods goods = new Goods();
        Long spuId = spu.getId();
        //填充数据
        goods.setId(spuId);
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setSubTitle(spu.getSubTitle());
        List<Sku> skuList = goodsClient.querySkuBySpuId(spuId);
        //设置价格列表，从sku列表中获取
        Set<Long> skuPriceList=new HashSet<>();
        //设置sku列表json字符串
        List<Map<String,Object>> skus=new ArrayList<>();
        for (Sku sku : skuList) {
            //简化sku数据，获取价格列表
            Map<String,Object> skuMap=new HashMap<>();
            skuMap.put("id",sku.getId());
            skuMap.put("title",sku.getTitle());
            skuMap.put("images", StringUtils.substringBefore(sku.getImages(),","));
            skuMap.put("price",sku.getPrice());
            skus.add(skuMap);
            skuPriceList.add(sku.getPrice());
        }
        goods.setPrice(skuPriceList);
        goods.setSkus(JsonUtils.serialize(skus));
        //设置搜索字段，所有需要被搜索的信息，包含标题，分类，甚至品牌
        StringBuilder all=new StringBuilder();
            //标题
        String title = spu.getTitle();
            //分类
        String category=StringUtils.join(categoryClient
                .queryListByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()))
                .stream().map(Category::getName).collect(Collectors.toList())," ");
            //品牌
        String brand=brandClient.queryBrandById(spu.getBrandId()).getName();

        all.append(title).append(" ").append(category).append(" ").append(brand);
        goods.setAll(all.toString());
        //设置要搜索的规格参数
        List<SpecParam> specParams = specClient.querySpecParamList(null, spu.getCid3(), true);
        SpuDetail detail=goodsClient.queryDetailBySpuId(spuId);
        Map<Long,String> genericSpec=JsonUtils.parseMap(detail.getGenericSpec(),Long.class,String.class);
        Map<Long,List<String>> specialSpec=JsonUtils.nativeRead(detail.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {});
        Map<String, Object> specs=new HashMap<>();
        for (SpecParam specParam : specParams) {
            String key=specParam.getName();
            Object value=null;
            if(specParam.getGeneric()){
                if(specParam.getNumeric()){
                    //数值类型处理成段,便于搜索
                    value=chooseSegment(genericSpec.get(specParam.getId()),specParam);
                }else{
                    value=genericSpec.get(specParam.getId());
                }
            }else{
                value=specialSpec.get(specParam.getId());
            }

            specs.put(key,value);
        }
        goods.setSpecs(specs);
        return goods;
    }


    /**
     * @Author zourong
     * @Description 把聚合搜索到的参数分别转换为段，用于搜索
     * @Date 2020/7/8 10:06
     * @Param [value, p]
     * @return java.lang.String
     **/
    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    public SearchResult<Goods> querySearchPage(SearchRequest request) {
        NativeSearchQueryBuilder queryBuilder=new NativeSearchQueryBuilder();
        //1.过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","subTitle","skus"},null));
        //2.分页 注意ElasticSearch分页的页码是从0开始的，所以页码得-1，否则查不到第一页
        Integer page = request.getPage()-1;
        Integer size = request.getSize();
        queryBuilder.withPageable(PageRequest.of(page, size));
        //3.查询条件,包含过滤条件
        QueryBuilder baseQuery = buildQuery(request);
        queryBuilder.withQuery(baseQuery);
        //4.聚合品牌和分类
        //4.1聚合分类
        String categoryAggrName="category_aggr";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggrName).field("cid3"));
        //4.2聚合品牌
        String brandAggrName="brand_aggr";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggrName).field("brandId"));
        //5.查询
//        Page<Goods> result = repository.search(queryBuilder.build());
        AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(), Goods.class);
        //6.解析
        //6.1解析分页
        SearchResult<Goods> searchResult=new SearchResult<>();
        searchResult.setItems(result.getContent());
        searchResult.setTotal(result.getTotalElements());
        searchResult.setTotalPage((long)result.getTotalPages());
        //6.2解析聚合
        Aggregations aggr = result.getAggregations();
        List<Category> categories=parseAggrCategory(aggr.get(categoryAggrName));
        List<Brand> brands=parseAggrBrand(aggr.get(brandAggrName));
        searchResult.setBrands(brands);
        searchResult.setCategories(categories);
        //6.3当分类唯一时,解析聚合规格参数可搜索列表
        List<Map<String,Object>> specs=null;
        if(null != categories && categories.size() ==1){
            specs=buildAggrSpecList(categories.get(0).getId(),baseQuery);
        }
        searchResult.setSpecs(specs);
        return searchResult;
    }

    /**
     * @Author zourong
     * @Description 构建查询条件
     * @Date 2020/7/2 16:08
     * @Param [request]
     * @return org.elasticsearch.index.query.QueryBuilder
     **/
    private QueryBuilder buildQuery(SearchRequest request) {
        //创建Boolean查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //查询条件
        boolQuery.must(QueryBuilders.matchQuery("all", request.getKey()));
        //过滤条件
        for (Map.Entry<String, String> entry : request.getFilter().entrySet()) {
            String key = entry.getKey();
            if(!"cid3".equals(key) && !"brandId".equals(key)) {
                key="specs."+key+".keyword";
            }
            boolQuery.filter(QueryBuilders.termQuery(key,entry.getValue()));
        }
        return boolQuery;
    }

    private List<Map<String,Object>> buildAggrSpecList(Long cid, QueryBuilder baseQuery) {
        List<Map<String,Object>> specs=new ArrayList<>();
        NativeSearchQueryBuilder queryBuilder=new NativeSearchQueryBuilder();
        //1.带上基础查询条件
        queryBuilder.withQuery(baseQuery);
        //2.拿到聚合规格参数
        List<SpecParam> params = specClient.querySpecParamList(null, cid, true);
        //3.添加聚合
        for (SpecParam param : params) {
            queryBuilder.addAggregation(AggregationBuilders.terms(param.getName()).field("specs."+param.getName()+".keyword"));
        }
        //4.拿到聚合结果
        AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(), Goods.class);
        Aggregations aggrs = result.getAggregations();
        //5.解析聚合结果
        for (SpecParam param : params) {
            StringTerms terms= aggrs.get(param.getName());
            List<String> options = terms.getBuckets().stream().map(t -> t.getKeyAsString()).collect(Collectors.toList());
            Map<String,Object> map=new HashMap<>();
            map.put("k",param.getName());
            map.put("options",options);
            specs.add(map);
        }
        return specs;
    }

    private List<Brand> parseAggrBrand(LongTerms terms) {
        try {
            List<Long> ids = terms.getBuckets().stream().map(c -> c.getKeyAsNumber().longValue()).collect(Collectors.toList());
            //调用品牌客户端查询分类信息
            List<Brand> brands = brandClient.queryBrandByIds(ids);
            return brands;
        } catch (Exception e) {
            log.error("[搜索服务]查询品牌出错",e);
        }
        return null;
    }

    private List<Category> parseAggrCategory(LongTerms terms) {
        try {
            List<Long> ids = terms.getBuckets().stream().map(c -> c.getKeyAsNumber().longValue()).collect(Collectors.toList());
            //调用分类客户端查询品牌信息
            List<Category> categories = categoryClient.queryListByIds(ids);
            return categories;
        } catch (Exception e) {
            log.error("[搜索服务]查询分类出错",e);
        }
        return null;
    }

    //商品新增或更新，更新索引库
    public void insertOrUpdateIndex(Long spuId) {
        //查询spu
        Spu spu = goodsClient.queryGoodsBySpuId(spuId);
        //构建商品索引
        Goods goods = buildGoods(spu);
        //存入索引库
        repository.save(goods);
    }
    //商品删除，删除对应索引库
    public void deleteIndex(Long spuId) {
        repository.deleteById(spuId);
    }
}
