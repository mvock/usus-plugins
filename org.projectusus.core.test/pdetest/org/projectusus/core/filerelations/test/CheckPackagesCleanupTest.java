package org.projectusus.core.filerelations.test;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.projectusus.adapter.ForcedRecompute;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.internal.JavaProject;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;

public class CheckPackagesCleanupTest extends PDETestForMetricsComputation {

    @Rule
    public JavaProject project2 = new JavaProject();

    @Test
    public void twoFilesInTwoProjects() throws Exception {
        project.createFolder( "mine" );
        project2.createFolder( "yours" );

        project.createFile( "mine/Acd_GameStateAI.java", loadResource( "Acd_GameStateAI.test" ) );
        workspace.buildFullyAndWait();
        assertEquals( 1, Packagename.getAll().size() );

        project2.createFile( "yours/Acd_LRUCache.java", loadResource( "Acd_LRUCache.test" ) );
        workspace.buildIncrementallyAndWait();
        new ForcedRecompute().schedule();
        Thread.sleep( 1000 );
        assertEquals( 2, Packagename.getAll().size() );
        project2.disableUsus();
        workspace.buildFullyAndWait();
        assertEquals( 1, Packagename.getAll().size() );
    }

}
