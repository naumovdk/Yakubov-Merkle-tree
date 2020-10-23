import accumulators.Accumulator;
import accumulators.Proof;
import accumulators.User;
import accumulators.optimized.OptimizedAccumulator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(JUnit4.class)
public class FunctionalityTest {

    @Test
    public void testBasic() {
        Accumulator accumulatorForTest = new OptimizedAccumulator();
        byte[] test = {1, 2, 3, 4, 5};
        byte[] test2 = {1, 5, 3, 4, 5};
        byte[] test3 = {1, 12, 3, 4, 5};

        User user = new User();
        User user2 = new User();
        Proof proof = accumulatorForTest.add(test, user);
        // user.sendProof(proof);
        accumulatorForTest.add(test2, user2);
        //  user2.sendProof(new Proof(12));
        accumulatorForTest.add(test2, user2);
        //   user2.sendProof(new Proof(34));
        Assert.assertTrue(accumulatorForTest.verify(test, user.getProof()));
    }

    @Test
    public void testMany() {
        List<User> users = new ArrayList<>();
        Accumulator accumulatorForTest = new OptimizedAccumulator();
        List<byte[]> messages = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 15000; i++) {
            byte[] msg = new byte[500];
            random.nextBytes(msg);
            messages.add(msg);
            users.add(new User());
            accumulatorForTest.add(msg, users.get(i));
        }
        for (int i = 0; i < 15000; i++) {
            Assert.assertTrue(accumulatorForTest.verify(messages.get(i), users.get(i).getProof()));
        }
    }
}
