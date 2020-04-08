/* Copyright 2020 Paremus, Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package eu.brain.iot.eventing.message.integrity.api;

public enum ValidationResult {
	
	// TODO - other states e.g. expired token, expired certificate, untrusted certificate?
	VALID, INVALID, MISSING, ILLEGAL_SIGNATURE;

}
