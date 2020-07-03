import static org.assertj.core.api.Assertions.assertThat;
import java.io.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AssertTest {

   class InsufficientFundsException extends RuntimeException {
      public InsufficientFundsException(String message) {
         super(message);
      }

      private static final long serialVersionUID = 1L;
   }

   class Account {
      int balance;
      String name;

      Account(String name) {
         this.name = name;
      }

      void deposit(int dollars) {
         balance += dollars;
      }

      void withdraw(int dollars) {
         if (balance < dollars) {
            throw new InsufficientFundsException("balance only " + balance);
         }
         balance -= dollars;
      }

      public String getName() {
         return name;
      }

      public int getBalance() {
         return balance;
      }

      public boolean hasPositiveBalance() {
         return balance > 0;
      }
   }

   class Customer {
      List<Account> accounts = new ArrayList<>();

      void add(Account account) {
         accounts.add(account);
      }

      Iterator<Account> getAccounts() {
         return accounts.iterator();
      }
   }

   private Account account;

   @BeforeEach
   public void createAccount() {
      account = new Account("an account name");
   }


   @Test
   public void hasPositiveBalance() {
      account.deposit(50);
      assertTrue(account.hasPositiveBalance());
   }


   @Test
   public void depositIncreasesBalance() {
      int initialBalance = account.getBalance();
      account.deposit(100);
      assertTrue(account.getBalance() > initialBalance);
      assertThat(account.getBalance()).isEqualTo(100);
   }


   @Test
   public void depositIncreasesBalance_hamcrestAssertTrue() {
      account.deposit(50);
      assertThat(account.getBalance() > 0).isEqualTo(true);
   }

   @DisplayName(value = "배열 실패 테스트")
   @Test
   public void comparesArraysFailing() {
      assertThat(new String[]{"a", "b", "c"}).isEqualTo(new String[]{"a", "b"});
   }

   @Test
   @DisplayName(value = "컬렉션 실패 테스트")
   public void comparesCollectionsFailing(){
      assertThat(Arrays.asList(new String[] {"a"})).isEqualTo(Arrays.asList(new String[] {"a", "ab"}));
   }

   @Test
   @Ignore
   public void assertFailure() {
      Account account1 = new Account("xxx");
      assertTrue(account.getName().startsWith("xyz"));
   }

   @Test
   @Ignore
   public void notEqualToTest(){
      assertThat(account.getName()).isNotEqualTo("hihi");
   }

   @Test
   @Ignore
   public void NullTest(){
      assertThat(account.getName()).isNotNull();      //실제로 유용하진 않음.
      assertThat(account.getName()).isNull();
      assertThat(account.getName()).isEqualTo("hihi");      //이렇게 하는게 더 유용함.
   }

   /**
    * 메세지를 넣는 것은 오히려 가독성을 떨어뜨림.
    */
   @Ignore
   @Test
   public void testWithWorthlessAssertionComment() {
         account.deposit(50);
         assertThat(account.getBalance()).isEqualTo(50);
   }

   /**
    * 예외를 기대하는 방법
    * contain보단 hasMessage로 전문을 포함하는것이 더 좋다!
    */

   @Test
   @DisplayName(value = "예외 던져지는지 확인")
   public void throwsWhenWithdrawingTooMuch(){
      account.deposit(40);
      assertThatThrownBy(()->account.withdraw(50)).isInstanceOf(InsufficientFundsException.class)
              .hasMessage("balance only 40");
   }



   /**
    * 컬렉션에 가지고 있는지 확인
    */
   @Test
   @SuppressWarnings("unchecked")
   public void items() {
      List<String> names = new ArrayList<>();
      names.add("Moe");
      names.add("Larry");
      names.add("Curly");

      assertThat(names).contains("Moe");

   }


}
/*

   @Test
   public void variousMatcherTests() {
      Account account = new Account("my big fat acct");
      assertThat(account.getName(), is(equalTo("my big fat acct")));

      assertThat(account.getName(), allOf(startsWith("my"), endsWith("acct")));

      assertThat(account.getName(), anyOf(startsWith("my"), endsWith("loot")));

      assertThat(account.getName(), not(equalTo("plunderings")));

      assertThat(account.getName(), is(not(nullValue())));
      assertThat(account.getName(), is(notNullValue()));

      assertThat(account.getName(), isA(String.class));

      assertThat(account.getName(), is(notNullValue())); // not helpful
      assertThat(account.getName(), equalTo("my big fat acct"));
   }

   @Test
   public void sameInstance() {
      Account a = new Account("a");
      Account aPrime = new Account("a");
      // TODO why needs to be fully qualified??
      assertThat(a, not(org.hamcrest.CoreMatchers.sameInstance(aPrime)));
   }


   @Test
   @SuppressWarnings("unchecked")
   public void items() {
      List<String> names = new ArrayList<>();
      names.add("Moe");
      names.add("Larry");
      names.add("Curly");

      assertThat(names, hasItem("Curly"));

      assertThat(names, hasItems("Curly", "Moe"));

      assertThat(names, hasItem(endsWith("y")));

      assertThat(names, hasItems(endsWith("y"), startsWith("C"))); //warning!

      assertThat(names, not(everyItem(endsWith("y"))));
   }

   @Test
   @ExpectToFail @Ignore
   public void location() {
      Point point = new Point(4, 5);
      
      // WTF why do JUnit matches not include closeTo
//      assertThat(point.x, closeTo(3.6, 0.2));
//      assertThat(point.y, closeTo(5.1, 0.2));
      
      assertThat(point, isNear(3.6, 5.1));
   }
   
   @Test
   @ExpectToFail @Ignore
   public void classicAssertions() {
      Account account = new Account("acct namex");
      assertEquals("acct name", account.getName());
   }
   
   @Test(expected=InsufficientFundsException.class)
   public void throwsWhenWithdrawingTooMuch() {
      account.withdraw(100);
   }
   
   @Test
   public void throwsWhenWithdrawingTooMuchTry() {
      try {
         account.withdraw(100);
         fail();
      }
      catch (InsufficientFundsException expected) {
         assertThat(expected.getMessage(), equalTo("balance only 0"));
      }
   }
   
   @Test
   public void readsFromTestFile() throws IOException {
      String filename = "test.txt";
      BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
      writer.write("test data");
      writer.close();
      // ...
   }
   
   @After
   public void deleteForReadsFromTestFile() {
      new File("test.txt").delete();
   }
   
   @Test
   @Ignore("don't forget me!")
   public void somethingWeCannotHandleRightNow() {
      // ...
   }

   @Rule
   public ExpectedException thrown = ExpectedException.none();  
   
   @Test
   public void exceptionRule() {
      thrown.expect(InsufficientFundsException.class); 
      thrown.expectMessage("balance only 0");  
      
      account.withdraw(100);  
   }
   
   @Test
   public void doubles() {
      assertEquals(9.7, 10.0 - 0.3, 0.005);
   }
}
*/
