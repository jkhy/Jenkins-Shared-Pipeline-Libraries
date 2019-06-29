package org.s2kdesign.build;

import org.junit.Before;
import org.junit.Test;
import org.s2kdesign.IStepExecutor;
import org.s2kdesign.ioc.ContextRegistry;
import org.s2kdesign.ioc.IContext;

import static org.mockito.Mockito.*;

/**
 * Example test class
 */
public class MsBuildTest {
    private IContext _context;
    private IStepExecutor _steps;

    @Before
    public void setup() {
        _context = mock(IContext.class);
        _steps = mock(IStepExecutor.class);

        when(_context.getStepExecutor()).thenReturn(_steps);

        ContextRegistry.registerContext(_context);
    }

    @Test
    public void build_callsShStep() {
        // prepare
        String solutionPath = "some/path/to.sln";
        MsBuild builder = new MsBuild(solutionPath);

        // execute
        builder.build();

        // verify
        verify(_steps).sh(anyString());
    }

    @Test
    public void build_shStepReturnsStatusNotEqualsZero_callsErrorStep() {
        // prepare
        String solutionPath = "some/path/to.sln";
        MsBuild builder = new MsBuild(solutionPath);

        when(_steps.sh(anyString())).thenReturn(-1);

        // execute
        builder.build();

        // verify
        verify(_steps).error(anyString());
    }
}
