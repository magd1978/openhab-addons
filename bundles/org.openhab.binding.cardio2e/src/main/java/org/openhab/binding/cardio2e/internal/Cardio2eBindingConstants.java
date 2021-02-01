/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
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
package org.openhab.binding.cardio2e.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link cardio2eBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Fernando A. P. Gomes - Initial contribution
 */
@NonNullByDefault
public class Cardio2eBindingConstants {

    private static final String BINDING_ID = "cardio2e";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_CARDIO2E = new ThingTypeUID(BINDING_ID, "cardio2e");

    // List of all Channel ids
    // for now we don't have channels
    public static final String CHANNEL_LIGHTING = "LIGHTING";
    public static final String CHANNEL_RELAY = "RELAY";
    public static final String CHANNEL_HVAC_CONTROL = "HVAC_CONTROL";
    public static final String CHANNEL_DATE_AND_TIME = "DATE_AND_TIME";
    public static final String CHANNEL_SCENARIO = "SCENARIO";
    public static final String CHANNEL_SECURITY = "SECURITY";
    public static final String CHANNEL_ZONES_BYPASS = "ZONES_BYPASS";
    public static final String CHANNEL_CURTAIN = "CURTAIN";
    public static final String CHANNEL_INFORMATION = "INFORMATION";
    public static final String CHANNEL_LOGIN = "LOGIN";
}
