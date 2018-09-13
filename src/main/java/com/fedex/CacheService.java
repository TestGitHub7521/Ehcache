package com.fedex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "organizations")
public class CacheService {

    private static Logger log = LoggerFactory.getLogger(CacheService.class);

    @CacheEvict(allEntries = true)
    public void clearCache(){}

    @Cacheable(condition = "#org.equals('Ground')")
    public String validate(String org) {
        log.info("Executing: " + this.getClass().getSimpleName() + ".validating(\"" + org + "\");");
        return "joining " + org + "!";
    }

}