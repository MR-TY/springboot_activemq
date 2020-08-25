package cn.lzg.mq.producer;

import cn.lzg.mq.dto.MessageDto;
import cn.lzg.mq.utils.ProtoStuffSerializerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.QosSettings;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.Date;

/**
 * 生产者类
 * @Author lzg
 * @Date 2016/12/23 23:19
 */
@Component
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue testQueue;

    @Autowired
    private Topic testTopic;

    @Autowired
    private Queue testQueueObj;

    /**
     * 消息的持久化和优先级设置，还有延时发送设置
     * @param msg
     * @param persistentType
     * @param priority
     */
    public void sendQueueTextJms(String msg, int persistentType, int priority)  {
        QosSettings qosSettings = new QosSettings(  );
        qosSettings.setDeliveryMode( persistentType );
        qosSettings.setPriority( priority );
        jmsTemplate.setQosSettings( qosSettings );
        jmsTemplate.convertAndSend( this.testQueue, msg);
    }

    public void sendQueueText(String msg)  {
        this.jmsMessagingTemplate.convertAndSend(this.testQueue, msg);
    }

    public void sendTopicText(String msg)  {
        this.jmsMessagingTemplate.convertAndSend(this.testTopic, msg);
    }

    public void sendQueueObj(MessageDto msg)  {
        this.jmsMessagingTemplate.convertAndSend(this.testQueueObj, ProtoStuffSerializerUtil.serialize(msg));
    }

    /**
     * 间隔时间定投(间隔3秒)
     */
/*    @Scheduled(cron = " 0/3 * * * * ? ")
    public void scheduled(){
        this.jmsMessagingTemplate.convertAndSend(this.testQueue, "ni hao tangyu" +new Date() );
    }*/
}

