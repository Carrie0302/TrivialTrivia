package com.avinashdavid.trivialtrivia.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.avinashdavid.trivialtrivia.Utility;
import com.avinashdavid.trivialtrivia.data.QuizDBContract;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
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
import static org.junit.Assert.assertNull;
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
    public void QuizProviderUpdateWithNullValues(){
        // Arrange
        ContentValues cv = null;
        String[] selArgs = {"a", "b", "c"};
        // Act
        int actual = qp.update(mock(Uri.class), cv, " ", selArgs);
        // Assert
        assertEquals(0, actual);
    }

    @Test
    public void QuizProviderUpdateWithNullURI(){
        // Arrange
        String[] selArgs = {"a", "b", "c"};
        // Act
        int actual = qp.update(null, null, " ", selArgs);
        // Assert
        assertEquals(0, actual);
    }

    @Test
    public void QuizProviderUpdateWithDefaultURI(){
        // Arrange
        String[] selArgs = {"a", "b", "c"};
        // Act
        int actual = qp.update(mock(Uri.class), null, " ", selArgs);
        // Assert
        assertEquals(0, actual);
    }

    @Test
    public void QuizProviderUpdateWithCategoryIDURI(){
        // Arrange
        String sel = "a";
        String[] selArgs = {"a", "b", "c"};
        Uri uri = QuizDBContract.CategoryEntry.buildUriCategoryId(QuizProvider.CATEGORY_ID);
        ContentValues cv = mock(ContentValues.class);
        QuizDBHelper qbh = qp.getMQuizDBHelper();
        when(qbh.getWritableDatabase()).thenReturn(mock(SQLiteDatabase.class));

        // Act
        int actual = qp.update(uri, cv, sel, selArgs);
        // Assert
        assertEquals(1, actual);
    }

    @Test
    public void QuizProviderUpdateWithCategoryNameURI(){
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

    // Test fails due to mismatch
    @Test
    public void QuizProviderGetTypeQuizAll(){
        // Arrange
        Uri uri = QuizDBContract.QuizEntry.buildUriQuizId(QuizProvider.QUIZ_ALL);
        // Act
        String actual = qp.getType(uri);
        // Assert
        assertEquals(QuizDBContract.QuizEntry.CONTENT_TYPE, actual);
    }

    @Test
    public void QuizProviderGetTypeQuizID(){
        // Arrange
        Uri uri = QuizDBContract.QuizEntry.buildUriQuizId(QuizProvider.QUIZ_ID);
        // Act
        String actual = qp.getType(uri);
        // Assert
        assertEquals(QuizDBContract.QuizEntry.CONTENT_ITEM_TYPE, actual);
    }

    // Test fails due to mismatch
    @Test
    public void QuizProviderGetTypeCategoryAll(){
        // Arrange
        Uri uri = QuizDBContract.CategoryEntry.buildUriCategoryId(QuizProvider.CATEGORY_ALL);
        // Act
        String actual = qp.getType(uri);
        // Assert
        assertEquals(QuizDBContract.QuizEntry.CONTENT_TYPE, actual);
    }

    @Test
    public void QuizProviderGetTypeCategoryID(){
        // Arrange
        Uri uri = QuizDBContract.CategoryEntry.buildUriCategoryId(QuizProvider.CATEGORY_ID);
        // Act
        String actual = qp.getType(uri);
        // Assert
        assertEquals(QuizDBContract.QuizEntry.CONTENT_ITEM_TYPE, actual);
    }

    @Test
    public void QuizProviderGetTypeCategoryName(){
        // Arrange
        Uri uri = QuizDBContract.CategoryEntry.buildUriCategoryId(QuizProvider.CATEGORY_NAME);
        // Act
        String actual = qp.getType(uri);
        // Assert
        assertEquals(QuizDBContract.QuizEntry.CONTENT_ITEM_TYPE, actual);
    }

    @Test (expected = UnsupportedOperationException.class)
    public void QuizProviderGetTypeDefaultShouldThrow(){
        // Arrange
        // Act
        String actual = qp.getType(mock(Uri.class));
        // Assert - it should throw exception so no need to assert
    }

    // Test fails due to code bug - method does not handle null input
    @Test
    public void QuizProviderGetTypeNullShouldFail(){
        // Arrange
        // Act
        String actual = qp.getType(null);
        // Assert - it should throw exception so no need to assert
    }
}