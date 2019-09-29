package de.demarks.wms;

import de.demarks.wms.dao.StockStorageMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class StorageMapperTest {

    @Autowired
    StockStorageMapper stockStorageMapper;

    @Test
    public void selectAll(){
//        List<Storage> storages = storageMapper.selectAll(1, 3001);
//        System.out.println(storages.size());
    }

    @Test
    public void selectByGoodsIDTest(){
//        List<Storage> storages = storageMapper.selectByGoodsID(1001, 1, 3001);
//        System.out.println(storages.size());
    }

    @Test
    public void selectByGoodsName(){
//        List<Storage> storages = storageMapper.selectByGoodsName("A23", null, null);
//        System.out.println(storages.size());
    }

    @Test
    public void insertTest(){
//        Storage storage = new Storage();
//        storage.setGoodsID(1001);
//        storage.setCustomerID(2001);
//        storage.setBatchID(1);
//        storage.setRepositoryID(3001);
//        storage.setNumber((long) 100);
//        storageMapper.insert(storage);
    }

    @Test
    public void deleteTest(){
//        storageMapper.delete(1001,1,3001);
    }

}
