package com.github.panhongan.spring;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SpringTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}
