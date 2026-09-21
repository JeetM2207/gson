package com.google.gson.functional;

import static org.junit.Assert.assertFalse;

import com.google.gson.JsonStreamParser;
import java.io.StringReader;
import org.junit.Test;

// https://github.com/google/gson/issues/1575: JsonStreamParser(emptyString).hasNext()
// throws JsonIOException instead of returning false for genuinely empty input.
public class JsonStreamParserEmptyString1575Test {
  @Test
  public void testHasNextReturnsFalseForEmptyInputInsteadOfThrowing() {
    JsonStreamParser parser = new JsonStreamParser(new StringReader(""));
    assertFalse(parser.hasNext());
  }
}
