/*******************************************************************************
 * Copyright (C) 2021 Paremus
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/

package eu.brain.iot.eventing.message.integrity.api;

import java.util.Map;

public interface MessageIntegrityService {
	
	public byte[] generateSecurityToken(Map<String, Object> event);
	
	public ValidationResult validateEvent(Map<String, Object> event);

}
