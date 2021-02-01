package org.openhab.binding.cardio2e.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openhab.binding.cardio2e.internal.com.Cardio2eConnectionEvent;
import org.openhab.binding.cardio2e.internal.com.Cardio2eReceivedDataEvent;
import org.openhab.binding.cardio2e.internal.com.Cardio2eCom.Cardio2eComEventListener;

public class ReceivedDataListener implements Cardio2eComEventListener {
    private final Logger logger = LoggerFactory.getLogger(Cardio2eHandler.class);

    public void receivedData(Cardio2eReceivedDataEvent e) {
    }

    public void isConnected(Cardio2eConnectionEvent e) {
        logger.info("Cardio is {}.", (e.getIsConnected() ? "CONNECTED"
                : "DISCONNECTED"));
    }
}