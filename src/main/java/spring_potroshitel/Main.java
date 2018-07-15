package spring_potroshitel;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("quoter.xml");
//        while (true) {
//            Thread.sleep(500);
//            Quoter terminatorQuoter = (Quoter) context.getBean("terminatorQuoter");
//            terminatorQuoter.sayQuote();
//        }
    }
}
