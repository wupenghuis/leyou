package com.bigdata;

import com.leyou.LySearchService;
import com.leyou.Repository.GoodsRepository;
import com.leyou.client.GoodsClient;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.SpuBo;
import com.leyou.pojo.Goods;
import com.leyou.service.IndexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchService.class)
public class IndexTest {
    @Autowired
    private IndexService indexService;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void init(){
            //建库
            elasticsearchTemplate.createIndex(Goods.class);
            //间表
            elasticsearchTemplate.putMapping(Goods.class);
    }

    @Test
    public void loadData(){
        int page=1;
        while (true){
            PageResult<SpuBo> pageResult = goodsClient.querySpuByPage(null, null, page, 50);
            //空
            if(pageResult==null){
                break;
            }
            page++;
            //获取商品list
            List<SpuBo> items = pageResult.getItems();
            ArrayList<Goods> list = new ArrayList<>();
            for (SpuBo spuBo:items){
               Goods goods=indexService.buildGoods(spuBo);
               list.add(goods);
            }
            goodsRepository.saveAll(list);
        }


    }


}
