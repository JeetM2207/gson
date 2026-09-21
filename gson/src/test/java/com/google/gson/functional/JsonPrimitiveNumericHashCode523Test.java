package com.google.gson.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.gson.JsonPrimitive;
import org.junit.Test;

// https://github.com/google/gson/issues/523: JsonPrimitive.equals() considers a
// JsonPrimitive(1) (int) equal to JsonPrimitive(1d) (double), but their hashCode()s
// differ -- violating the hashCode/equals contract.
public class JsonPrimitiveNumericHashCode523Test {
  @Test
  public void testEqualNumericPrimitivesHaveSameHashCode() {
    JsonPrimitive p1 = new JsonPrimitive(1);
    JsonPrimitive p2 = new JsonPrimitive(1d);
    assertTrue(p1.equals(p2));
    assertEquals(p1.hashCode(), p2.hashCode());
  }
}
