package org.prgms;

import org.junit.Test;
import org.prgms.user.User;
import org.prgms.voucher.FixedAmountVoucher;
import org.prgms.voucher.Voucher;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.Objects;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test(){
        double a = 10 / 100.0;
        double b = 1 - a;
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void serializeTest() throws IOException {
        Voucher voucher = new FixedAmountVoucher(10L, UUID.randomUUID());
        String filename = String.format("./objects/%s.obj", voucher.hashCode());
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        System.out.println(filename);
        oos.writeObject(voucher);
    }

    @Test
    public void loadTest() throws IOException, ClassNotFoundException {
        File folder = new File("./objects");
        File[] list = folder.listFiles();
        String pattern = "*.obj";
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        for (File filename : Objects.requireNonNull(list)) {
            if (matcher.matches(filename.toPath().getFileName())) {
                FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object obj = ois.readObject();
                Voucher voucher = (Voucher) obj;
                System.out.println(voucher.getVoucherId());
            }
        }
    }

    @Test
    public void userTest() {
        User user = new User("근오", "남", 30);
    }
}
