package Test;

public class Cache {
    static class Core_Object {
        int id;
    
        public Core_Object(int id) {
            this.id = id;
        }
    }

    static class Processor {
        Cache cache;
        byte[][] memory;
        int clock;
        Core_Object[] cores;
        int hits;
        int accesses;
    
        public Processor(Cache cache, byte[][] memory, int clock, Core_Object[] cores, int hits, int accesses) {
            this.cache = cache;
            this.memory = memory;
            this.clock = clock;
            this.cores = cores;
            this.hits = hits;
            this.accesses = accesses;
        }
    
        public static Processor processorInit() {
            Cache cache = cacheInit();
            byte[][] memory = new byte[1024][4];
            int clock = 0;
            Core_Object[] cores = {coreInit(1), coreInit(2)};
            int hits = 0;
            int accesses = 0;
            return new Processor(cache, memory, clock, cores, hits, accesses);
        }
    
        // Stub methods for cache_Init and core_Init.
        private static Cache cacheInit() {
            return new Cache();
        }
    
        private static Core_Object coreInit(int id) {
            return new Core_Object(id);
        }
    }
}
