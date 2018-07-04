package org.firstinspires.ftc.teamcode.robot.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImpl;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.TestBase;
import org.firstinspires.ftc.teamcode.hardware.dcmotors.MotorType;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GearedMotorTest extends TestBase {
    public GearedMotorTest() {
        super("GearedMotorTest");
    }

    @Rule
    public TestName name = new TestName();

    @Test
    public void GearedMotor_MotorIsBusy_ReturnsTrue() {
        logTestName(name.getMethodName());

        GearChain chain = new GearChain(MotorType.TEST,1);
        DcMotor motorMock = mock(DcMotorImpl.class);
        when(motorMock.isBusy()).thenReturn(true);

        GearedMotor motor = new GearedMotor(chain, motorMock);
        logTestCase(true, motor.isBusy());

        assertTrue(motor.isBusy());
    }

    @Test
    public void GearedMotor_setDirection_HasValueOfForward() {
        logTestName(name.getMethodName());

        GearChain chain = new GearChain(MotorType.TEST,1);
        final DcMotor motorMock = mock(DcMotorImpl.class);
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] arguments = invocation.getArguments();
                DcMotorSimple.Direction direction = (DcMotorSimple.Direction)(arguments[0]);
                when(motorMock.getDirection()).thenReturn(direction);
                return null;
            }
        }).when(motorMock).setDirection(any(DcMotorSimple.Direction.class));

        GearedMotor motor = new GearedMotor(chain, motorMock);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        System.out.println(motorMock.getDirection());
        logTestCase(DcMotorSimple.Direction.FORWARD, motor.getDirection());

        assertEquals(DcMotorSimple.Direction.FORWARD, motor.getDirection());
    }

    @Test
    public void GearedMotor_runOutputGearTicks_() {

    }

}