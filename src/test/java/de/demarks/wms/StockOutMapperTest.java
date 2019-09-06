package de.demarks.wms;

import de.demarks.wms.dao.StockOutMapper;
import de.demarks.wms.domain.StockOutDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class StockOutMapperTest {

    @Autowired
    StockOutMapper stockOutMapper;

    @Test
    public void selectByGoodsIDTest(){
        List<StockOutDO> stockOutDOS = stockOutMapper.selectByGoodsID(1001);
        System.out.println(stockOutDOS.size());
    }

}
