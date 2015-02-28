package net.hamnaberg.funclite;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

public class MapOpsTest {

    @Test
    public void foreach_externalSideEffect(){
        final List<AtomicInteger> sideEffect = Lists.newArrayList();
        Map<Integer,AtomicInteger> map = MapOps.newHashMap(1, new AtomicInteger(1), 2, new AtomicInteger(2), 3, new AtomicInteger(3));
        MapOps.foreach(map, entry -> sideEffect.add(entry.getValue()));
        for (AtomicInteger atomicInteger : sideEffect) {
            assertSame(map.get(atomicInteger.get()), atomicInteger);
        }
    }

    @Test
    public void foreach_mutationSideEffect(){
        Map<Integer,AtomicInteger> map = MapOps.newHashMap(1, new AtomicInteger(1), 2, new AtomicInteger(2), 3, new AtomicInteger(3));
        MapOps.foreach(map, entry -> entry.setValue(new AtomicInteger(entry.getValue().get()+1)));
        for (Integer integer : map.keySet()) {
            assertFalse(integer.equals(map.get(integer).get()));
        }
    }
    
    
}


