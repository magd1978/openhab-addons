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

import static org.openhab.binding.cardio2e.internal.Cardio2eBindingConstants.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openhab.binding.cardio2e.internal.com.Cardio2eCom;
import org.openhab.binding.cardio2e.internal.com.Cardio2eCom.Cardio2eComEventListener;
import org.openhab.binding.cardio2e.internal.com.Cardio2eConnectionEvent;
import org.openhab.binding.cardio2e.internal.com.Cardio2eReceivedDataEvent;
import org.openhab.binding.cardio2e.internal.code.Cardio2eCurtainTransaction;
import org.openhab.binding.cardio2e.internal.code.Cardio2eDateTimeTransaction;
import org.openhab.binding.cardio2e.internal.code.Cardio2eDecodedTransactionEvent;
import org.openhab.binding.cardio2e.internal.code.Cardio2eDecoder;
import org.openhab.binding.cardio2e.internal.code.Cardio2eDecoder.Cardio2eDecodedTransactionListener;
import org.openhab.binding.cardio2e.internal.code.Cardio2eHvacControlTransaction;
import org.openhab.binding.cardio2e.internal.code.Cardio2eHvacSystemModes;
import org.openhab.binding.cardio2e.internal.code.Cardio2eHvacTemperatureTransaction;
import org.openhab.binding.cardio2e.internal.code.Cardio2eLightingTransaction;
import org.openhab.binding.cardio2e.internal.code.Cardio2eLoginTransaction;
import org.openhab.binding.cardio2e.internal.code.Cardio2eObjectTypes;
import org.openhab.binding.cardio2e.internal.code.Cardio2eRelayTransaction;
import org.openhab.binding.cardio2e.internal.code.Cardio2eScenarioTransaction;
import org.openhab.binding.cardio2e.internal.code.Cardio2eSecurityTransaction;
import org.openhab.binding.cardio2e.internal.code.Cardio2eTransaction;
import org.openhab.binding.cardio2e.internal.code.Cardio2eTransactionTypes;
import org.openhab.binding.cardio2e.internal.code.Cardio2eZoneBypassStates;
import org.openhab.binding.cardio2e.internal.code.Cardio2eZoneStates;
import org.openhab.binding.cardio2e.internal.code.Cardio2eZonesBypassTransaction;
import org.openhab.binding.cardio2e.internal.code.Cardio2eZonesTransaction;

/**
 * The {@link cardio2eHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Manuel Alberto Guerrero Díaz - Initial contribution
 * @author Fernando A. P. Gomes - OH2 and OH3 port
 */
@NonNullByDefault
public class Cardio2eHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(Cardio2eHandler.class);

    private @Nullable Cardio2eConfiguration config;
    private @Nullable Cardio2eCom com;
    private @Nullable Cardio2eDecoder decoder;
    private @Nullable ReceivedDataListener receivedDataListener;
    private @Nullable DecodedTransactionListener decodedTransactionListener;

    public Cardio2eHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (CHANNEL_1.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {
                // TODO: handle data refresh
            }

            // TODO: handle command

            // Note: if communication with thing fails for some reason,
            // indicate that by setting the status with detail information:
            // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
            // "Could not control device at IP address x.x.x.x");
        }
    }

    @Override
    public void initialize() {
        logger.debug(" Cardio2e Start initializing!");
        config = getConfigAs(Cardio2eConfiguration.class);

        updateStatus(ThingStatus.UNKNOWN);

        com = new Cardio2eCom();
        decoder = com.decoder;
        receivedDataListener = new ReceivedDataListener();
        decodedTransactionListener = new DecodedTransactionListener();
        com.addReceivedDataListener(receivedDataListener);
        decoder.addDecodedTransactionListener(decodedTransactionListener);
        decoder.decodeZonesStateTransaction = config.zones;
        com.setSerialPort(config.serialPort);
        if (config.minDelayBetweenReceivingAndSending > 0)
            com.setMinDelayBetweenReceivingAndSending(config.minDelayBetweenReceivingAndSending);
        if (config.minDelayBetweenSendings > 0)
            com.setMinDelayBetweenSendings(config.minDelayBetweenSendings);
  
        try {
            com.sendTransaction(new Cardio2eLoginTransaction(config.programCode)); // Login
                                                                            // request
        } catch (Exception ex) {
            logger.warn("Failed to send login request: '{}'", ex.toString());
        }
  
        //if (testMode)
        //    loggedIn = true;
  
        //setProperlyConfigured(true);
  
        logger.debug("Cardio2e binding activated");
        
        scheduler.execute(() -> {
            boolean thingReachable = true; // <background task with long running initialization here>
            // when done do:
            if (thingReachable) {
                updateStatus(ThingStatus.ONLINE);
            } else {
                updateStatus(ThingStatus.OFFLINE);
            }
        });

        // logger.debug("Finished initializing!");

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }
}
