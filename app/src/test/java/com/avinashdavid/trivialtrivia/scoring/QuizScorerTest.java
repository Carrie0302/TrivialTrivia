package com.avinashdavid.trivialtrivia.scoring;

import android.content.ContentValues;
import android.content.Context;
import com.avinashdavid.trivialtrivia.data.QuizDBContract;
import com.avinashdavid.trivialtrivia.data.QuizDBHelper;
import com.avinashdavid.trivialtrivia.questions.IndividualQuestion;
import com.avinashdavid.trivialtrivia.questions.QuestionsHandling;

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
public class QuizScorerTest {

    /**
     *  Testing a Static Factory, so in order to prevent
     *  caching need to pass in a new quiz number between tests
     */
    private static QuizScorer basic;
    private static int basicQuizSize = 10;
    private static int halfCorrectSize = 4;
    private static QuizScorer halfCorrect;
    private QuestionScorer firstQuestion;
    private QuestionScorer secondQuestion;
    private QuestionScorer thirdQuestion;
    private QuestionScorer fourthQuestion;
    private ArrayList<QuestionScorer> halfCorrectQuestions;

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    Context mockContext;

    @Before
    public void setUp() throws Exception {
        //Basic Quiz
        basicQuizSize = 10;

        // Four Question Quiz Half Right
        firstQuestion = new QuestionScorer(1, 3, 1, 1);
        secondQuestion = new QuestionScorer(2, 1, 1, 2);
        thirdQuestion = new QuestionScorer(3, 0, 3, 3);
        fourthQuestion = new QuestionScorer(4, 0, 2, 0);
        halfCorrectQuestions = new ArrayList<>();
        halfCorrectQuestions.add( firstQuestion );
        halfCorrectQuestions.add( secondQuestion );
        halfCorrectQuestions.add( thirdQuestion );
        halfCorrectQuestions.add( fourthQuestion );


    }


    @Test
    public void setSize() {
        //Should set the size of the quiz, which is the number of questions
        basic = QuizScorer.getInstance(mockContext, basicQuizSize, 0);
        assertEquals(basicQuizSize, basic.getSize() );
        basic.setSize(100);

        //After Should not Be the Same as Before
        assertNotEquals(basicQuizSize, basic.getSize() );
        assertEquals(100, basic.getSize() );
    }

    @Test
    public void setSizeShouldNotBeNegative() {
        //Should not set a negative number of questions for a quiz
        basic = QuizScorer.getInstance(mockContext, basicQuizSize, 1);
        try {
            basic.setSize(-1);
            fail("Expected exception has not been thrown");
        }
        catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Illegal Capacity: -1"));
        }
    }

    @Test
    public void canNotScoreQuizBeforeItIsComplete() throws Exception {
        //Can not check the score of a quiz before quiz completion
        basic = QuizScorer.getInstance(mockContext, basicQuizSize, 2);
        try {
            ContentValues cv = QuizScorer.createQuizRecordContentValues( mockContext, basic );
            fail("Expected exception has not been thrown. Can not check the score of a quiz before complete.");
        }
        catch (Exception e) {
            assertThat(e.getMessage(), is("createQuizRecordContentValues() called before quiz completion (0, 10)"));
        }
    }

    @Test
    public void quizScoreShouldBeZeroWhenAllWrong() throws Exception {
        //Set up: One question quiz with wrong answer selected
        basic = QuizScorer.getInstance(mockContext, 1, 3);
        basic.addQuestionScorer(1, 0, 1, 2);
        ContentValues cv = QuizScorer.createQuizRecordContentValues( mockContext, basic );

        assertEquals("Quiz size:", 1, (int)cv.getAsInteger(QuizDBContract.QuizEntry.COLUMN_NAME_QUIZ_SIZE));
        assertEquals("Quiz score:", 0, (int)cv.getAsInteger(QuizDBContract.QuizEntry.COLUMN_NAME_SCORE));
    }


    @Test
    public void quizScoreMatchesContentValuesPassedToDB() throws Exception {
        //Setup Add half correct questions to the QuizScorer
        halfCorrect = QuizScorer.getInstance(mockContext, halfCorrectSize, 4);
        halfCorrect.addQuestionScorer(firstQuestion);
        halfCorrect.addQuestionScorer(secondQuestion);
        halfCorrect.addQuestionScorer(thirdQuestion);
        halfCorrect.addQuestionScorer(fourthQuestion);
        ContentValues cv = QuizScorer.createQuizRecordContentValues( mockContext, halfCorrect );
        int scoreForDB = (int)cv.getAsInteger(QuizDBContract.QuizEntry.COLUMN_NAME_SCORE);
        int score = halfCorrect.scoreQuiz( halfCorrectQuestions );

        //Check that the content values for insertion into quizEntry table in database match the current score
        assertEquals( "The number of correct answers:  ", 2, score );
        assertEquals("Quiz score inserted into quizEntry table in database: ", score,  scoreForDB );
    }

//
//    @Test
//    public void quizScoreShouldBe() throws Exception {
//        //Set up: Two question quiz with wrong answer selected
//        basic = QuizScorer.getInstance(mockContext, 2, 5);
//        basic.addQuestionScorer(1, 2, 1, 2);
//        basic.addQuestionScorer(1, 2, 3, 3);
//        ArrayList<QuestionScorer> qs = basic.getQuestionScorers();
//
//
////        QuestionScorer firstQuestionScorer = new QuestionScorer(firstQuestion.questionNumber, firstQuestion.category, firstQuestion.correctAnswer, 0);
////        firstQuestionScorer.setTimeTaken(4);
////        QuestionScorer secondQuestionScorer = new QuestionScorer(secondQuestion.questionNumber, secondQuestion.category, 5, secondQuestion.correctAnswer, 3);
////        QuestionScorer thirdQuestionScorer = new QuestionScorer(thirdQuestion.questionNumber, thirdQuestion.category, 7, thirdQuestion.correctAnswer, 2);
////        QuestionScorer fourthQuestionScorer = new QuestionScorer(fourthQuestion.questionNumber, fourthQuestion.category, fourthQuestion.correctAnswer, 0);
////        fourthQuestionScorer.setTimeTaken(3);
////        QuestionScorer fifthQuestionScorer = new QuestionScorer(fifthQuestion.questionNumber, fifthQuestion.category, 10, fifthQuestion.correctAnswer, 3);
//
//
//        ContentValues cv = QuizScorer.createQuizRecordContentValues( mockContext, basic );
//
//        assertEquals("Quiz size:", 1, (int)cv.getAsInteger(QuizDBContract.QuizEntry.COLUMN_NAME_QUIZ_SIZE));
//        assertEquals("Quiz score:", 0, (int)cv.getAsInteger(QuizDBContract.QuizEntry.COLUMN_NAME_SCORE));
//    }

    //
}