/*******************************************************************************
 * Copyright (C) 2021 Paremus
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package eu.brain.iot.eventing.message.integrity.insecure.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import eu.brain.iot.eventing.message.integrity.api.ValidationResult;

public class MessageIntegrityServiceImplTest {

	private MessageIntegrityServiceImpl impl;

	@Before
	public void before() {
		impl = new MessageIntegrityServiceImpl();
		impl.start();
	}
	
	
	@Test
	public void testGenerateSecurityToken() {
		Map<String, Object> fooMap = new HashMap<>();
		fooMap.put("foo", "bar");
		
		assertNotNull(impl.generateSecurityToken(fooMap));
	}

	@Test
	public void testValidateEvent() {
		Map<String, Object> fooMap = new HashMap<>();
		fooMap.put("foo", "bar");
		
		fooMap.put("securityToken", impl.generateSecurityToken(fooMap));
		
		assertEquals(ValidationResult.VALID, impl.validateEvent(fooMap));
	}

	@Test
	public void testValidateEventMissingSig() {
		Map<String, Object> fooMap = new HashMap<>();
		fooMap.put("foo", "bar");
		
		assertEquals(ValidationResult.MISSING, impl.validateEvent(fooMap));
	}

	@Test
	public void testValidateEventWrongSigType() {
		Map<String, Object> fooMap = new HashMap<>();
		fooMap.put("foo", "bar");
		fooMap.put("securityToken", "foobar");
		
		assertEquals(ValidationResult.ILLEGAL_SIGNATURE, impl.validateEvent(fooMap));
	}

	@Test
	public void testValidateModifiedEvent() {
		Map<String, Object> fooMap = new HashMap<>();
		fooMap.put("foo", "bar");
		
		fooMap.put("securityToken", impl.generateSecurityToken(fooMap));
		fooMap.put("foo", "foobar");
		
		assertEquals(ValidationResult.INVALID, impl.validateEvent(fooMap));
	}
	
}
