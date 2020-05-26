package com.avinashdavid.trivialtrivia.data;

import static org.junit.Assert.*;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.avinashdavid.trivialtrivia.Utility;
import com.avinashdavid.trivialtrivia.data.QuizDBContract;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@RunWith(RobolectricTestRunner.class)
public class QuizProviderTest {

    @Test
    public void OnCreateShouldPass(){
        // Act
        Boolean actual =
    }
}