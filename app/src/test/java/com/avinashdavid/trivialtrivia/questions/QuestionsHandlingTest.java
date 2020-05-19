package com.avinashdavid.trivialtrivia.questions;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.avinashdavid.trivialtrivia.data.QuizDBContract;
import com.avinashdavid.trivialtrivia.questions.IndividualQuestion;

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


public class QuestionsHandlingTest {
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    Context mockContext;

    @Test
    public void GetInstanceNullContextShouldPass(){

    }

    @Test
    public void GetInstanceRegularContextShouldPass(){

    }

    @Test
    public void GetInstanceQuizNumberNegOneShouldPass(){

    }

    @Test
    public void GetInstanceOtherQuizNumberShouldPass(){

    }


}