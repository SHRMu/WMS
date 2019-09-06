package de.demarks.wms;

import de.demarks.wms.dao.GoodsMapper;
import de.demarks.wms.dao.StockInMapper;
import de.demarks.wms.dao.StockOutMapper;
import de.demarks.wms.domain.StockInDO;
import de.demarks.wms.domain.StockOutDO;
import de.demarks.wms.domain.Storage;
import de.demarks.wms.exception.GoodsManageServiceException;
import org.apache.ibatis.exceptions.PersistenceException;
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

    @Autowired
    StockOutMapper stockOutMapper;

    @Test
    public void deleteGoodsTest(){
       goodsMapper.deleteById(1);
    }

}
