package org.firstinspires.ftc.teamcode.util.ramp;

/*
    The logarithmic ramp increases quickly for low values and slows down as it approaches the higher values.

    It is of the form g(x) = Q*log(R*x)

    Where Q and R are constants computed such that
    g(x1) = y1 and f(x2) = y2 for the given (x1, y1) and (x2, y2).

    g(x1) = y1 so:                  y1 = Q*log(R*x1)
    g(x2) = y2 so:                  y2 = Q*log(R*x2)
    Subtract to two equation:       y2 - y1 = Q*log(R*x2) - Q*log(R*x1)
    Factor out Q:                   y2 - y1 = Q*(log(R*x2) - (log(R*x1)))
    Log of product is sum of logs:  y2 - y1 = Q*((log(R) + log(x2)) - ((log(R) + log(x1))))
    Rearrange:                      y2 - y1 = Q*(log(R) - log(R) + log(x2) - log(x1))
    Reduce:                         y2 - y1 = Q*(log(x2) - log(x1))
    Combine the logs:               y2 - y1 = Q*log(x2/x1)
    Result:                         Q = (y2 - y1)/(log(x2/x1)

    Substitute the above expression for Q and (x1, y1) in g(x) = Q*log(R*x) and solve for R

    y1 = Q*log(R*x1)
    Divide both sides by Q:         y1/Q = log(R*x1)
    Exp both sides:                 exp(y1/Q) = exp(log(R*x1))
    Log and exp cancel:             exp(y1/Q) = R*x1
    Divide both sides by x1:        R = exp(y1/Q)/x1              where x1 != 0

    The inverse of y = g(x) = Q*log(R*x) is:

    Exp both side:                  exp(Q*log(R*x)) = exp(y)
    Exp of product is sum of exps:  exp(Q) + exp(log(R*x)) = exp(y)
    Exp and log cancel:             R*x = exp(y) - exp(Q)
    Divide both sides by R:         g'(y) = x = (exp(y) - exp(Q))/R
 */

public class LogarithmicRamp extends Ramp
{
    double Q;
    double R;
    double expQ;

    public LogarithmicRamp (double x1, double y1, double x2, double y2)
    {
        super(x1, y1, x2, y2);

        // x1 can't be zero... but really small is fine.
        if (x1 == 0.0)
            x1 = Double.MIN_VALUE;

        this.Q = (y2 - y1)/Math.log(x2/x1);
        this.expQ = Math.exp(Q);
        this.R = Math.exp(y1/Q)/x1;
    }

    @Override
    public double value(double x)
    {
        Double result = CheckDomain(x);
        if (result != null)
            return  result.doubleValue();

        return Q*Math.log(R*x);
    }

    /*
    The exponential ramp starts slowly at the low end of the range
    and accelerates to catch up at the high end of the range.
     */
    @Override
    public double inverse(double y)
    {
        Double result = CheckRange(y);
        if (result != null)
            return  result.doubleValue();

        return Math.exp(y/Q)/R;
    }
}
