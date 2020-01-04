package com.sirnommington.interception.sample.interceptors.authretry;

import com.sirnommington.interception.core.OperationImpl;
import com.sirnommington.interception.interceptor.Interceptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class OperationImplTest {

    private final Iterator<Interceptor> interceptors = Arrays.asList(new Interceptor[] {}).iterator();

    private OperationImpl operation;

    @Before
    public void before() {
        operation = new OperationImpl(interceptors);
    }

    @Test
    public void operationName_isInitiallyNull() {
        assertNull(operation.name());
    }

    @Test
    public void operationName_setsAndGetsOperationName() {
        String expectedOperationName = "theOperationName";
        operation.name(expectedOperationName);
        assertEquals(expectedOperationName, operation.name());
    }

    @Test
    public void param_isInitiallyNull() {
        assertNull(operation.param("theParamKey"));
    }

    @Test
    public void param_setsAndGetsParam() {
        final String paramKey = "theParamKey";
        final String expectedParamValue = "theParamValue";
        operation.param(paramKey, expectedParamValue);

        assertEquals(expectedParamValue, operation.param(paramKey));
    }
}
