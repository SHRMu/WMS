package de.demarks.wms;


import com.google.protobuf.RpcCallback;
import de.demarks.wms.common.service.Interface.RepositoryBatchManageService;
import de.demarks.wms.dao.RepositoryBatchMapper;
import de.demarks.wms.domain.RepositoryBatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BatchMapperTest {

    @Autowired
    RepositoryBatchMapper repositoryBatchMapper;

    @Test
    public void selectAllTest(){
        List<RepositoryBatch> batches = repositoryBatchMapper.selectAll(3001);
        System.out.println(batches.size());
    }

    @Test
    public void selectByBatchIDTest(){
        RepositoryBatch repositoryBatch = repositoryBatchMapper.selectByID(1, 3002);
        System.out.println(repositoryBatch.toString());
    }

    @Test
    public void selectByCodeTest(){
        List<RepositoryBatch> batches = repositoryBatchMapper.selectByCode("Anker",null);
        System.out.println(batches.size());
    }

    @Test
    public void selectByStatusTest(){
        List<RepositoryBatch> batches = repositoryBatchMapper.selectByStatus("可用", null);
        System.out.println(batches.size());
    }

    @Test
    public void insertTest(){
        RepositoryBatch repositoryBatch = new RepositoryBatch();
        repositoryBatch.setCode("Anker 10");
        repositoryBatch.setStatus("可用");
        repositoryBatch.setTime(new Date());
        repositoryBatch.setDesc("测试");
        repositoryBatch.setRepositoryID(3001);

        repositoryBatchMapper.insert(repositoryBatch);
    }

    @Test
    public void updateTest(){
        RepositoryBatch repositoryBatch = new RepositoryBatch();
        repositoryBatch.setCode("Anker 9");
        repositoryBatch.setStatus("完结");
        repositoryBatch.setDesc("已完结");
        repositoryBatch.setRepositoryID(3001);
        repositoryBatchMapper.update(repositoryBatch);
    }

    @Test
    public void deleteTest(){
        repositoryBatchMapper.deleteByID(1);
    }
}
