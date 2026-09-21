package com.google.gson.functional;

import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

// https://github.com/google/gson/issues/1920: when an enum with a @SerializedName is
// used as a Map key, serialization ignores @SerializedName (uses enum.toString() via
// String.valueOf() instead) and deserialization then can't map the @SerializedName-based
// JSON key back to the enum, producing a null key.
public class EnumMapKeySerializedName1920Test {
  enum RoomIdentifier {
    @SerializedName("MARKER_NAME")
    ROOM_NAME
  }

  @Test
  public void testMapKeySerializationRespectsSerializedNameAndRoundTrips() {
    Gson gson = new Gson();
    Map<RoomIdentifier, String> slots = new HashMap<>();
    slots.put(RoomIdentifier.ROOM_NAME, "ROOM_NAME_TEST");

    String serialized = gson.toJson(slots);
    assertEquals("{\"MARKER_NAME\":\"ROOM_NAME_TEST\"}", serialized);

    Type type = new TypeToken<Map<RoomIdentifier, String>>() {}.getType();
    Map<RoomIdentifier, String> deserialized = gson.fromJson(serialized, type);
    assertEquals(RoomIdentifier.ROOM_NAME, deserialized.keySet().iterator().next());
  }
}
