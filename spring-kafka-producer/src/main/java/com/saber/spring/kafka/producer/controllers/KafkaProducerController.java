package com.saber.spring.kafka.producer.controllers;

import com.saber.model.Person;
import com.saber.model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/publish")
public class KafkaProducerController {
    private final KafkaTemplate<String,String> kafkaTemplateTest;
    private final KafkaTemplate<String, Person> kafkaTemplatePerson;
    private final KafkaTemplate<String,Student> kafkaTemplateStudent;

    @Value(value = "${spring.kafka.topics.testTopic}")
    private String testTopic;
    @Value(value = "${spring.kafka.topics.studentTopic}")
    private String studentTopic;
    @Value(value = "${spring.kafka.topics.personTopic}")
    private String personTopic;


    public KafkaProducerController(KafkaTemplate<String, String> kafkaTemplateTest,
                                   KafkaTemplate<String, Person> kafkaTemplatePerson,
                                   KafkaTemplate<String, Student> kafkaTemplateStudent) {
        this.kafkaTemplateTest = kafkaTemplateTest;
        this.kafkaTemplatePerson = kafkaTemplatePerson;
        this.kafkaTemplateStudent = kafkaTemplateStudent;
    }
    @GetMapping(value = "/test")
    public String testPublish(){
        kafkaTemplateTest.send(testTopic,"This is Test Message For TestTopic");
        return "Message Test is publish";
    }
    @GetMapping(value = "/student")
    public String studentPublish(){
        Student student = new Student();
        student.setFirstName("Jackie");
        student.setLastName("Chan");
        student.setAge(65);
        student.setField("Kung Fu");
        student.setMobileNumber("09365627895");
        kafkaTemplateStudent.send(studentTopic,student);
        return "Message Student is publish";
    }
    @GetMapping(value = "/person")
    public String personPublish(){
        Person person =new Person();
        person.setFirstName("Saber");
        person.setLastName("Azizi");
        person.setAge(33);
        person.setMobileNumber("09351298857");
        kafkaTemplatePerson.send(personTopic,person);
        return "Message Person is publish";
    }
}
