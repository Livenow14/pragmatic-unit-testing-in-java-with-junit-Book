/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
***/
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AssertHamcrestTest {
   @Test
   @DisplayName(value = "부동소수점 까지 읽히기 떄문에 테스트 실패")
   public void assertDoublesEqual() {
      assertThat(2.32 * 3).isEqualTo(6.96);
   }

   /**
    * 이것은 잘 읽히지 않는다.
    */
   @Test
   public void assertWithTolerance() {
      assertTrue(Math.abs((2.32 * 3) - 6.96) < 0.0005);
   }
   
   @Test
   public void assertDoublesCloseTo() {
      assertThat(2.32 * 3).isCloseTo(6.96, Percentage.withPercentage(0.0005));
   }
}
