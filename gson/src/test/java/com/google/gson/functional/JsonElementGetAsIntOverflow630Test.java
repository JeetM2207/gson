package com.google.gson.functional;

import static org.junit.Assert.assertThrows;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;

// https://github.com/google/gson/issues/630: JsonElement.getAsInt() on a value that
// overflows int range (e.g. from exponential notation) silently returns a truncated/
// wrong value instead of throwing.
public class JsonElementGetAsIntOverflow630Test {
  @Test
  public void testGetAsIntThrowsOnOutOfRangeExponentialValue() {
    JsonObject jsonObj = JsonParser.parseString("{\"id\":1e50}").getAsJsonObject();
    JsonElement idElement = jsonObj.get("id");
    assertThrows(RuntimeException.class, idElement::getAsInt);
  }
}
