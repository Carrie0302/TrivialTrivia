package com.avinashdavid.trivialtrivia.statistics;

import org.junit.Test;

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
    public void GetNumberOfPerfectQuizzesWithNullQuizSizeListThrows()
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
}