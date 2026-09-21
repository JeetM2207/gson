package com.google.gson.internal.bind.util;

import static org.junit.Assert.assertEquals;

import java.text.ParsePosition;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// https://github.com/google/gson/issues/1511: ISO8601Utils.parse() on a date-only string
// ("yyyy-MM-dd", no time/timezone component) builds the Calendar with the JVM's default
// timezone instead of UTC, so the parsed instant shifts by the local UTC offset -- the
// same input parses to a different instant depending on the system's default timezone.
public class Iso8601LocalTimezone1511Test {
  private TimeZone originalDefault;

  @Before
  public void setUp() {
    originalDefault = TimeZone.getDefault();
    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
  }

  @After
  public void tearDown() {
    TimeZone.setDefault(originalDefault);
  }

  @Test
  public void testDateOnlyParseIsTimezoneIndependent() throws java.text.ParseException {
    Date parsed = ISO8601Utils.parse("2019-03-20", new ParsePosition(0));
    assertEquals(Instant.parse("2019-03-20T00:00:00Z"), parsed.toInstant());
  }
}
