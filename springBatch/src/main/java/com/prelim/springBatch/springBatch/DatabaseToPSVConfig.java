
package com.prelim.springBatch.springBatch;

import org.flywaydb.core.internal.jdbc.RowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.H2PagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class DatabaseToPSVConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    DataSource dataSource;

    @Bean
    public DataSource dataSource(){
       final DriverManagerDataSource dataSource = new DriverManagerDataSource();
       dataSource.setDriverClassName("org.h2.Driver");
       dataSource.setUrl("jdbc:h2:mem:testdb");
       //dataSource.setUsername("root");
       //dataSource.setPassword("root");


       return dataSource;
   }

    @Bean
    public JdbcCursorItemReader<User> reader(){
        JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<User>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT id, first_name, last_name FROM user");
        reader.setRowMapper((org.springframework.jdbc.core.RowMapper<User>) new UserRowMapper());

        return reader;
    }

    public class UserRowMapper implements RowMapper<User> {


        @Override
        public User mapRow(ResultSet resultSet) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setAge(resultSet.getInt("age"));

            return user;

        }
    }

    @Bean
    public UserProcessor processor(){
        return new UserProcessor();
    }

    @Bean
    public FlatFileItemWriter<User> writer() {
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<User>();
        writer.setResource(new ClassPathResource("users.psv"));
        writer.setLineAggregator(new DelimitedLineAggregator<User>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<User>() {{
                setNames(new String[]{"id", "first_name", "last_name", "age"});
            }});
        }});

        return writer;

    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<User, User> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job exportUserJob() {
        return jobBuilderFactory.get("exportUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }
}

