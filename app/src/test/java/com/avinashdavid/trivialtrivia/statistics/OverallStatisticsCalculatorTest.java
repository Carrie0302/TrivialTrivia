package com.avinashdavid.trivialtrivia.statistics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class OverallStatisticsCalculatorTest
{
    @Test
    public void getNumberOfPerfectQuizzesWithNullScoreListThrows()
    {
        int[] quizsizelist = new int[]{2};
        try
        {
            OverallStatisticsCalculator.getNumberOfPerfectQuizzes(null, quizsizelist);
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(true);
        }
        fail("Expected IllegalArgumentException");
    }

    @Test
    public void getNumberOfPerfectQuizzesWithNullQuizSizeListThrows()
    {
        int[] scorelist = new int[]{7, 3};
        try
        {
            OverallStatisticsCalculator.getNumberOfPerfectQuizzes(scorelist, null);
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(true);
        }
        fail("Expected IllegalArgumentException");
    }

    @Test
    public void getNumberOfPerfectQuizzesWithNotEqualInputLists()
    {
        int[] scorelist = new int[]{3, 7};
        int[] quizsizelist = new int[]{2};
        double actual = OverallStatisticsCalculator.getNumberOfPerfectQuizzes(scorelist, quizsizelist);
        double expected = -1;
        assertEquals(expected, actual, 0.0001);
    }
}