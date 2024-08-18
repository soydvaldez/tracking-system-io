package io.tracksystem.sensor.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class ExceptionMessages {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    
    private static final String CONNECTION_FAILER = bundle.getString("connection.failed");
    public static final String INVALID_INPUT = bundle.getString("invalid.input");
}
