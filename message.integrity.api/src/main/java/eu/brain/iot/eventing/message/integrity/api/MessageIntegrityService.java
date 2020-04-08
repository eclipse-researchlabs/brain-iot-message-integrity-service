/* Copyright 2020 Paremus, Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package eu.brain.iot.eventing.message.integrity.api;

import java.util.Map;

public interface MessageIntegrityService {
	
	public byte[] generateSecurityToken(Map<String, Object> event);
	
	public ValidationResult validateEvent(Map<String, Object> event);

}
