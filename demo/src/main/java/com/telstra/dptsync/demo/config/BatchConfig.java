package com.telstra.dptsync.demo.config;

import com.mongodb.MongoClient;
import com.telstra.dptsync.demo.service.S3Service;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;


@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Value("${DPT_RAW_PATH}")
    String DPT_RAW_PATH;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job importProductJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importProductJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(ItemWriter<Product> mongoWriter, ItemWriter<Product> mongoCleaner) {
        TaskletStep step1 = stepBuilderFactory.get("step1")
                .chunk(10)
                .reader(excelProductReader())
                .processor(processor())
                .writer(mongoCleaner)
                .writer(mongoWriter)
                .build();
        step1.setAllowStartIfComplete(true);
        return step1;
    }


    public ProductItemProcessor processor() {
        return new ProductItemProcessor();
    }

    @Bean
    public ItemReader<Product> excelProductReader() {
        ByteArrayResource byteArrayResource = new ByteArrayResource(s3Service.downloadFile(DPT_RAW_PATH));
        PoiItemReader reader = new PoiItemReader();
        reader.setLinesToSkip(1);
        reader.setResource(byteArrayResource);
        reader.setRowMapper(new ProductExcelRowMapper());
        //reader.setMaxItemCount(10)
        return reader;
    }


    @Bean
    public ItemWriter<Product> mongoWriter() {
        MongoItemWriter<Product> mongoItemWriter = new MongoItemWriter();
        mongoItemWriter.setCollection("products");
        mongoItemWriter.setTemplate(mongoTemplate());
        return mongoItemWriter;
    }


    @Bean
    public ItemWriter<Product> mongoCleaner() {
        MongoItemWriter<Product> mongoItemWriter = new MongoItemWriter();
        mongoItemWriter.setCollection("products");
        mongoItemWriter.setTemplate(mongoTemplate());
        mongoTemplate().dropCollection("products");
        return mongoItemWriter;
    }
    // end::readerwriterprocessor[]

    @Bean
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(new MongoClient(), "product-catalog");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }
}
