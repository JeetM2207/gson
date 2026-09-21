package com.google.gson.functional;

import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import org.junit.Test;

// https://github.com/google/gson/issues/2638: a subclass that shadows (re-declares) a
// superclass field of the same name can't be serialized at all -- Gson's reflective
// adapter walks the whole class hierarchy and throws on the name collision instead of
// preferring the most-derived (shadowing) field, the way plain Java field access would.
public class SubclassOverriddenFieldSerialize2638Test {
  public static class MyClass {
    int a = 22;
  }

  public static class SubClass extends MyClass {
    @SuppressWarnings("HidingField") // the shadowed field is exactly what this bug is about
    int a = 25;
    int b = 12;
  }

  @Test
  public void testSerializesShadowedFieldFromMostDerivedClass() {
    Gson gson = new Gson();
    assertEquals("{\"a\":25,\"b\":12}", gson.toJson(new SubClass()));
  }
}
