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

/**
 * The {@link cardio2eConfiguration} class contains fields mapping thing configuration parameters.
 *
 * @author Manuel Alberto Guerrero Díaz - Initial contribution
 * @author Fernando A. P. Gomes - OH2 and OH3 port
 */
public class Cardio2eConfiguration {

    public String serialPort = "/dev/ttyUSB0"; // serial port where the device is connected
    // maybe modify to string?!?
    public String programCode = "00000"; // Installer code (optional, installer program code for login; by default '00000')
    public int minDelayBetweenReceivingAndSending = 200; // Minimum delay between receiving and sending (optional, for expert tunning only; by default '200' milliseconds tested safe value)
    public int minDelayBetweenSendings = 300; // Minimum delay between sendings (optional, for expert tunning only; by default '300' milliseconds tested safe value)
    public boolean filterUnnecessaryCommand = false; // Filter unnecessary command (optional; by default 'false')
    public boolean filterUnnecessaryReverseModeUpdate = false; // Filter unnecessary reverse mode update (optional; by default 'true')
    public String smartSendingEnabledObjectTypes = "LIGHTING,RELAY,HVAC_CONTROL,DATE_AND_TIME,SCENARIO,SECURITY,ZONES_BYPASS,CURTAIN"; // Smart sending enabled object types (optional; valid values are 'LIGHTING', 'RELAY', 'HVAC_CONTROL', 'DATE_AND_TIME', 'SCENARIO', 'SECURITY', 'ZONES_BYPASS' and 'CURTAIN')
    
    public boolean zones = false; // enables alarm zones state detection; by default 'false' for minimum use of resources
    public int zoneUnchangedMinRefreshDelay = 600000; // minimum delay for zone detection refresh when no state changes succeed; by default '600000' milliseconds = 10 minutes
    
    public int datetimeMaxOffset = 15; // maximum offset allowed in minutes for progressive date and time state update; '0' value will remove offset limit
    public boolean firstUpdateWillSetDatetime = false; // first update will set date and time
    public int allowedDatetimeUpdateHour = -1; // Allowed date and time update hour (optional, allows date and time updates on specified hour only; valid values are from '0' to '23'; '-1' value disables hour restriction; by default '-1')
    
    public int securityCode = 0; // cardio2e security code for arm / disarm alarm
}
