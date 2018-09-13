package com.fedex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication

public class Application implements CommandLineRunner{

    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    private CacheManager cacheManager;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        String filePath = "/home/vcap/app/BOOT-INF/classes/ehcache.xml";
        String text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
        		"<ehcache xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" + 
        		"         xsi:noNamespaceSchemaLocation=\"http://www.ehcache.org/ehcache.xsd\"\r\n" + 
        		"         updateCheck=\"true\"\r\n" + 
        		"         monitoring=\"autodetect\"\r\n" + 
        		"         dynamicConfig=\"true\">\r\n" + 
        		"\r\n" + 
        		"    <cache name=\"organizations\"\r\n" + 
        		"           maxElementsInMemory=\"100\"\r\n" + 
        		"           eternal=\"false\"\r\n" + 
        		"           overflowToDisk=\"false\"\r\n" + 
        		"           timeToLiveSeconds=\"300\"\r\n" + 
        		"           timeToIdleSeconds=\"0\"\r\n" + 
        		"           memoryStoreEvictionPolicy=\"LFU\"\r\n" + 
        		"           transactionalMode=\"off\">\r\n" + 
        		"    </cache>\r\n" + 
        		"\r\n" + 
        		"</ehcache>";
        BufferedWriter output = null;
        try {
            File file = new File(filePath);
            output = new BufferedWriter(new FileWriter(file));
            output.write(text);
        } catch ( IOException e ) {
        	log.error("Exception while writing File");
        } finally {
          if ( output != null ) {
            output.close();
          }
        }
    }
    

   
    public void run(String... args) throws Exception {
        log.info("Spring Boot Ehcache 2 Caching Example Configuration");
        log.info("using cache manager: " + cacheManager.getClass().getName());

        cacheService.clearCache();

        validate("Ground");
        validate("Express");
        validate("Ground");
        validate("Freight");
        validate("Ground");
    }

    private void validate(String org){
        log.info("Calling: " + CacheService.class.getSimpleName() + ".validating(\"" + org + "\");");
        cacheService.validate(org);
    }
}
