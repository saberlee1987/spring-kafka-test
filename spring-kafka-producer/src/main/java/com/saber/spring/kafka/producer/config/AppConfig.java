package com.saber.spring.kafka.producer.config;

import com.saber.model.Person;
import com.saber.model.Student;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Value(value = "${spring.kafka.topics.testTopic}")
    private String testTopic;
    @Value(value = "${spring.kafka.topics.studentTopic}")
    private String studentTopic;
    @Value(value = "${spring.kafka.topics.personTopic}")
    private String personTopic;
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public NewTopic testTopic() {
        return new NewTopic(testTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic studentTopic() {
        return new NewTopic(studentTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic personTopic() {
        return new NewTopic(personTopic, 1, (short) 1);
    }

    @Bean
    public ProducerFactory<String, Person> personProducerFactory() {
       return new DefaultKafkaProducerFactory<>(objectMap());
    }

    @Bean
    public ProducerFactory<String, Student> studentProducerFactory() {
        return new DefaultKafkaProducerFactory<>(objectMap());
    }

    @Bean
    public ProducerFactory<String, String> testProducerFactory() {
        Map<String, Object> testConfig = new HashMap<>();
        testConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        testConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        testConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(testConfig);
    }

    private Map<String,Object> objectMap(){
        Map<String, Object> objectConfig = new HashMap<>();
        objectConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        objectConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        objectConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return objectConfig;
    }
    @Bean
    public KafkaTemplate<String,String> kafkaTemplateTest(){
        return new KafkaTemplate<>(testProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, Person> kafkaTemplatePerson(){

        return new KafkaTemplate<>(personProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, Student> kafkaTemplateStudent(){
        return new KafkaTemplate<>(studentProducerFactory());
    }
}
