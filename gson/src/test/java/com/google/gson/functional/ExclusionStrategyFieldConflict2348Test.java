package com.google.gson.functional;

import static org.junit.Assert.assertEquals;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.junit.Test;

// https://github.com/google/gson/issues/2348: a deserialization-only ExclusionStrategy
// that skips a superclass field sharing a serialized name with a subclass field is
// ignored, because Gson's own conflict check only skips a field when BOTH serialize and
// deserialize are excluded (AND) instead of either (OR) -- so the "excluded" superclass
// field still collides with the subclass field and fromJson() throws.
public class ExclusionStrategyFieldConflict2348Test {
  public static class BaseClass {
    @SuppressWarnings("unused")
    @SerializedName("data")
    private Object data = null;
  }

  public static class SpecificClass extends BaseClass {
    @SerializedName("data")
    private String concreteData = null;
  }

  @Test
  public void testDeserializationExclusionStrategyAvoidsFieldConflict() {
    GsonBuilder builder = new GsonBuilder();
    builder.addDeserializationExclusionStrategy(new ExclusionStrategy() {
      @Override
      public boolean shouldSkipField(FieldAttributes f) {
        return f.getName().equals("data") && Object.class.equals(f.getDeclaringClass());
      }

      @Override
      public boolean shouldSkipClass(Class<?> clazz) {
        return false;
      }
    });
    Gson gson = builder.create();
    SpecificClass result = gson.fromJson("{\"data\":\"Test String\"}", SpecificClass.class);
    assertEquals("Test String", result.concreteData);
  }
}
