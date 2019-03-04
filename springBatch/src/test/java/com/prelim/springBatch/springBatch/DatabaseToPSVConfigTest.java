package com.prelim.springBatch.springBatch;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcCallOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import javax.sql.DataSource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBatchTest
@ContextConfiguration(classes = DatabaseToPSVConfig.class)
public class DatabaseToPSVConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    private SimpleJdbcCall simpleJdbcCall;
    private SimpleJdbcCallOperations simpleJdbcCallOperations;

    public void setDataSource (DataSource dataSource){
        this.simpleJdbcCall =  new SimpleJdbcCall(dataSource);
    }

    @Test
    public void launchJob()throws Exception {

        JobExecution jobExecution = (JobExecution) jobLauncherTestUtils.launchJob();
        //assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

    }
}