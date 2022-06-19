package me.braun.spacex.persistence.dto;

import com.google.gson.JsonObject;

public class ErrorDTO implements DTO{
    private final int status;
    private final String message;

    public ErrorDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorDTO fromJson(JsonObject json) {
        return new ErrorDTO(
                json.getAsJsonPrimitive("status").getAsInt(),
                json.getAsJsonPrimitive("message").getAsString()
        );
    }

    @Override
    public String toJson() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status", this.status);
        jsonObject.addProperty("message", this.message);
        return jsonObject.toString();
    }
}
