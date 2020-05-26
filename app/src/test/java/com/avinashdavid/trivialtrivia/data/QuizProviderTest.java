package com.avinashdavid.trivialtrivia.data;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.avinashdavid.trivialtrivia.Utility;
import com.avinashdavid.trivialtrivia.data.QuizDBContract;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.fakes.RoboCursor;

import java.util.ArrayList;
import java.util.Arrays;

import static com.avinashdavid.trivialtrivia.statistics.OverallStatisticsCalculator.AVERAGE_PERCENTAGE_SCORE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@PrepareForTest(Utility.class) // allow mocking static methods in Utility class
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@RunWith(RobolectricTestRunner.class)

public class QuizProviderTest {

    @Mock
    Context mockContext;

    public QuizProvider qp;

    @Before
    public void setUp(){
        qp = new QuizProvider();
        mockContext = mock(Context.class);
        //when(qp.onCreate()).thenReturn(true);
    }

    @Test
    public void QuizProviderOnCreateShouldPass(){
        // Arrange
        // Act
        //when(qp.onCreate()).thenReturn(true);
        //Boolean actual = qp.onCreate();
        // Assert
        //assertEquals(true, actual);
    }

    @Test
    public void QuizProviderInsertWithNullValues(){
        // Arrange
        // Act
        // Assert
    }

    @Test
    public void QuizProviderInsertWithNullURI(){
        // Arrange
        // Act
        // Assert
    }

    @Test
    public void QuizProviderDelete(){
        // Arrange
        // Act
        int actual = qp.delete(null, null, null);
        // Assert
        assertEquals(0, actual);
    }

    @Test
    public void QuizProviderGetTypeQuizAll(){
        // Arrange
        // Act
        // Assert
    }

    @Test
    public void QuizProviderGetTypeQuizID(){
        // Arrange
        // Act
        // Assert
    }

    @Test
    public void QuizProviderGetTypeCategoryAll(){
        // Arrange
        // Act
        // Assert
    }

    @Test
    public void QuizProviderGetTypeCategoryID(){
        // Arrange
        // Act
        // Assert
    }

    @Test
    public void QuizProviderGetTypeCategoryName(){
        // Arrange
        // Act
        // Assert
    }

    @Test (expected = NullPointerException.class)
    public void QuizProviderGetTypeDefaultShouldThrow(){
        // Arrange
        // Act
        String actual = qp.getType(null);
        // Assert
    }
}