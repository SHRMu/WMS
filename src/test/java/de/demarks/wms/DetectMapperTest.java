package de.demarks.wms;

import de.demarks.wms.dao.DetectMapper;
import de.demarks.wms.domain.DetectDO;
import de.demarks.wms.domain.StockInDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DetectMapperTest {

    @Autowired
    private DetectMapper detectMapper;

    @Test
    public void insertTest() throws Exception {

        DetectDO detectDO = new DetectDO();
        detectDO.setGoodsID(103);
        detectDO.setBatchID(10);
        detectDO.setRepositoryID(1003);
        detectDO.setPassed(100);
        detectDO.setScratch(20);
        detectDO.setDamage(100);
        detectDO.setTime(new Date());
        detectDO.setPersonInCharge("admin");
        detectMapper.insert(detectDO);

    }

}
