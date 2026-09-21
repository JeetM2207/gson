package com.google.gson.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;
import org.junit.Test;

// https://github.com/google/gson/issues/1397: an explicit @Expose(serialize = true) on a
// transient field should take precedence over Gson's default exclusion of transient
// fields, but the transient modifier still wins and the field is dropped.
public class ExposeOverridesTransientExclusion1397Test {
  public static class HasExposedTransientField {
    @Expose(serialize = true, deserialize = true)
    transient int value = 5;
  }

  @Test
  public void testExposedTransientFieldIsSerialized() {
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(new HasExposedTransientField());
    JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
    assertTrue("expected 'value' to be present in: " + json, obj.has("value"));
    assertEquals(5, obj.get("value").getAsInt());
  }
}
