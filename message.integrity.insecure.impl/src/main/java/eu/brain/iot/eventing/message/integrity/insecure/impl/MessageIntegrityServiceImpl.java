package eu.brain.iot.eventing.message.integrity.insecure.impl;

import static eu.brain.iot.eventing.message.integrity.api.ValidationResult.ILLEGAL_SIGNATURE;
import static eu.brain.iot.eventing.message.integrity.api.ValidationResult.INVALID;
import static eu.brain.iot.eventing.message.integrity.api.ValidationResult.MISSING;
import static eu.brain.iot.eventing.message.integrity.api.ValidationResult.VALID;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import eu.brain.iot.eventing.message.integrity.api.MessageIntegrityService;
import eu.brain.iot.eventing.message.integrity.api.ValidationResult;

@Component
public class MessageIntegrityServiceImpl implements MessageIntegrityService {

	@Activate
	void start() {
		
	}
	
	private <R> R doWithDigest(Function<MessageDigest, R> action) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			return action.apply(digest);
		} catch (NoSuchAlgorithmException e) {
					throw new RuntimeException("Unable to create the message digest", e);
		}
	}
	
	private byte[] getValueBytes(Object value) {
		
		if(value == null) {
			return new byte[0];
		} else {
			return getBytes(value.toString());
		}
	}
	
	private byte[] getBytes(String s) {
		return s.getBytes(UTF_8);
	}
	
	@Override
	public byte[] generateSecurityToken(Map<String, Object> event) {
		
		return doWithDigest(digest ->  {
				event.entrySet().stream()
					.filter(e -> !e.getKey().equals("securityToken"))
					.sorted((a,b) -> a.getKey().compareTo(b.getKey()))
					.forEach(e -> {
						digest.update(getBytes(e.getKey()));
						digest.update(getValueBytes(e.getValue()));
						
					});
			
				return digest.digest();	
			});
	}

	@Override
	public ValidationResult validateEvent(Map<String, Object> event) {
		Object token = event.get("securityToken");
		
		if (token == null) {
			return MISSING;
		}
		
		if(token instanceof byte[]) {
			return Arrays.equals((byte[]) token, generateSecurityToken(event)) ? VALID : INVALID; 
		}
		return ILLEGAL_SIGNATURE;
	}

}
