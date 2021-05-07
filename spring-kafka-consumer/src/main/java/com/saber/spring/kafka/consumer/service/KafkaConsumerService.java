package com.saber.spring.kafka.consumer.service;

import com.saber.model.Person;
import com.saber.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topics = "${spring.kafka.topics.testTopic}"
    ,groupId = "${spring.kafka.groups.testGroupId}")
    public void consumeTest(String message){
        log.info("Received message from testGroupId ==> {}",message);
    }

    @KafkaListener(topics = "${spring.kafka.topics.studentTopic}"
            ,groupId = "${spring.kafka.groups.studentGroupId}",
    containerFactory = "studentConcurrentKafkaListenerContainerFactory")
    public void consumeStudent(Student student){
        log.info("Received message from studentGroupId ==> {}",student);
    }

    @KafkaListener(topics = "${spring.kafka.topics.personTopic}"
            ,groupId = "${spring.kafka.groups.personGroupId}",
    containerFactory = "personConcurrentKafkaListenerContainerFactory")
    public void consumePerson(Person person){
        log.info("Received message from personGroupId ==> {}",person);
    }


}
