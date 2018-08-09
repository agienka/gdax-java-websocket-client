package com.missd.gdaxjavawebsocketclient.message;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;

import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.MESSAGE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.REASON;

public final class ErrorMessage {
    private final String errorMessage;
    private final String reason;

    private ErrorMessage(String errorMessage, String reason) {
        this.errorMessage = errorMessage;
        this.reason = reason;
    }

    public static ErrorMessage from(Map<String, Object> errorAsMap) {
        return new ErrorMessage(String.valueOf(errorAsMap.get(MESSAGE)),
                String.valueOf(errorAsMap.get(REASON)));
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErrorMessage{");
        sb.append("errorMessage='").append(errorMessage).append('\'');
        sb.append(", reason='").append(reason).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ErrorMessage)) return false;

        ErrorMessage that = (ErrorMessage) o;

        return new EqualsBuilder()
                .append(errorMessage, that.errorMessage)
                .append(reason, that.reason)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(errorMessage)
                .append(reason)
                .toHashCode();
    }
}
