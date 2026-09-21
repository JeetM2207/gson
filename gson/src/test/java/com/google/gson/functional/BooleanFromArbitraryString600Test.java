package com.google.gson.functional;

import static org.junit.Assert.assertThrows;

import com.google.gson.Gson;
import org.junit.Test;

// https://github.com/google/gson/issues/600: deserializing an arbitrary JSON string
// (e.g. "ABC") into a boolean field raises no error and silently sets the field false,
// while a JSON number in the same position correctly raises an error.
public class BooleanFromArbitraryString600Test {
  public static class HasBoolean {
    public boolean isPRA;
  }

  @Test
  public void testBooleanFieldRejectsNonBooleanString() {
    assertThrows(RuntimeException.class, () ->
        new Gson().fromJson("{\"isPRA\":\"ABC\"}", HasBoolean.class));
  }
}
