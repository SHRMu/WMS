package de.demarks.wms;

import de.demarks.wms.dao.DetectStorageMapper;
import de.demarks.wms.domain.DetectStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DetectStorageMapperTest {

    @Autowired
    DetectStorageMapper detectStorageMapper;

    @Test
    public void selectByGoodsIDTest(){
        List<DetectStorage> detectStorageList = detectStorageMapper.selectByGoodsID(1001, null, null);
        System.out.println(detectStorageList.size());
    }
}
