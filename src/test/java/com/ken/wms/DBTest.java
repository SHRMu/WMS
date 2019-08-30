package com.ken.wms;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBTest {
    @Test
    public void MySqlTest() throws Exception{
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wms_db?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "hyhz126");

        ApplicationContext context = new ClassPathXmlApplicationContext("config/SpringMySqlConfiguration.xml");
        DataSource ds = (DataSource)context.getBean("dataSource");
        Connection connection = ds.getConnection();
        System.out.println(connection);

    }
}
