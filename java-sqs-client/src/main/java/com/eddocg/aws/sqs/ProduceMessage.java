/**
 * 
 */
package com.eddocg.aws.sqs;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;

/**
 * @author eddocg
 * @url http://eddosoa.blogspot.com/
 *
 */
public class ProduceMessage {

	private static final Logger LOG = LoggerFactory.getLogger(ProduceMessage.class);
	
	/**
	 * 
	 */
	public ProduceMessage() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(ClientInfo.accessKey, ClientInfo.secretKey);
		
		AmazonSQS sqs = AmazonSQSClientBuilder.standard()
				  .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				  .withRegion(Regions.US_EAST_2)
				  .build();
		
		produceMessage(sqs);

	}
	
	public static void produceMessage(AmazonSQS sqs) {
		Map<String, MessageAttributeValue> messageAttributes = new HashMap<String, MessageAttributeValue>();
		messageAttributes.put("AttributeOne", new MessageAttributeValue()
		  .withStringValue("This is an attribute")
		  .withDataType("String")); 
		
		messageAttributes.put("MessageGroupId", new MessageAttributeValue()
		  .withStringValue("test-id")
		  .withDataType("String"));
		
		LOG.info("Preparing msg.");
		
		SendMessageRequest sendMessageStandardQueue = new SendMessageRequest()
				  .withQueueUrl(ClientInfo.queueUrl)
				  .withMessageBody("{\"message\": \"test msg\"}")
				  .withMessageGroupId("test-msg")
				  .withMessageAttributes(messageAttributes);
				 
		sqs.sendMessage(sendMessageStandardQueue);
	}

}
