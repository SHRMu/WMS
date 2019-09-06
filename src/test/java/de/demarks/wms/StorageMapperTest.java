package de.demarks.wms;

import de.demarks.wms.dao.StorageMapper;
import de.demarks.wms.domain.Storage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class StorageMapperTest {

    @Autowired
    StorageMapper storageMapper;

    @Test
    public void selectByGoodsIDTest(){
        List<Storage> storages = storageMapper.selectByGoodsID(1001, null, null);
        System.out.println(storages.size());
    }

}
