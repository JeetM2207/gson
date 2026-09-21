package com.google.gson.functional;

import static org.junit.Assert.assertThrows;

import com.google.gson.Gson;
import org.junit.Test;

// https://github.com/google/gson/issues/1727: gson.fromJson(json, Long.class) on a value
// one past Long.MAX_VALUE silently clamps to Long.MAX_VALUE instead of throwing, even
// though JsonPrimitive.getAsLong() and Long.valueOf() both correctly throw
// NumberFormatException for the identical input.
public class LongOverflowFromJson1727Test {
  @Test
  public void testFromJsonRejectsLongOverflow() {
    Gson gson = new Gson();
    assertThrows(RuntimeException.class, () -> gson.fromJson("9223372036854775808", Long.class));
  }
}
