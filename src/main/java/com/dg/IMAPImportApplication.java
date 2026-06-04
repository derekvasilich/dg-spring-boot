package com.dg;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@SpringBootApplication
@Profile("import")
public class IMAPImportApplication implements CommandLineRunner {
    
    private static final Logger log = LoggerFactory.getLogger(IMAPImportApplication.class);

    @Override
    public void run(String... args) throws Exception {
        log.info("Importing IMAP data {}", Arrays.toString(args));
    }

}
