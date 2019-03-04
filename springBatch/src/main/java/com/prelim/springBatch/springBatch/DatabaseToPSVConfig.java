
package com.prelim.springBatch.springBatch;

//import org.flywaydb.core.internal.jdbc.RowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;


import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

//https://www.jackrutorial.com/2018/03/spring-boot-batch-read-from-mysql-database-and-write-into-a-csv-file-tutorial.html

@Configuration

public class DatabaseToPSVConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public JdbcCursorItemReader<User> reader(DataSource dataSource){
        JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<User>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT id, first_name, last_name, age FROM user");

        reader.setRowMapper(new UserRowMapper());

        return reader;
    }

    public class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setFirst_name(resultSet.getString("first_name"));
            user.setLast_name(resultSet.getString("last_name"));
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
        writer.setResource(new ClassPathResource("data/users.psv"));
        writer.setLineAggregator(new DelimitedLineAggregator<User>() {{
            setDelimiter("|");
            setFieldExtractor(new BeanWrapperFieldExtractor<User>() {{
                setNames(new String[]{"id", "first_name", "last_name", "age"});
            }});
        }});

        return writer;

    }

    @Bean
    public Step step1(DataSource dataSource) {
        return stepBuilderFactory.get("step1").<User, User> chunk(10)
                .reader(reader(dataSource))
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job exportUserJob(DataSource dataSource) {
        return jobBuilderFactory.get("exportUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1(dataSource))
                .end()
                .build();
    }
}

