package org.firstinspires.ftc.teamcode;

public class TestBase {

    public void logTestName(String name) {
        System.out.println("--- " + name + " ---");
    }

    public void logTestCase(Object expected, Object actual) {
        System.out.println("Expected: \"" + expected.toString() + "\"\t Actual: \"" + actual.toString() + "\"\n");
    }
}
