package com.avinashdavid.trivialtrivia.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.mock.MockContentResolver;

import com.avinashdavid.trivialtrivia.Utility;
import com.avinashdavid.trivialtrivia.data.QuizDBContract;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.fakes.RoboCursor;

import java.util.ArrayList;
import java.util.Arrays;

import static com.avinashdavid.trivialtrivia.statistics.OverallStatisticsCalculator.AVERAGE_PERCENTAGE_SCORE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// allow mocking static methods in QuizDBHelper class
@PrepareForTest(fullyQualifiedNames = "com.avinashdavid.trivialtrivia.data.QuizDBHelper")
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@RunWith(RobolectricTestRunner.class)
public class QuizProviderTest {
    private static final String sCategoryIdSelection = QuizDBContract.CategoryEntry.TABLE_NAME + "." + QuizDBContract.CategoryEntry._ID + "=?";
    private static final String sCategoryNameSelection = QuizDBContract.CategoryEntry.TABLE_NAME + "." + QuizDBContract.CategoryEntry.COLUMN_NAME_NAME + "=?";

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private QuizDBHelper quizDBHelper;

    @Mock
    private SQLiteDatabase quizDb;

    @Mock
    Context mockContext;

    @Mock
    MockContentResolver mockContentResolver;

    public QuizProvider qp;

    @Before
    public void setUp() throws Exception
    {
        qp = spy(QuizProvider.class); // Use spy() to allow mocking only getContext()
        when(qp.getContext()).thenReturn(mockContext);
        when(mockContext.getContentResolver()).thenReturn(mockContentResolver);

        PowerMockito.spy(QuizDBHelper.class); // Partial mock for QuizDBHelper class
        PowerMockito.doReturn(quizDBHelper).when(QuizDBHelper.class, "getInstance", any());
        when(quizDBHelper.getReadableDatabase()).thenReturn(quizDb);
        when(quizDBHelper.getWritableDatabase()).thenReturn(quizDb);

        // Call onCreate() to initialize the QuizProvider
        qp.onCreate();
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
    public void QuizProviderUpdateWithDefaultURI() {
        // Arrange
        String[] selArgs = {"a", "b", "c"};
        // Act
        int actual = qp.update(mock(Uri.class), mock(ContentValues.class), " ", selArgs);
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

        // Return any value other than zero so we know QuizProvider didn't return zero without calling quiz DB
        when(quizDb.update(anyString(), any(ContentValues.class), anyString(), any(String[].class))).thenReturn(1);

        // Act
        int actual = qp.update(uri, cv, sel, selArgs);

        // Assert
        // Check that zero was not returned
        assertEquals(1, actual);

        // Check that database update was called with the parameters we intended
        verify(quizDb).update(eq(QuizDBContract.CategoryEntry.TABLE_NAME), eq(cv), eq(sCategoryIdSelection),
                Mockito.argThat(new ArgumentMatcher<String[]>()
        {
            @Override
            public boolean matches(String[] argument)
            {
                return argument.length == 1 && argument[0].equals(Integer.toString(QuizProvider.CATEGORY_ID));
            }
        }));

        // Check that notify change was called
        verify(mockContentResolver).notifyChange(uri, null);
    }

    @Test
    public void QuizProviderUpdateWithCategoryNameURI(){
        // Arrange
        String sel = "a";
        String[] selArgs = {"a", "b", "c"};
        Uri uri = Uri.parse("content://com.avinashdavid.trivialtrivia.data/category/categoryName/general");
        ContentValues cv = mock(ContentValues.class);

        // Return any value other than zero so we know QuizProvider didn't return zero without calling quiz DB
        when(quizDb.update(anyString(), any(ContentValues.class), anyString(), any(String[].class))).thenReturn(1);

        // Act
        int actual = qp.update(uri, cv, sel, selArgs);

        // Assert
        // Check that zero was not returned
        assertEquals(1, actual);

        // Check that database update was called with the parameters we intended
        verify(quizDb).update(eq(QuizDBContract.CategoryEntry.TABLE_NAME), eq(cv), eq(sCategoryNameSelection),
                Mockito.argThat(new ArgumentMatcher<String[]>()
                {
                    @Override
                    public boolean matches(String[] argument)
                    {
                        return argument.length == 1 && argument[0].equals("general");
                    }
                }));

        // Check that notify change was called
        verify(mockContentResolver).notifyChange(uri, null);
    }

    @Test
    public void QuizProviderInsertWithNullContentValues(){
        // Arrange
        ContentValues cv = null;
        // Act
        Uri actual = qp.insert(mock(Uri.class), cv);
        // Assert
        assertNull(actual);
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
        Uri uri = Uri.parse("content://com.avinashdavid.trivialtrivia.data/quiz");
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
        Uri uri = QuizDBContract.CategoryEntry.buildUriCategoryName(String.valueOf(QuizProvider.CATEGORY_NAME));
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
    public void QuizProviderGetTypeNullShouldHandle(){
        // Arrange
        // Act
        String actual = qp.getType(null);
        // Assert - it should throw exception so no need to assert
    }
}