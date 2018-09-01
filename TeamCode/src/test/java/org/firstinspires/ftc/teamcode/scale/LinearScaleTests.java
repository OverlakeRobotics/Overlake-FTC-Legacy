package org.firstinspires.ftc.teamcode.scale;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by EvanCoulson on 9/1/18.
 */

public class LinearScaleTests
{
    @Test
    public void ScaleX_Xis5_Returns10() {
        IScale scale = new LinearScale(2, 0);
        Assert.assertEquals(10d, scale.scaleX(5), 0.0000001);
    }

    @Test
    public void ScaleY_Yis10_Returns5() {
        IScale scale = new LinearScale(2, 0);
        Assert.assertEquals(5d, scale.scaleY(10), 0.0000001);
    }
}
