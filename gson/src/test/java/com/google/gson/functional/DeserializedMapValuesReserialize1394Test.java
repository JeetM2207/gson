package com.google.gson.functional;

import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import java.util.Map;
import org.junit.Test;

// https://github.com/google/gson/issues/1394: a Map deserialized by Gson (backed
// internally by LinkedTreeMap) can't have its own .values() view serialized again --
// unlike keySet() and entrySet(), LinkedTreeMap doesn't override values(), so it falls
// back to AbstractMap's anonymous-inner-class view, which Gson's reflective adapter
// excludes from serialization, producing "null" instead of the actual values.
public class DeserializedMapValuesReserialize1394Test {
  public static class Data {
    Map<String, String> map;
  }

  @Test
  public void testDeserializedMapValuesCanBeSerializedAgain() {
    Gson gson = new Gson();
    Data deserialized = gson.fromJson("{\"map\":{\"key\":\"value\"}}", Data.class);
    assertEquals("[\"value\"]", gson.toJson(deserialized.map.values()));
  }
}
