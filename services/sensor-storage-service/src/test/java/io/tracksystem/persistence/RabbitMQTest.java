package io.tracksystem.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.RabbitMQContainer;

import io.tracksystem.persistence.dto.MessageTemperatureDataDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = { RabbitConfigurationTest.class })
public class RabbitMQTest {

    @Value("${tracksystem.queue.name}")
    private String queue;

    @Autowired
    private static RabbitMQContainer rabbitMQContainer;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendAndReceive() {
        for (MessageTemperatureDataDTO messageToSend : getMessagesToSend()) {
            rabbitTemplate.convertAndSend("temperature-data", messageToSend);

            MessageTemperatureDataDTO receivedMessage = (MessageTemperatureDataDTO) rabbitTemplate
                    .receiveAndConvert("temperature-data");

            assertNotNull(receivedMessage.getSensorId());
            assertThat(receivedMessage.getSensorId()).isEqualTo(messageToSend.getSensorId());
            assertThat(receivedMessage.getTimestamp()).isEqualTo(messageToSend.getTimestamp());
            assertThat(receivedMessage.getTemperature()).isEqualTo(messageToSend.getTemperature());
            assertThat(receivedMessage.getDegree()).isEqualTo(messageToSend.getDegree());
        }
    }

    public Date getCurrentLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private ArrayList<MessageTemperatureDataDTO> getMessagesToSend() {
        MessageTemperatureDataDTO messageToSend1 = new MessageTemperatureDataDTO();
        messageToSend1.setSensorId("SENSOR001");
        messageToSend1.setTimestamp(getCurrentLocalDateTime());
        messageToSend1.setTemperature(Float.parseFloat("45.0"));
        messageToSend1.setDegree("Celsius");

        MessageTemperatureDataDTO messageToSend2 = new MessageTemperatureDataDTO();
        messageToSend2.setSensorId("SENSOR001");
        messageToSend2.setTimestamp(getCurrentLocalDateTime());
        messageToSend2.setTemperature(Float.parseFloat("45.0"));
        messageToSend2.setDegree("Celsius");

        MessageTemperatureDataDTO messageToSend3 = new MessageTemperatureDataDTO();
        messageToSend3.setSensorId("SENSOR002");
        messageToSend3.setTimestamp(getCurrentLocalDateTime());
        messageToSend3.setTemperature(Float.parseFloat("45.0"));
        messageToSend3.setDegree("Celsius");

        ArrayList<MessageTemperatureDataDTO> messageList = new ArrayList<>();
        messageList.add(messageToSend1);
        messageList.add(messageToSend2);
        messageList.add(messageToSend3);
        return messageList;
    }
}