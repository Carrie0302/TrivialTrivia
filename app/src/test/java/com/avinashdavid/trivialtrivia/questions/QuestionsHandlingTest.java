package com.avinashdavid.trivialtrivia.questions;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.test.mock.MockContext;

import com.avinashdavid.trivialtrivia.data.QuizDBContract;
import com.avinashdavid.trivialtrivia.questions.IndividualQuestion;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@RunWith(RobolectricTestRunner.class)
public class QuestionsHandlingTest {
//    @Rule
//    public PowerMockRule rule = new PowerMockRule();
//
//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    Context mockContext;

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp(){

    }

    @Test
    public void GetInstanceNullContextShouldFail(){
        MockContext mock = new MockContext();
        // Arrange
        QuestionsHandling instance = QuestionsHandling.getInstance(null, -1);
        // Act
        // Assert
        assertNotNull(instance);
        assertEquals(0,instance.getFullQuestionSet().get(0).correctAnswer);
    }

    @Test
    public void GetInstanceRegularContextShouldPass(){
        // Arrange
        QuestionsHandling instance = QuestionsHandling.getInstance(mockContext, -1);
        // Act
        // Assert
        assertNotNull(instance);
    }

    @Test
    public void GetInstanceQuizNumberNegOneShouldPass(){
        // Arrange
        // Act
        // Assert
    }

    @Test
    public void GetInstanceOtherQuizNumberShouldPass(){
        // Arrange
        // Act
        // Assert
    }


}