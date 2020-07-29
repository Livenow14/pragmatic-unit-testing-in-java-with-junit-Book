import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ScoreCollectionTest {

    /**
     * 4.1 AAA 관용어를 이해하면 따로 명시할 필요가 없다.
     *
     * 준비(Arrange): 테스트 코드를 실행하기 전에 시스템이 적절한 상태에 있는지 확인. 객체들을
     * 생성하거나 이것과 의사소통하거나 다른 API를 호출 하는 것
     *
     * 실행(Act): 테스트 코드를 실행함, 보통은 단일 메서드 호출
     *
     * 단언(Assert): 실행한 코드가 기대한 대로 동작하는지 확인. 실행한 코드의 반환값 혹은
     * 그 외에 필요한 객체들의 새로운 상태를 검사.
     *
     * 빈줄로 테스트의 각 부분을 구별
     */
    @Test
    public void answersArithmeticMeanOfTwoNumbers(){
        ScoreCollection collection = new ScoreCollection();
        collection.add(()->5);
        collection.add(()->7);

        int acutualResult = collection.arithmeticMean();

        assertThat(acutualResult).isEqualTo(12);
    }

    /**
     * 4.2 동작 테스트 vs 메서드 테스트
     * 테스트를 작성할 때 클래스 동작에 집중해야 하며 개별 메서드를 테스트 하면 안된다.
     * 단위 테스트를 시작할 때는 먼저 전체적인 시각에서 시작해야함.
     */
    /**
     * 4.3 태스트와 프로덕션 코드의 관계
     */
}