package de.demarks.wms;

import de.demarks.wms.dao.PacketMapper;
import de.demarks.wms.domain.Packet;
import org.apache.tools.ant.taskdefs.Pack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PacketMapperTest {

    @Autowired
    PacketMapper packetMapper;

    @Test
    public void selectAllTest(){
        List<Packet> packetList = packetMapper.selectAll(null);
        System.out.println(packetList.size());
    }

    @Test
    public void selectByIDTest(){
        Packet packet = packetMapper.selectByID(1);
        if (packet != null)
            System.out.println(packet.toString());
    }

    @Test
    public void selectByTraceTest(){
        Packet packet = packetMapper.selectByTrace("DHL123456", 3001);
        if (packet != null)
            System.out.println(packet.toString());
    }

    @Test
    public void selectByTraceApproximateTest(){
        List<Packet> packetList = packetMapper.selectByTraceApproximate("DHL", null, null);
        System.out.println(packetList.size());
    }

    @Test
    public void insertTest(){
        Packet packet = new Packet();
        packet.setTrace("DHL123456");
        packet.setTime(new Date());
        packet.setDesc("DHL654321");
        packet.setRepositoryID(3001);
        packetMapper.insert(packet);
    }

    @Test
    public void updateTest(){
        Packet packet = new Packet();
        packet.setId(1);
        packet.setTrace("DHL");
        packet.setTime(new Date());
        packet.setRepositoryID(3001);
        packetMapper.update(packet);
    }

    @Test
    public void deleteByIDTest(){
        packetMapper.deleteByID(1);
    }

}
