import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreCollectionTest {

    @Test
    public void test(){
        //given
        ScoreCollection collection = new ScoreCollection();
        collection.add(()->5);
        collection.add(()->7);

        //when
        int actualResult = collection.arithmeticMean();
        System.out.println("actualResult = " + actualResult);

        //then
        assertThat(actualResult).isEqualTo(6);
    }

}