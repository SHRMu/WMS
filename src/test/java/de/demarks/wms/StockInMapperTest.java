package de.demarks.wms;

import de.demarks.wms.dao.StockInMapper;
import de.demarks.wms.domain.StockInDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("web")
public class StockInMapperTest{

    @Autowired
    private StockInMapper stockInMapper;

    @Test
    public void insertTest() throws Exception {

        StockInDO stockInDO = new StockInDO();
        stockInDO.setPacket("DHL");
        stockInDO.setBatchID(10);
        stockInDO.setGoodsID(103);
        stockInDO.setCustomerID(1214);
        stockInDO.setNumber(100);
        stockInDO.setPersonInCharge("admin");
        stockInDO.setTime(new Date());
        stockInDO.setRepositoryID(1003);
        stockInMapper.insert(stockInDO);

    }
}
