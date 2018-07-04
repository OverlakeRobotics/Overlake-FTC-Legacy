package org.firstinspires.ftc.teamcode;

public class TestBase {

    public TestBase(String className) {
        System.out.println("--- " + className + " ---");
    }

    public void logTestName(String name) {
        System.out.println("\t" + name );
    }

    public void logTestCase(Object expected, Object actual) {
        System.out.println("\tExpected: \"" + expected.toString() + "\"\t Actual: \"" + actual.toString() + "\"\n");
    }
}
