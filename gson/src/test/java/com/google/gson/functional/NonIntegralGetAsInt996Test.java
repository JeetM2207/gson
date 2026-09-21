package com.google.gson.functional;

import static org.junit.Assert.assertThrows;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Test;

// https://github.com/google/gson/issues/996: JsonElement.getAsInt() on a non-integral
// value like 6.7 silently truncates to 6 instead of throwing, because
// LazilyParsedNumber's int conversion goes through BigDecimal.intValue() (which
// truncates) rather than a form that rejects a non-integral value.
public class NonIntegralGetAsInt996Test {
  @Test
  public void testGetAsIntRejectsNonIntegralValue() {
    JsonElement elem = JsonParser.parseString("6.7");
    assertThrows(RuntimeException.class, elem::getAsInt);
  }
}
