package de.demarks.wms;

import de.demarks.wms.dao.GoodsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GoodsMapperTest {
    @Autowired
    GoodsMapper goodsMapper;

    @Test
    public void deleteGoodsTest(){
       goodsMapper.deleteById(1001);
    }

}
