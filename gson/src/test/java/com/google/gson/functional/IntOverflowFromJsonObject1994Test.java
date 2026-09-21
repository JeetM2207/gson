package com.google.gson.functional;

import static org.junit.Assert.assertThrows;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Test;

// https://github.com/google/gson/issues/1994: fromJson(String, Class) throws when a JSON
// long value overflows an int field, but fromJson(JsonObject, Class) on the exact same
// oversized value silently truncates (integer overflow) instead of throwing -- the two
// entry points should behave consistently.
public class IntOverflowFromJsonObject1994Test {
  public static class HasIntField {
    public int foo;
  }

  @Test
  public void testFromJsonObjectRejectsLongOverflowingInt() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("foo", 10000000000L);
    assertThrows(RuntimeException.class, () -> new Gson().fromJson(jsonObject, HasIntField.class));
  }
}
