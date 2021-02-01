/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.cardio2e.internal.connector;

import java.util.EventObject;

/**
 * Cardio2e Connection Event class.
 * 
 * @author Manuel Alberto Guerrero Díaz
 * @author Fernando A. P. Gomes - OH2 and OH3 port
 */

public class Cardio2eConnectionEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private boolean isConnected;

	public Cardio2eConnectionEvent(Object source, boolean isConnected) {
		super(source);
		this.isConnected = isConnected;
	}

	public boolean getIsConnected() {
		return isConnected;
	}
}