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

public enum ValidationResult {
	
	// TODO - other states e.g. expired token, expired certificate, untrusted certificate?
	VALID, INVALID, MISSING, ILLEGAL_SIGNATURE;

}
