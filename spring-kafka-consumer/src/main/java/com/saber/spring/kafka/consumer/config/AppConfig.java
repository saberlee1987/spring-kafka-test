package com.saber.spring.kafka.consumer.config;

import com.saber.model.Person;
import com.saber.model.Student;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class AppConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value(value = "${spring.kafka.groups.testGroupId}")
    private String testGroupId;
    @Value(value = "${spring.kafka.groups.studentGroupId}")
    private String studentGroupId;
    @Value(value = "${spring.kafka.groups.personGroupId}")
    private String personGroupId;
    @Bean
    public ConsumerFactory<String,String> testConsumerFactory(){
        Map<String,Object> config= new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG,testGroupId);
        config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConsumerFactory<String, Person> personConsumerFactory(){
        Map<String,Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG,personGroupId);
        config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        return new DefaultKafkaConsumerFactory<>(config,
                new StringDeserializer(),
                new JsonDeserializer<>(Person.class));
    }

    @Bean
    public ConsumerFactory<String, Student> studentConsumerFactory(){
        Map<String,Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG,studentGroupId);
        config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                new JsonDeserializer<>(Student.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> testKafkaListenerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,String> testConcurrentKafkaListener =
                new ConcurrentKafkaListenerContainerFactory<>();
        testConcurrentKafkaListener.setConsumerFactory(testConsumerFactory());
        return testConcurrentKafkaListener;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,Person> personConcurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,Person> personConcurrentKafkaListenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        personConcurrentKafkaListenerContainerFactory.setConsumerFactory(personConsumerFactory());
        return personConcurrentKafkaListenerContainerFactory;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,Student> studentConcurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,Student> studentConcurrentKafkaListenerContainerFactory= new ConcurrentKafkaListenerContainerFactory<>();
        studentConcurrentKafkaListenerContainerFactory.setConsumerFactory(studentConsumerFactory());
        return studentConcurrentKafkaListenerContainerFactory;
    }

}
