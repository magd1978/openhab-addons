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
package org.openhab.binding.cardio2e.internal.handler;

import java.io.IOException;

import static org.openhab.binding.cardio2e.internal.Cardio2eBindingConstants.*;

import org.openhab.binding.cardio2e.internal.Cardio2eConfiguration;

import org.openhab.binding.cardio2e.internal.connector.Cardio2eSerialConnector;
import org.openhab.binding.cardio2e.internal.connector.Cardio2eConnectionEvent;
import org.openhab.binding.cardio2e.internal.connector.Cardio2eReceivedDataEvent;

import org.openhab.binding.cardio2e.internal.code.Cardio2eDecoder;

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

/**
 * The {@link cardio2eHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Manuel Alberto Guerrero Díaz - Initial contribution
 * @author Fernando A. P. Gomes - OH2 and OH3 port
 * 
 */
@NonNullByDefault
public class Cardio2eHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(Cardio2eHandler.class);

    private @Nullable Cardio2eConfiguration config;
    private @Nullable Cardio2eSerialConnector connector;
    private @Nullable Cardio2eDecoder decoder;
    private @Nullable ReceivedDataListener receivedDataListener;
    private @Nullable DecodedTransactionListener decodedTransactionListener;


    public Cardio2eHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        logger.debug("Cardio2e start initializing!");
        config = getConfigAs(Cardio2eConfiguration.class);

        updateStatus(ThingStatus.UNKNOWN);

        connector = new Cardio2eSerialConnector();
        decoder = connector.decoder;
        receivedDataListener = new ReceivedDataListener();
        decodedTransactionListener = new DecodedTransactionListener();
        connector.addReceivedDataListener(receivedDataListener);
        decoder.addDecodedTransactionListener(decodedTransactionListener);
        decoder.decodeZonesStateTransaction = config.zones;
        connector.setSerialPort(config.serialPort);
        if (config.minDelayBetweenReceivingAndSending > 0)
            connector.setMinDelayBetweenReceivingAndSending(config.minDelayBetweenReceivingAndSending);
        if (config.minDelayBetweenSendings > 0)
            connector.setMinDelayBetweenSendings(config.minDelayBetweenSendings);

        try {
            connector.sendTransaction(new Cardio2eLoginTransaction(config.programCode)); // Login request
        } catch (Exception ex) {
            logger.warn("Failed to send login request: '{}'", ex.toString());
        }
    
        if (config.testMode)
            loggedIn = true;
        
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

        logger.debug("Finished initializing!");

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("Handle command '{}' for {}", command, channelUID);

        switch (channelUID.getId()) {
            case CHANNEL_LOGIN:
                //loggedIn = true;
                logger.info("Cardio login succeed.");
                break;
            case CHANNEL_LIGHTING:
                logger.debug("LIGHTING channel received");
                break;          
            default:
                //logger.debug("No actions defined for received {} type transactions", transaction.getTransactionType());
        }
    }
}
